package goxjanskloon.jsgs.network.packet;
import io.netty.buffer.ByteBuf;
import java.util.HashMap;
import java.util.Map;
public class PacketType<T extends Packet<T>>{
    public final PacketDecoder<T> decoder;
    public final int id;
    protected PacketType(PacketDecoder<T> decoder,int id){
        this.decoder=decoder;
        this.id=id;
    }
    public Packet<T> decode(ByteBuf buf){
        return decoder.decode(buf);
    }
    @Override public String toString(){
        return "PacketType:id="+id;
    }
    private static final Map<Integer,PacketType<?>> TYPES=new HashMap<>();
    @SuppressWarnings("unchecked")
    public static <T extends Packet<T>> PacketType<T> register(Class<T> clazz,PacketDecoder<T> decoder){
        return (PacketType<T>)TYPES.computeIfAbsent(clazz.getName().hashCode(),id->new PacketType<>(decoder,id));
    }
    public static PacketType<?> getType(int id){
        return TYPES.get(id);
    }
}