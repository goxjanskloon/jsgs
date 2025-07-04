package goxjanskloon.jsgs.server;
import goxjanskloon.jsgs.network.handler.PacketHandler;
import goxjanskloon.jsgs.network.packet.DisconnectionSignal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Client{
    private static final Logger LOGGER=LogManager.getLogger();
    private final PacketHandler packetHandler;
    public final String name;
    public final int id;
    public final ServerMain server;
    public Client(String name,int id,PacketHandler packetHandler,ServerMain server){
        this.name=name;
        this.id=id;
        this.packetHandler=packetHandler;
        this.server=server;
    }
    public void disconnect()throws InterruptedException{
        packetHandler.send(DisconnectionSignal.TYPE);
        packetHandler.close();
        LOGGER.info("Disconnected client {}",name);
    }
}