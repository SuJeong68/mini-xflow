package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.wire.Wire;

public class HttpServerNode extends InputOutputNode {
    @Override
    void process() {
        for (int i = 0; i < getInputWiresLength(); i++) {
            Wire inputWire = getInputWire(i);
            while (inputWire.hasMessage()) {
                output(inputWire.get());
            }
        }
    }
}
