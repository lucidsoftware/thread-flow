package com.lucidchart.threadflow.java;

import java.util.concurrent.*;

public abstract class ProxyWrappedExecutor extends WrappedExecutor {

    public ProxyWrappedExecutor(Executor underlying) {
        super(underlying);
    }

    protected abstract Runnable proxy(Runnable runnable);

    public void execute(Runnable command) {
        super.execute(proxy(command));
    }

}
