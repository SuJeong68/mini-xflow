package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.exception.AlreadyExistsException;
import com.nhnacademy.aiot.exception.OutOfBoundsException;
import com.nhnacademy.aiot.message.Message;
import com.nhnacademy.aiot.wire.Wire;

public abstract class InputOutputNode extends ActiveNode {
    private static final int DEFAULT_COUNT = 1;
    private final Wire[] outputWires;
    private final Wire[] inputWires;

    InputOutputNode() {
        super();
        outputWires = new Wire[DEFAULT_COUNT];
        inputWires = new Wire[DEFAULT_COUNT];
    }

    public void connectInputWires(int index, Wire wire) {
        if (index < 0 || inputWires.length <= index) {
            throw new OutOfBoundsException();
        }

        if (inputWires[index] != null) {
            throw new AlreadyExistsException();
        }

        inputWires[index] = wire;
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

    public int getInputWiresLength() {
        return inputWires.length;
    }

    public Wire getInputWire(int index) {
        return inputWires[index];
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
