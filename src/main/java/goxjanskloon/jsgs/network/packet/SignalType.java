package goxjanskloon.jsgs.network.packet;
import io.netty.buffer.ByteBuf;
public class SignalType<T extends Signal<T>> extends PacketType<T>{
    SignalType(int id,Class<T> clazz){
        super(new PacketDecoder<>(){
            private final T p;
            {
                try{
                    p=clazz.getConstructor().newInstance();
                }catch(Exception e){
                    throw new RuntimeException("Signal "+clazz.getName()+" must have a no-arg constructor",e);
                }
            }
            @Override public T decode(ByteBuf buf){
                return p;
            }
        },id);
    }
}