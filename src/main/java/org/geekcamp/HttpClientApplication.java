package main.java.org.geekcamp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

public class HttpClientApplication {
    enum startLineElement {
        HttpMethod,
        HttpUri,
        HttpVersion
    }

    public static void main(String[] args) throws IOException {
        final String filename = "src/main/resources/request.txt";
        // Class<HttpClientApplication> clazz = HttpClientApplication.class;
        // InputStream inputStream = clazz.getResourceAsStream("/request.txt");
        final String requestStream = Files.readString(Path.of(filename));

        final int bodyIndex = requestStream.indexOf("\r\n\r\n");
        final String body = requestStream.substring(bodyIndex + 4);

        final int startLineIndex = requestStream.indexOf("\r\n");
        final String startLine = requestStream.substring(0, startLineIndex);

        final int headersBeginIndex = startLineIndex + 2;
        final int headersEndIndex = bodyIndex;
        final String headers = requestStream.substring(headersBeginIndex, headersEndIndex);

        HashMap<startLineElement, String> startLineMap = new HashMap<>();
        startLineParse(startLine, 0, startLineMap);

        HashMap<String, String> headerMap = new HashMap<>();
        httpParse(headers, headerMap);

        HttpMessage httpMessage = new HttpMessage();
        httpMessage.setStartLine(startLineMap);
        httpMessage.setHeader(headerMap);
        httpMessage.setBody(body);

        System.out.println(httpMessage.startLine.get(startLineElement.HttpMethod));
        System.out.println(httpMessage.header.get("Host"));
        System.out.println(httpMessage.body);
    }

    public static void startLineParse(String startLine, int index, HashMap<startLineElement, String> startLineMap) {
        startLineElement element = startLineElement.values()[index];

        int startLineElementEndIndex = startLine.indexOf(" ");
        int startLineElementBeginIndex = 0;

        if (startLineElementEndIndex == -1) {
            String httpMethod = startLine.substring(startLineElementBeginIndex);
            startLineMap.put(element, httpMethod);
        } else {
            String httpMethod = startLine.substring(startLineElementBeginIndex, startLineElementEndIndex);
            startLineMap.put(element, httpMethod);

            startLineElementBeginIndex = startLineElementEndIndex + 1;
            String lastStartLine = startLine.substring(startLineElementBeginIndex);
            index++;
            startLineParse(lastStartLine, index, startLineMap);
        }
    }

    public static void httpParse(String headers, HashMap<String, String> headerMap) {
        int headersBeginIndex = 0;
        int headersEndIndex = headers.indexOf("\r\n");

        String header;
        if (headersEndIndex == -1) {
            header = headers;

            int keyIndex = header.indexOf(":");
            String key = header.substring(0, keyIndex);
            String value = header.substring(keyIndex + 2);

            headerMap.put(key, value);
        } else {
            header = headers.substring(headersBeginIndex, headersEndIndex);

            int keyIndex = header.indexOf(":");
            String key = header.substring(0, keyIndex);
            String value = header.substring(keyIndex + 2);

            headerMap.put(key, value);

            headersBeginIndex = headersEndIndex + 2;
            String lastHeader = headers.substring(headersBeginIndex);

            httpParse(lastHeader, headerMap);
        }
    }
}
