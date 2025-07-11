package goxjanskloon.jsgs.network.handler;
import goxjanskloon.jsgs.network.packet.PacketType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
public class DecoderHandler extends ByteToMessageDecoder{
    @Override protected void decode(ChannelHandlerContext ctx,ByteBuf in,List<Object> out){
        int id=in.readInt();
        var type=PacketType.getType(id);
        out.add(type!=null?type.decode(in):id);
    }
}