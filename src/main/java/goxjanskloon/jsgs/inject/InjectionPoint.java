package goxjanskloon.jsgs.inject;
import java.util.HashSet;
public abstract class InjectionPoint<T>{
    private final HashSet<Injector<T>> injectors=new HashSet<>();
    public void inject(Injector<T> injector){
        injectors.add(injector);
        injector.onInject();
    }
    protected void run(T target){
        for(var i:injectors)
            i.run(target);
    }
}