package com.lucidchart.threadflow;

public class BaseFlow {

    final protected FlowStore store;

    public BaseFlow(FlowStore store) {
        this.store = store;
    }

    public String getValue() {
        return store.get();
    }

    public void setValue(String value) {
        store.set(value);
    }

}
