package goxjanskloon.jsgs.network.handler;
import goxjanskloon.jsgs.network.packet.Packet;
import goxjanskloon.jsgs.network.packet.Signal;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
public class EncoderHandler extends MessageToByteEncoder<Packet<?>>{
    @Override protected void encode(ChannelHandlerContext ctx,Packet<?> msg,ByteBuf out){
        out.writeInt(msg.getType().id);
        if(!(msg instanceof Signal<?>))
            msg.write(out);
    }
}