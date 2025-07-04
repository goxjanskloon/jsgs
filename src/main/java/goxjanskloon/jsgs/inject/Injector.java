package goxjanskloon.jsgs.inject;
public interface Injector<T>{
    void run(T target);
    default void onInject(){}
    default void onRemove(){}
}