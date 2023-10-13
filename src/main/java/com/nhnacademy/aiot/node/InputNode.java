package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.exception.AlreadyExistsException;
import com.nhnacademy.aiot.exception.OutOfBoundsException;
import com.nhnacademy.aiot.message.Message;
import com.nhnacademy.aiot.wire.Wire;

public abstract class InputNode extends ActiveNode {
    private static final int DEFAULT_COUNT = 1;
    private final Wire[] outputWires;

    InputNode() {
        super();
        outputWires = new Wire[DEFAULT_COUNT];
    }

    public void connectOutputWire(int index, Wire wire) {
        if (index < 0 || outputWires.length <= index) {
            throw new OutOfBoundsException();
        }

        if (outputWires[index] != null) {
            throw new AlreadyExistsException();
        }

        outputWires[index] = wire;
    }

    void output(Message message) {
        for (Wire wire : outputWires) {
            if (wire != null) {
                wire.put(message);
            }
        }

        logger.trace("Message Out : {}", message);
    }
}
