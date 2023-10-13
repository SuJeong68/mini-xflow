package com.nhnacademy.aiot.http;

import java.util.HashMap;
import java.util.Map;

public class Request {
    private static final String CRLF = "\r\n";
    private String method;
    private String path;
    private String version;
    private Map<String, String> headers;
    private StringBuilder body;

    public Request(String method, String path, String version) {
        this.method = method;
        this.path = path;
        this.version = version;
        headers = new HashMap<>();

        if (method.equals("POST") || method.equals("PUT")) {
            body = new StringBuilder();
        }
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void addBody(String line) {
        body.append(line);
    }

    public String getMethod() {
        return method;
    }

    public String getBody() {
        return body.toString();
    }

    public String getVersion() {
        return version;
    }

    public boolean isExistHeader(String key) {
        return headers.containsKey(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s %s %s", method, path, version)).append(CRLF);

        for (Map.Entry entry : headers.entrySet()) {
            sb.append(String.format("%s: %s", entry.getKey(), entry.getValue())).append(CRLF);
        }

        if (body != null) {
            sb.append(getBody()).append(CRLF);
        }
        return sb.toString();
    }
}
