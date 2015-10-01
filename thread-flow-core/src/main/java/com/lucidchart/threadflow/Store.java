package com.lucidchart.threadflow;

public interface Store<V> {

    V get();

    void set(V value);

}
