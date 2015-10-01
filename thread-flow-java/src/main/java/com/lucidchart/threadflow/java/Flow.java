package com.lucidchart.threadflow.java;

import com.lucidchart.threadflow.AggregateFlowStore;
import com.lucidchart.threadflow.BaseFlow;
import com.lucidchart.threadflow.FlowStore;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Supplier;

public class Flow extends BaseFlow {
    public static Flow create(FlowStore store, FlowStore... others) {
        return new Flow(new AggregateFlowStore(store, others));
    }

    public Flow(FlowStore store) {
        super(store);
    }

    public String getValue() {
        return store.get();
    }

    public void setValue(String value) {
        store.set(value);
    }

    public Runnable wrapRunnable(final Runnable runnable) {
        final String id = store.get();
        if (id == null) {
            return runnable;
        } else {
            return new Runnable() {
                public void run() {
                    store.set(id);
                    try {
                        runnable.run();
                    } finally {
                        store.set(null);
                    }
                }
            };
        }
    }

    public <V> Callable<V> wrapCallable(final Callable<V> callable) {
        final String id = store.get();
        if (id == null) {
            return callable;
        } else {
            return new Callable<V>() {
                public V call() throws Exception {
                    store.set(id);
                    try {
                        return callable.call();
                    } finally {
                        store.set(null);
                    }
                }
            };
        }
    }

    public Executor wrapExecutor(Executor executor) {
        return new ProxyWrappedExecutor(executor) {
            protected Runnable proxy(Runnable runnable) {
                return wrapRunnable(runnable);
            }
        };
    }

    public ExecutorService wrapExecutorService(ExecutorService executorService) {
        return new ProxyWrappedExecutorService(executorService) {
            protected <V> Callable<V> proxy(Callable<V> callable) {
                return wrapCallable(callable);
            }

            protected Runnable proxy(Runnable runnable) {
                return wrapRunnable(runnable);
            }
        };
    }

}
