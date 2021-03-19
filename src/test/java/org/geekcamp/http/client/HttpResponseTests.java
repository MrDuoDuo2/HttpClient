package org.geekcamp.http.client;

import org.geekcamp.HttpClientApplication;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpResponseTests {
    @Test
    void allTests() throws Exception {
        HttpResponse httpResponse = (HttpResponse) HttpClientApplication.build("src/main/resources/response.txt",HttpType.RESPONSE);

        //HttpVersion Test
//        httpResponse.setHttpVersion("HTTP/1.1");
        System.out.print("httpResponse.getHttpVersion equals HTTP/1.1 Testing...");
        assertEquals("HTTP/1.1",httpResponse.getHttpVersion());
        System.out.println("OK");

        //StatusCode Test
//        httpResponse.setStatusCode(200);
        System.out.print("httpResponse.getStatusCode equals 200 Testing...");
        assertEquals(200,httpResponse.getStatusCode());
        System.out.println("OK");

        //HttpMessage Test
//        httpResponse.setMessage("OK");
        System.out.print("httpResponse.getMessage equals OK Testing...");
        assertEquals("OK",httpResponse.getMessage());
        System.out.println("OK");

        //Header Test
//        httpResponse.addHeader("Server","nginx/1.18.0");
        System.out.print("httpResponse.getHeaders().get equals nging/1.18.0 Testing...");
        assertEquals("nginx/1.18.0",httpResponse.getHeaders().get("Server"));
        System.out.println("OK");

        //RawData Test and getBody Test
//        final ByteBuffer byteBuffer = ByteBuffer.wrap("Hello world!".getBytes(StandardCharsets.UTF_8));
//        httpResponse.setRawData(byteBuffer);
//        assertEquals("Hello world!",httpResponse.getBody());
    }
}
