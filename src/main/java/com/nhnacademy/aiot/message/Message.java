package com.nhnacademy.aiot.message;

public abstract class Message {
    private static int count;
    private final String id;
    private final long creationTime;

    Message() {
        count++;
        id = getClass().getSimpleName() + count;
        creationTime = System.currentTimeMillis();
    }
}
