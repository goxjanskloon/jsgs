package goxjanskloon.jsgs.ability;
import com.google.common.collect.ImmutableSet;
import goxjanskloon.jsgs.inject.Injector;
import java.util.Collection;
public class Ability{
    public final String name,description;
    public final ImmutableSet<Injector<?>> injectors;
    public Ability(String name,String description,Collection<Injector<?>> injectors){
        this.name=name;
        this.description=description;
        this.injectors=injectors instanceof ImmutableSet<Injector<?>> i?i:ImmutableSet.copyOf(injectors);
    }
}