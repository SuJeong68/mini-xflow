package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.message.StringMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SocketInNode extends InputNode {
    BufferedReader reader;

    public SocketInNode(InputStream inputStream) {
        super();
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    void process() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                output(new StringMessage(line));
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    synchronized void postprocess() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
