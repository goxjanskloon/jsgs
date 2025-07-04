package goxjanskloon.jsgs.inject;
import java.util.HashSet;
public class InjectionPoint<T>{
    private final HashSet<Injector<T>> injectors=new HashSet<>();
    private final int key;
    public InjectionPoint(int key){
        this.key=key;
    }
    public void inject(Injector<T> injector){
        injectors.add(injector);
        injector.onInject();
    }
    public void remove(Injector<T> injector){
        injectors.remove(injector);
        injector.onRemove();
    }
    public void run(int key,T target){
        if(this.key!=key)
            throw new IllegalArgumentException("Invalid key");
        try{
            for(var i:injectors)
                i.run(target);
        }catch(Exception e){
            throw new RuntimeException("Error running injectors",e);
        }
    }
}