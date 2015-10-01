package com.lucidchart.threadflow.java;

import java.util.concurrent.*;

public abstract class WrappedExecutor implements Executor {

    private final Executor underlying;

    public WrappedExecutor(Executor underlying) {
        this.underlying = underlying;
    }

    public void execute(Runnable command) {
        underlying.execute(command);
    }

}
