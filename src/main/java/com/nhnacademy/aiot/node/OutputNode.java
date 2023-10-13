package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.exception.AlreadyExistsException;
import com.nhnacademy.aiot.exception.OutOfBoundsException;
import com.nhnacademy.aiot.wire.Wire;

public abstract class OutputNode extends ActiveNode {
    private static final int DEFAULT_COUNT = 1;
    private final Wire[] inputWires;

    OutputNode() {
        super();
        inputWires = new Wire[DEFAULT_COUNT];
    }

    public int getInputWiresLength() {
        return inputWires.length;
    }

    public Wire getInputWire(int index) {
        return inputWires[index];
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
}
