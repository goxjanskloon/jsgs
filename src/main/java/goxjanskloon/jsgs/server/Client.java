package goxjanskloon.jsgs.server;
import goxjanskloon.jsgs.inject.InjectionPoint;
import goxjanskloon.jsgs.network.Signals;
import goxjanskloon.jsgs.network.handler.PacketHandler;
import goxjanskloon.jsgs.network.handler.SignalHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Client{
    private static final Logger LOGGER=LogManager.getLogger();
    private static class Inject extends InjectionPoint<Client>{
        public void run(Client c){
            super.run(c);
        }
    }
    private final PacketHandler packetHandler;
    private final SignalHandler signalHandler;
    public final String name;
    public final int id;
    public final ServerMain server;
    public static final InjectionPoint<Client> AFTER_REGISTRATION=new Inject();
    public Client(String name,int id,PacketHandler packetHandler,SignalHandler signalHandler,ServerMain server){
        this.name=name;
        this.id=id;
        this.packetHandler=packetHandler;
        this.signalHandler=signalHandler;
        this.server=server;
        ((Inject)AFTER_REGISTRATION).run(this);
    }
    public void disconnect(){
        signalHandler.send(Signals.DISCONNECTION);
        packetHandler.close();
        signalHandler.close();
        disconnected();
    }
    public final InjectionPoint<Client> afterDisconnection=new Inject();
    public void disconnected(){
        LOGGER.info("Disconnected client {}",name);
        ((Inject)afterDisconnection).run(this);
    }
}