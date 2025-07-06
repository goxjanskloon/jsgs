package goxjanskloon.jsgs.network.handler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class SignalHandler extends SimpleChannelInboundHandler<Integer>{
    private static final Logger LOGGER=LogManager.getLogger();
    private final Map<Integer,Runnable> listeners=new ConcurrentHashMap<>();
    private Channel channel;
    public void send(int signal){
        var buf=channel.alloc().buffer();
        buf.writeInt(signal);
        channel.writeAndFlush(buf);
    }
    public void addListener(int signal,Runnable listener){
        listeners.put(signal,listener);
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
    @Override protected void channelRead0(ChannelHandlerContext ctx,Integer signal){
        listeners.get(signal).run();
    }
}