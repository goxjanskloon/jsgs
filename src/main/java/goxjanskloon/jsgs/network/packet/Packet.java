package goxjanskloon.jsgs.network.packet;
import io.netty.buffer.ByteBuf;
public interface Packet<T extends Packet<T>>{
    PacketType<T> getType();
    void write(ByteBuf buf);
}