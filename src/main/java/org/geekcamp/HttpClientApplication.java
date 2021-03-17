package main.java.org.geekcamp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class HttpClientApplication {
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

        System.out.println(startLine);
        System.out.println(headers);
        System.out.println(body);
    }
}
