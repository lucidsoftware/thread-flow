package com.lucidchart.threadflow;

public class BaseFlow {

    private final FlowStore store;

    public BaseFlow(FlowStore store) {
        this.store = store;
    }

    public FlowStore getStore() {
        return store;
    }

}
