package goxjanskloon.jsgs.network.packet.s2c;
import goxjanskloon.jsgs.network.StringCodec;
import goxjanskloon.jsgs.network.packet.Packet;
import goxjanskloon.jsgs.network.packet.PacketType;
import io.netty.buffer.ByteBuf;
public class ServerNameS2CPacket implements Packet<ServerNameS2CPacket>{
    public static final PacketType<ServerNameS2CPacket> TYPE=PacketType.register(ServerNameS2CPacket.class,buf->new ServerNameS2CPacket(StringCodec.read(buf))); 
    public final String name;
    public ServerNameS2CPacket(String name){
        this.name=name;
    }
    @Override public PacketType<ServerNameS2CPacket> getType(){
        return TYPE;
    }
    @Override public void write(ByteBuf buf){
        StringCodec.write(buf,name);
    }
}