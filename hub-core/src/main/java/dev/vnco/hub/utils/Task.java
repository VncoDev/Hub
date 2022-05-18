package dev.vnco.hub.utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public abstract class Task implements Runnable {

    private final ScheduledThreadPoolExecutor executor;
    private final ScheduledFuture<?> future;

    public Task(int corePoolSize, long period, TimeUnit unit) {
        executor = new ScheduledThreadPoolExecutor(corePoolSize, new ThreadFactoryBuilder()
                .setNameFormat(getClass().getSimpleName() + " Task - %d")
                .setDaemon(true).build());

        executor.setRemoveOnCancelPolicy(true);
        future = executor.scheduleAtFixedRate(this, 0L, period, unit);
    }

    public void cancel() {
        executor.shutdownNow();
        future.cancel(true);
    }
}
