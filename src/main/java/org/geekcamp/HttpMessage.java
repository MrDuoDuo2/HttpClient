package main.java.org.geekcamp;

import java.util.HashMap;

public class HttpMessage {
    HashMap<HttpClientApplication.startLineElement, String> startLine;
    HashMap<String,String> header = new HashMap<>();
    String body;

    public HttpMessage() {
    }

    public HashMap<HttpClientApplication.startLineElement, String> getStartLine() {
        return startLine;
    }

    public void setStartLine(HashMap<HttpClientApplication.startLineElement, String> startLine) {
        this.startLine = startLine;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
