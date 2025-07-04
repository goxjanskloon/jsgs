package goxjanskloon.jsgs.network.packet.c2s;
import goxjanskloon.jsgs.network.packet.Packet;
import goxjanskloon.jsgs.network.packet.PacketType;
import goxjanskloon.jsgs.network.StringCodec;
import io.netty.buffer.ByteBuf;
public class ClientRegisterationC2SPacket implements Packet<ClientRegisterationC2SPacket>{
    public static final PacketType<ClientRegisterationC2SPacket> TYPE=PacketType.register(ClientRegisterationC2SPacket.class,buf->new ClientRegisterationC2SPacket(buf.readInt(),StringCodec.read(buf)));
    public final int id;
    public final String name;
    public ClientRegisterationC2SPacket(int id,String name){
        this.id=id;
        this.name=name;
    }
    @Override public PacketType<ClientRegisterationC2SPacket> getType(){
        return TYPE;
    }
    @Override public void write(ByteBuf buf){
        buf.writeInt(id);
        StringCodec.write(buf,name);
    }
}