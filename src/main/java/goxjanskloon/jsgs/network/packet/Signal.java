package goxjanskloon.jsgs.network.packet;
import io.netty.buffer.ByteBuf;
public interface Signal<T extends Signal<T>> extends Packet<T>{
    @Override default void write(ByteBuf buf){}
    @Override SignalType<T> getType();
}