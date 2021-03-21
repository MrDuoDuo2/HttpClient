package org.geekcamp.http.client;

import org.geekcamp.http.client.header.Header;

import java.util.HashMap;
import java.util.Map;

public class Http {
    private String httpVersion;
    private final Map<String, Header> headers = new HashMap<>();
    private String body;

    public String getHttpVersion() {
        return httpVersion;
    }

    public Map<String, Header> getHeaders() {
        return headers;
    }


    public void setHttpVersion(String v) {
        this.httpVersion = v;
    }

    public void addHeader(String name, Header header) {
        this.headers.put(name, header);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
