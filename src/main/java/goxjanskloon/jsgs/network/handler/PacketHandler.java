package goxjanskloon.jsgs.network.handler;
import goxjanskloon.jsgs.network.packet.Packet;
import goxjanskloon.jsgs.network.packet.PacketListener;
import goxjanskloon.jsgs.network.packet.PacketType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
public class PacketHandler extends SimpleChannelInboundHandler<Packet<?>>{
    private static final Logger LOGGER=LogManager.getLogger();
    private final Map<PacketType<?>,PacketListener> listeners=new ConcurrentHashMap<>();
    private Channel channel;
    public void send(Packet<?> packet){
        channel.writeAndFlush(packet);
    }
    public void close(){
        if(channel!=null){
            try{
                channel.close().sync();
            }catch(InterruptedException e){
                LOGGER.error("Error closing channel",e);
            }
        }
    }
    @Override public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        LOGGER.error(cause);
        ctx.close();
    }
    @Override public void channelActive(ChannelHandlerContext ctx){
        channel=ctx.fireChannelActive().channel();
    }
    @Override protected void channelRead0(ChannelHandlerContext ctx,Packet<?> msg){
        listeners.get(msg.getType()).accept(msg);
    }
    public void addListener(PacketType<?> type,PacketListener listener){
        listeners.put(type,listener);
    }
    public void removeListener(PacketType<?> type){
        listeners.remove(type);
    }
    @SuppressWarnings("unchecked")
    public <T extends Packet<T>> Packet<T> expectReply(Packet<?> packet,PacketType<T> type){
        if(listeners.containsKey(type))
            throw new IllegalArgumentException("Listener already exists for "+type);
        CompletableFuture<Packet<T>> f=new CompletableFuture<>();
        addListener(type,p->f.complete((Packet<T>)p));
        send(packet);
        try{
            return f.get();
        }catch(Exception e){
            LOGGER.error("Error while waiting for packet of {}",type,e);
            throw new RuntimeException(e);
        }finally{
            removeListener(type);
        }
    }
}