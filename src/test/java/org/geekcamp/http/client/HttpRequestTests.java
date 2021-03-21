package org.geekcamp.http.client;

import org.geekcamp.http.client.header.Header;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class HttpRequestTests {
    @Test
    void allTests() throws Exception {
        final String filename = "/home/lyuqiang/workspace/HttpClient/src/main/resources/request.txt";
        final String content = Files.readString(Path.of(filename));

        HttpRequest httpRequest = HttpRequestParser.run(content);

        assertNotNull(httpRequest.getMethod());
        assertEquals("/api/login", httpRequest.getUri());
        assertEquals("HTTP/1.1", httpRequest.getHttpVersion());

        final String expectedBody = "username=foo&&password=123456&&type=1";
        assertEquals(expectedBody, httpRequest.getBody());

        Map<String, Header> headers = httpRequest.getHeaders();
        assertEquals(15, headers.size());

        assertEquals("localhost", headers.get("Host").getValue());
        assertEquals("keep-alive", headers.get("Connection").getValue());
        assertEquals("no-cache", headers.get("Pragma").getValue());
        assertEquals("no-cache", headers.get("Cache-Control").getValue());
        assertEquals("1", headers.get("Upgrade-Insecure-Requests").getValue());
        assertEquals("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                "Chrome/87.0.4280.66 Safari/537.36", headers.get("User-Agent").getValue());

        List<String> expectedAccept = Arrays.asList("text/html",
                                                    "application/xhtml+xml",
                                                    "application/xml;q=0.9",
                                                    "image/avif",
                                                    "image/webp",
                                                    "image/apng",
                                                    "*/*;q=0.8",
                                                    "application/signed-exchange;v=b3;q=0.9");
        assertEquals(expectedAccept, headers.get("Accept").getListValue());

        assertEquals("none", headers.get("Sec-Fetch-Site").getValue());
        assertEquals("navigate", headers.get("Sec-Fetch-Mode").getValue());
        assertEquals("?1", headers.get("Sec-Fetch-User").getValue());
        assertEquals("document", headers.get("Sec-Fetch-Dest").getValue());

        List<String> expectedAcceptEncoding = Arrays.asList("gzip", "deflate", "br");
        assertEquals(expectedAcceptEncoding, headers.get("Accept-Encoding").getListValue());

        List<String> expectedAcceptLanguage = Arrays.asList("zh-CN", "zh;q=0.9", "en;q=0.8");
        assertEquals(expectedAcceptLanguage, headers.get("Accept-Language").getListValue());

        assertEquals("298zf09hf012fh2", headers.get("Cookie").getCookie("PHPSESSID"));
        assertEquals("u32t4o3tb3gg43", headers.get("Cookie").getCookie("csrftoken"));
        assertEquals("1", headers.get("Cookie").getCookie("_gat"));

        assertEquals("fsdfosuoufdsfsdfsf9u", headers.get("Auth").getValue());

    }
}
