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
        assertEquals("HTTP/1.1",httpResponse.getHttpVersion());

        //StatusCode Test
//        httpResponse.setStatusCode(200);
        assertEquals(200,httpResponse.getStatusCode());

        //HttpMessage Test
//        httpResponse.setMessage("OK");
        assertEquals("OK",httpResponse.getMessage());

        //Header Test
//        httpResponse.addHeader("Server","nginx/1.18.0");
        assertEquals("nginx/1.18.0",httpResponse.getHeaders().get("Server"));

        //RawData Test and getBody Test
//        final ByteBuffer byteBuffer = ByteBuffer.wrap("Hello world!".getBytes(StandardCharsets.UTF_8));
//        httpResponse.setRawData(byteBuffer);
        assertEquals("Hello world!",httpResponse.getBody());
    }
}
