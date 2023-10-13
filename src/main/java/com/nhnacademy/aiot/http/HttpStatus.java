package com.nhnacademy.aiot.http;

public enum HttpStatus {
    OK(200, "OK");

    int status;
    String name;

    HttpStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }
}
