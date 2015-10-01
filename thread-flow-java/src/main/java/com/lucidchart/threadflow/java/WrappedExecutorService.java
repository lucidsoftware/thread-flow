package com.lucidchart.threadflow.java;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

public abstract class WrappedExecutorService extends WrappedExecutor implements ExecutorService {

    private final ExecutorService underlying;

    public WrappedExecutorService(ExecutorService underlying) {
        super(underlying);
        this.underlying = underlying;
    }

    public void shutdown() {
        underlying.shutdown();
    }

    public List<Runnable> shutdownNow() {
        return underlying.shutdownNow();
    }

    public boolean isShutdown() {
        return underlying.isShutdown();
    }

    public boolean isTerminated() {
        return underlying.isTerminated();
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return underlying.awaitTermination(timeout, unit);
    }

    public <T> Future<T> submit(Callable<T> task) {
        return underlying.submit(task);
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return underlying.submit(task, result);
    }

    public Future<?> submit(Runnable task) {
        return underlying.submit(task);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return underlying.invokeAll(tasks);
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return underlying.invokeAll(tasks, timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return underlying.invokeAny(tasks);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return underlying.invokeAny(tasks, timeout, unit);
    }

}
