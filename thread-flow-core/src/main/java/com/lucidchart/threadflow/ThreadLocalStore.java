package com.lucidchart.threadflow;

public class ThreadLocalStore implements FlowStore {

    private final ThreadLocal<String> flowId;

    public static final ThreadLocalStore GLOBAL = new ThreadLocalStore();

    public ThreadLocalStore() {
        flowId = new ThreadLocal<>();
    }

    public String get() {
        return flowId.get();
    }

    public void set(String value) {
        flowId.set(value);
    }

}
