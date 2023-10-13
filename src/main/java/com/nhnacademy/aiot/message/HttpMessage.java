package com.nhnacademy.aiot.message;

import com.nhnacademy.aiot.http.Request;
import com.nhnacademy.aiot.http.Response;

public class HttpMessage extends Message {
    private Request request;
    private Response response;

    public HttpMessage(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }
}
