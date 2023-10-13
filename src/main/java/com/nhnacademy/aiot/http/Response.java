package com.nhnacademy.aiot.http;

public class Response {
    private String version;
    private HttpStatus httpStatus;

    public Response(String version) {
        this.version = version;
    }
}
