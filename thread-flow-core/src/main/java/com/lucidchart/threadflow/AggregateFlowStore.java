package com.lucidchart.threadflow;

public class AggregateFlowStore implements FlowStore {

    private final FlowStore store;
    private final FlowStore[] others;

    public AggregateFlowStore(FlowStore store, FlowStore... others) {
        this.store = store;
        this.others = others;
    }

    public String get() {
        return store.get();
    }

    public void set(String value) {
        store.set(value);
        for (FlowStore store: others) {
            store.set(value);
        }
    }

}
