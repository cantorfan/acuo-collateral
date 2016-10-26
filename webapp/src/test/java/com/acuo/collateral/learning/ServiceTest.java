package com.acuo.collateral.learning;

import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.AbstractScheduledService;
import org.junit.Test;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServiceTest {

    @Test
    public void idleService(){
        AbstractIdleService service=new AbstractIdleService() {
            @Override
            protected void startUp() throws Exception {
                System.out.println("started");
            }

            @Override
            protected void shutDown() throws Exception {
                System.out.println("stopped");
            }
        };
        service.startAsync().awaitRunning();
        System.out.println("Running");
        service.stopAsync().awaitTerminated();
    }

    @Test
    public void scheduledService(){
        AbstractScheduledService service=new AbstractScheduledService() {
            @Override
            protected void runOneIteration() throws Exception {
                System.out.println("runOneIteration");
            }

            @Override
            protected Scheduler scheduler() {//调度策略
                return Scheduler.newFixedDelaySchedule(0,10, TimeUnit.MILLISECONDS);
            }

            @Override
            protected ScheduledExecutorService executor() {//Thread Pool

                return new ScheduledThreadPoolExecutor(10);
            }
        };
        service.startAsync().awaitRunning();
        service.stopAsync().awaitTerminated();
    }
}