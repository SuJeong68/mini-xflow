package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.exception.AlreadyStartedException;

public abstract class ActiveNode extends Node implements Runnable {
    private Thread thread;

    ActiveNode() {
        super();
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
        while (isAlive()) {
            process();
        }
        postprocess();
    }
}
