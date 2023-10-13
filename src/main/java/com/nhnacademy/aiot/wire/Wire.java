package com.nhnacademy.aiot.wire;

import com.nhnacademy.aiot.message.Message;

public interface Wire {
    void put(Message message);
    boolean hasMessage();
    Message get();
}
