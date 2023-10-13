package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.http.Request;
import com.nhnacademy.aiot.http.Response;
import com.nhnacademy.aiot.message.HttpMessage;
import com.nhnacademy.aiot.message.Message;
import com.nhnacademy.aiot.message.StringMessage;
import com.nhnacademy.aiot.wire.Wire;

public class HttpRequestParsingNode extends InputOutputNode {
    private static final String CRLF = "\r\n";

    @Override
    void process() {
        for (int i = 0; i < getInputWiresLength(); i++) {
            Wire inputWire = getInputWire(i);
            while (inputWire.hasMessage()) {
                Request request = receiveRequest(inputWire);
                output(new HttpMessage(request, new Response(request.getVersion())));
            }
        }
    }

    private Request receiveRequest(Wire inputWire) {
        String[] splited = ((StringMessage) inputWire.get()).getPayload().split(" ");
        Request request = new Request(splited[0], splited[1], splited[2]);

        Message message;
        while (inputWire.hasMessage()) {
            message = inputWire.get();
            String line;
            while (!(line = ((StringMessage) message).getPayload()).equals(CRLF)) {
                logger.debug(line);

                splited = line.trim().split(":");
                request.addHeader(splited[0], splited[1]);
            }

            if (request.getMethod().equals("POST") && request.isExistHeader("Content-Length")) {
                while (!(line = ((StringMessage) message).getPayload()).equals(CRLF)) {
                    request.addBody(line);
                }
            }
        }
        return request;
    }
}
