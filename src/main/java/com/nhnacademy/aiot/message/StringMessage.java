package com.nhnacademy.aiot.message;

public class StringMessage extends Message {
    String payload;

    public StringMessage(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
