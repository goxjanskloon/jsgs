package goxjanskloon.jsgs.client;
import goxjanskloon.jsgs.network.handler.PacketHandler;
import goxjanskloon.jsgs.network.handler.DecoderHandler;
import goxjanskloon.jsgs.network.handler.EncoderHandler;
import goxjanskloon.jsgs.network.handler.SignalHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.MultiThreadIoEventLoopGroup;
import io.netty.channel.nio.NioIoHandler;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.ThreadLocalRandom;
public class ClientMain{
    private static final Logger LOGGER=LogManager.getLogger();
    private final EventLoopGroup workerGroup=new MultiThreadIoEventLoopGroup(NioIoHandler.newFactory());
    private final PacketHandler packetHandler=new PacketHandler();
    private final SignalHandler signalHandler=new SignalHandler();
    private Server server;
    public final int id=ThreadLocalRandom.current().nextInt(),port;
    public final String name,host;
    public ClientMain(String name,String host,int port){
        this.name=name;
        this.host=host;
        this.port=port;
    }
    public void start(){
        ChannelFuture f=new Bootstrap().group(workerGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<>(){
            @Override public void initChannel(Channel c){
                c.pipeline()
                        .addLast("logging",new LoggingHandler())
                        .addLast("decoding",new DecoderHandler())
                        .addLast("packetListening",packetHandler)
                        .addLast("signalListening",signalHandler)
                        .addLast("encoding",new EncoderHandler());
            }
        }).connect(host,port);
        try{
            f.sync();
        }catch(InterruptedException e){
            LOGGER.error(e);
        }
        server=new Server(packetHandler,signalHandler,this);
    }
    public void close(){
        server.disconnect();
        try{
            workerGroup.shutdownGracefully().sync();
        }catch(InterruptedException e){
            LOGGER.error("Error shutting down workerGroup",e);
        }
    }
}