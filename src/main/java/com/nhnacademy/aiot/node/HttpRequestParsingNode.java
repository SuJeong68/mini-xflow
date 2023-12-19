package com.nhnacademy.aiot.node;

import com.nhnacademy.aiot.http.Request;
import com.nhnacademy.aiot.http.Response;
import com.nhnacademy.aiot.message.HttpMessage;
import com.nhnacademy.aiot.message.Message;
import com.nhnacademy.aiot.message.StringMessage;
import com.nhnacademy.aiot.wire.Wire;

public class HttpRequestParsingNode extends InputOutputNode {

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

        logger.debug("Init Request => {}", request);

        Message message = inputWire.get();

        String line;
        while (inputWire.hasMessage() && !(line = ((StringMessage) message).getPayload()).equals("")) {
            logger.debug("Header => {}", line);

            splited = line.trim().split(":");
            request.addHeader(splited[0], splited[1]);

            message = inputWire.get();
        }

        if (request.getMethod().equals("POST") && request.isExistHeader("Content-Length")) {
            while (inputWire.hasMessage() && !(line = ((StringMessage) message).getPayload()).equals("")) {
                request.addBody(line);

                message = inputWire.get();
            }
        }
        return request;
    }
}
