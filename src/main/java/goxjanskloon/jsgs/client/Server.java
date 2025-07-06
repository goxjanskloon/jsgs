package goxjanskloon.jsgs.client;
import goxjanskloon.jsgs.network.Signals;
import goxjanskloon.jsgs.network.handler.PacketHandler;
import goxjanskloon.jsgs.network.handler.SignalHandler;
import goxjanskloon.jsgs.network.packet.c2s.ClientRegisterationC2SPacket;
import goxjanskloon.jsgs.network.packet.s2c.ServerNameS2CPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Server{
    private static final Logger LOGGER=LogManager.getLogger();
    private final PacketHandler packetHandler;
    private final SignalHandler signalHandler;
    public final String name;
    public final ClientMain client;
    public Server(PacketHandler packetHandler,SignalHandler signalHandler,ClientMain client){
        this.client=client;
        this.signalHandler=signalHandler;
        name=((ServerNameS2CPacket)(this.packetHandler=packetHandler).expectReply(new ClientRegisterationC2SPacket(client.id,client.name),ServerNameS2CPacket.TYPE)).name;
        LOGGER.info("Connected to server {}",name);
    }
    public void disconnect(){
        signalHandler.send(Signals.DISCONNECTION);
        packetHandler.close();
        signalHandler.close();
        LOGGER.info("Disconnected from server {}",name);
    }
}