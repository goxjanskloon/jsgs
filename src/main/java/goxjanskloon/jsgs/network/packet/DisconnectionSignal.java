package goxjanskloon.jsgs.network.packet;
public class DisconnectionSignal implements Signal<DisconnectionSignal>{
    public static final SignalType<DisconnectionSignal> TYPE=PacketType.register(DisconnectionSignal.class);
    @Override public SignalType<DisconnectionSignal> getType(){
        return TYPE;
    }
}