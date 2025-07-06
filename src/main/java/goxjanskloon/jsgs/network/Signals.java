package goxjanskloon.jsgs.network;
public interface Signals{
    int DISCONNECTION=of("disconnection");
    static int of(String name){
        return (name+"signal").hashCode();
    }
}