package com.nhnacademy.aiot.node;

import com.github.f4b6a3.uuid.UuidCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public abstract class Node {
    private static int count;
    private UUID id;
    private String name;
    protected Logger logger;

    Node() {
        id = UuidCreator.getTimeBased();
        name = id.toString();

        count++;
        logger = LoggerFactory.getLogger(name);

        logger.trace("Created Node => id : {}, name : {}", id, name);
    }

    public String getName() {
        return name;
    }
}
