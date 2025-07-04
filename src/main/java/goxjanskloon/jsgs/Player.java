package goxjanskloon.jsgs;
import goxjanskloon.jsgs.ability.Ability;
import goxjanskloon.jsgs.general.General;
import java.util.HashSet;
import java.util.Set;
public abstract class Player{
    public final String name;
    protected int health,maxHealth;
    public final General general;
    protected final Set<? extends Ability> abilities;
    protected Player(String name,General general){
        this.name=name;
        this.health=(this.general=general).initHealth;
        this.maxHealth=general.maxHealth;
        this.abilities=new HashSet<>(general.abilities);
    }
}