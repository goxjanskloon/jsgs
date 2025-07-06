package goxjanskloon.jsgs.server;
import goxjanskloon.jsgs.network.Signals;
import goxjanskloon.jsgs.network.handler.PacketHandler;
import goxjanskloon.jsgs.network.handler.SignalHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Client{
    private static final Logger LOGGER=LogManager.getLogger();
    private final PacketHandler packetHandler;
    private final SignalHandler signalHandler;
    public final String name;
    public final int id;
    public final ServerMain server;
    public Client(String name,int id,PacketHandler packetHandler,SignalHandler signalHandler,ServerMain server){
        this.name=name;
        this.id=id;
        this.packetHandler=packetHandler;
        this.signalHandler=signalHandler;
        this.server=server;
    }
    public void disconnect(){
        signalHandler.send(Signals.DISCONNECTION);
        packetHandler.close();
        signalHandler.close();
        LOGGER.info("Disconnected client {}",name);
    }
}