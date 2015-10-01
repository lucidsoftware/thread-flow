package com.lucidchart.threadflow.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public abstract class ProxyWrappedExecutorService extends WrappedExecutorService {

    public ProxyWrappedExecutorService(ExecutorService underlying) {
        super(underlying);
    }

    protected abstract <V> Callable<V> proxy(Callable<V> callable);

    protected abstract Runnable proxy(Runnable runnable);

    private <V> Collection<Callable<V>> proxy(Collection<? extends Callable<V>> tasks) {
        Collection<Callable<V>> proxyTasks = new ArrayList<>(tasks.size());
        for (Callable<V> task : tasks) {
            proxyTasks.add(task);
        }
        return proxyTasks;
    }

    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(proxy(task));
    }

    public <T> Future<T> submit(Runnable task, T result) {
        return super.submit(proxy(task), result);
    }

    public Future<?> submit(Runnable task) {
        return super.submit(proxy(task));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return super.invokeAll(proxy(tasks));
    }

    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return super.invokeAll(proxy(tasks), timeout, unit);
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return super.invokeAny(proxy(tasks));
    }

    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return super.invokeAny(proxy(tasks), timeout, unit);
    }

    public void execute(Runnable command) {
        super.execute(proxy(command));
    }

}
