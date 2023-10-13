package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.message.Message;
import com.nhnacademy.aiot.message.StringMessage;
import com.nhnacademy.aiot.wire.Wire;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class SocketOutNode extends OutputNode {
    BufferedWriter writer;

    public SocketOutNode(OutputStream outputStream) {
        super();
        writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    @Override
    void process() {
        try {
            for (int i = 0; i < getInputWiresLength(); i++) {
                Wire inputWire = getInputWire(i);
                while (inputWire.hasMessage()) {
                    Message message = inputWire.get();
                    if (message instanceof StringMessage) {
                        writer.write(((StringMessage) message).getPayload() + "\n");
                        writer.flush();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    synchronized void postprocess() {
        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}