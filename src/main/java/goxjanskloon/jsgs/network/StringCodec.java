package goxjanskloon.jsgs.network;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import java.nio.charset.StandardCharsets;
public interface StringCodec{
    static void write(ByteBuf buf,String str){
        buf.writeInt(ByteBufUtil.utf8Bytes(str));
        buf.writeCharSequence(str,StandardCharsets.UTF_8);
    }
    static String read(ByteBuf buf){
        return buf.readCharSequence(buf.readInt(),StandardCharsets.UTF_8).toString();
    }
}