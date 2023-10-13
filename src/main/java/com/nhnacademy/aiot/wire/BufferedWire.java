package com.nhnacademy.aiot.wire;

import com.nhnacademy.aiot.message.Message;

import java.util.LinkedList;
import java.util.Queue;

public class BufferedWire implements Wire {
    private final Queue<Message> messageQueue;

    public BufferedWire() {
        super();
        messageQueue = new LinkedList<>();
    }

    @Override
    public void put(Message message) {
        messageQueue.add(message);
    }

    @Override
    public boolean hasMessage() {
        return !messageQueue.isEmpty();
    }

    @Override
    public Message get() {
        return messageQueue.poll();
    }
}
