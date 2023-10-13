package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.exception.AlreadyStartedException;

public abstract class ActiveNode extends Node implements Runnable {
    private static final long DEFAULT_INTERVAL = 1;
    private Thread thread;
    private long interval;

    ActiveNode() {
        super();

        interval = DEFAULT_INTERVAL;
    }

    public synchronized void start() {
        if (thread != null) {
            throw new AlreadyStartedException();
        }

        thread = new Thread(this, getName());
        thread.start();
    }

    public synchronized void stop() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    public synchronized boolean isAlive() {
        return thread != null && thread.isAlive();
    }

    void preprocess() {}
    void process() {}
    synchronized void postprocess() {}

    @Override
    public void run() {
        preprocess();

        long previousTime = System.currentTimeMillis();

        while (isAlive()) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - previousTime;

            if (elapsedTime < interval) {
                try {
                    process();
                    Thread.sleep(interval - elapsedTime);
                } catch (InterruptedException e) {
                    stop();
                }
            }

            previousTime = previousTime + (System.currentTimeMillis() - previousTime) / interval * interval;
        }

        postprocess();
    }
}
