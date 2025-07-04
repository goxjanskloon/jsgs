package goxjanskloon.jsgs.network.packet;
import io.netty.buffer.ByteBuf;
public interface PacketDecoder<T extends Packet<?>>{
    T decode(ByteBuf buf);
}