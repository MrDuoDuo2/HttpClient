package org.geekcamp.http.client;

import org.geekcamp.HttpClientApplication;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class HttpRequestTests {
    @Test
    void allTests() throws Exception {
        HttpRequest httpRequest = (HttpRequest) HttpClientApplication.build("src/main/resources/request.txt",HttpType.REQUEST);
//        HttpRequest httpRequest = new HttpRequest();

        //HttpMethod Test
//        assertThrows(Exception.class, () -> httpRequest.setMethod("FOO"));
//        assertDoesNotThrow(() -> httpRequest.setMethod("GET"));
        System.out.print("httpRequest.getMethod Testing...");
        assertNotNull(httpRequest.getMethod());
        System.out.println("OK");

        //HttpUri Test
//        httpRequest.setUri("/api/login");
        System.out.print("httpRequest.getUri equals /api/login Testing...");
        assertEquals("/api/login", httpRequest.getUri());
        System.out.println("OK");

        //HttpParameter Test
//        httpRequest.addParameter("user","test_user");
//        assertEquals("test_user",httpRequest.getParameters().get("user"));

        //HttpVersion Test
//        httpRequest.setHttpVersion("HTTP/1.1");
        System.out.print("httpRequest.getHttpVersion equals HTTP/1.1 Testing...");
        assertEquals("HTTP/1.1", httpRequest.getHttpVersion());
        System.out.println("OK");

        //RawData use List<Byte> Test
//        httpRequest.setRawData2();

        //Header Test
//        httpRequest.addHeader("Host", "localhost");
        System.out.print("httpRequest.getHeaders().get equals localhost Testing...");
        assertEquals("localhost", httpRequest.getHeaders().get("Host"));
        System.out.println("OK");

        //RawData use ByteBuffer Test and getBody Test
//        final ByteBuffer buffer = ByteBuffer.wrap("Hello world!".getBytes(StandardCharsets.UTF_8));
//        httpRequest.setRawData(buffer);
//        assertEquals("username=foo&&password=123456type=1", httpRequest.getBody());

        // TODO 将HTTP请求报文解析成HttpRequest对象
        // TODO 完成HTTP响应报文的解析，以及单元测试
    }
}
