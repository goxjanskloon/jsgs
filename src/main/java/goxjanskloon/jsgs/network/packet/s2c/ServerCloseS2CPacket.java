package goxjanskloon.jsgs.network.packet.s2c;
import goxjanskloon.jsgs.network.packet.Packet;
import goxjanskloon.jsgs.network.packet.PacketType;
import io.netty.buffer.ByteBuf;
public class ServerCloseS2CPacket implements Packet<ServerCloseS2CPacket>{
    public static final PacketType<ServerCloseS2CPacket> TYPE=PacketType.register(ServerCloseS2CPacket.class,buf->new ServerCloseS2CPacket());
    @Override public PacketType<ServerCloseS2CPacket> getType(){
        return TYPE;
    }
    @Override public void write(ByteBuf buf){}
}