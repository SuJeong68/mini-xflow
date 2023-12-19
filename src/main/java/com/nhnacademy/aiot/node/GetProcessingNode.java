package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.http.Request;
import com.nhnacademy.aiot.message.HttpMessage;
import com.nhnacademy.aiot.wire.Wire;

public class GetProcessingNode extends InputOutputNode {

    @Override
    void process() {
        for (int i = 0; i < getInputWiresLength(); i++) {
            Wire inputWire = getInputWire(i);
            while (inputWire.hasMessage()) {
                HttpMessage message = (HttpMessage) inputWire.get();
                if (message.getRequest().getPath().equals("/dev")) {
                    Request request = new Request("GET", "/dev", message.getRequest().getVersion());
                    request.addHeader("Host", "ems.nhnacademy.com:1880");
                    message.setRequest(request);
                }
                output(message);
            }
        }
    }
}
