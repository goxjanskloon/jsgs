package goxjanskloon.jsgs.general;
import com.google.common.collect.ImmutableSet;
import goxjanskloon.jsgs.ability.Ability;
import java.util.Collection;
public class General{
    public final String name;
    public final int initHealth,maxHealth;
    public final ImmutableSet<? extends Ability> abilities;
    public General(String name,int initHealth,int maxHealth,Collection<? extends Ability> abilities){
        this.name=name;
        this.initHealth=initHealth;
        this.maxHealth=maxHealth;
        this.abilities=abilities instanceof ImmutableSet<? extends Ability> a?a:ImmutableSet.copyOf(abilities);
    }
}