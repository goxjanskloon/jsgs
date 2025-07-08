package goxjanskloon.jsgs.server;
import goxjanskloon.jsgs.inject.InjectionPoint;
import goxjanskloon.jsgs.network.Signals;
import goxjanskloon.jsgs.network.handler.PacketHandler;
import goxjanskloon.jsgs.network.handler.DecoderHandler;
import goxjanskloon.jsgs.network.handler.EncoderHandler;
import goxjanskloon.jsgs.network.handler.SignalHandler;
import goxjanskloon.jsgs.network.packet.c2s.ClientRegisterationC2SPacket;
import goxjanskloon.jsgs.network.packet.s2c.ServerNameS2CPacket;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class ServerMain{
    private static final Logger LOGGER=LogManager.getLogger();
    private static class Inject extends InjectionPoint<ServerMain>{
        public void run(ServerMain s){
            super.run(s);
        }
    }
    public final String name,localAddress;
    public final int port;
    private EventLoopGroup bossGroup,workerGroup;
    private final Map<Integer,Client> clients=new ConcurrentHashMap<>();
    public ServerMain(int port,String name){
        this.name=name;
        this.port=port;
        try{
            localAddress=InetAddress.getLocalHost().getHostAddress();
        }catch(UnknownHostException e){
            throw new RuntimeException(e);
        }
    }
    public void start(){
        bossGroup=new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        workerGroup=new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
        ChannelFuture f=new ServerBootstrap().group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<>(){
            @Override public void initChannel(Channel c){
                var h=new PacketHandler();
                var s=new SignalHandler();
                h.addListener(ClientRegisterationC2SPacket.TYPE,packet->{
                    var p=(ClientRegisterationC2SPacket)packet;
                    var client=new Client(p.name,p.id,h,s,ServerMain.this);
                    clients.put(p.id,client);
                    LOGGER.info("Registered client {} (id={})",p.name,p.id);
                    h.send(new ServerNameS2CPacket(name));
                    s.addListener(Signals.DISCONNECTION,()->unregisterClient(client.id));
                });
                c.pipeline()
                        .addLast("logging",new LoggingHandler())
                        .addLast("decoding",new DecoderHandler())
                        .addLast("packetListening",h)
                        .addLast("signalListening",s)
                        .addLast("encoding",new EncoderHandler());
            }
        }).bind(port);
        try{
            f.sync();
        }catch(InterruptedException e){
            LOGGER.error(e.toString());
        }
    }
    public void disconnect(Client client){
        unregisterClient(client.id).disconnect();
    }
    public Client unregisterClient(int id){
        var client=clients.remove(id);
        LOGGER.info("Unregistered client {} (id={})",client.name,id);
        return client;
    }
    public final InjectionPoint<ServerMain> afterClose=new Inject();
    public void close(){
        if(!clients.isEmpty()){
            for(var c: clients.values())
                c.disconnect();
            clients.clear();
        }
        try{
            workerGroup.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }catch(InterruptedException e){
            LOGGER.error("Error shutting down event loop group(s)",e);
        }
        ((Inject)afterClose).run(this);
    }
}