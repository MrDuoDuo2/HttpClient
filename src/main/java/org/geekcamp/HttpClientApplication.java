package org.geekcamp;

import org.geekcamp.http.client.Http;
import org.geekcamp.http.client.HttpRequest;
import org.geekcamp.http.client.HttpResponse;
import org.geekcamp.http.client.HttpType;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;

public class HttpClientApplication {
    public static void main(String[] args) throws Exception {
        HttpRequest httpRequest = (HttpRequest) build("src/main/resources/request.txt",HttpType.REQUEST);
        System.out.println(httpRequest.getBody());
    }


    public static Http build(String filePath, HttpType httpType) throws Exception {

        // Class<HttpClientApplication> clazz = HttpClientApplication.class;
        // InputStream inputStream = clazz.getResourceAsStream("/request.txt");
        final String requestStream = Files.readString(Path.of(filePath));

        final int bodyIndex = requestStream.indexOf("\r\n\r\n");
        final String body = requestStream.substring(bodyIndex + 4);

        final int startLineIndex = requestStream.indexOf("\r\n");
        final String startLine = requestStream.substring(0, startLineIndex);

        final int headersBeginIndex = startLineIndex + 2;
        final int headersEndIndex = bodyIndex;
        final String headers = requestStream.substring(headersBeginIndex, headersEndIndex);

        HttpRequest httpRequest = null;
        HttpResponse httpResponse = null;
        if (httpType == HttpType.REQUEST) {
            httpRequest = new HttpRequest();
        } else if (httpType == HttpType.RESPONSE) {
            httpResponse = new HttpResponse();
        }
        LinkedList<String> stringLinkedList = new LinkedList<>();
        startLineParse(startLine, stringLinkedList);
        int index = 0;
        for (String s : stringLinkedList) {
            if (index == 0) {
                if (httpType == HttpType.REQUEST) {
                    httpRequest.setMethod(s);
                } else if (httpType == HttpType.RESPONSE) {
                    httpResponse.setHttpVersion(s);
                }
            }
            if (index == 1) {
                if (httpType == HttpType.REQUEST) {
                    int uriEndIndex = s.indexOf("?");
                    String uri =s.substring(0,uriEndIndex);
                    httpRequest.setUri(uri);
                    String params = s.substring(uriEndIndex+1);
                    uriParse(params,httpRequest);

                } else if (httpType == HttpType.RESPONSE) {
                    httpResponse.setStatusCode(Integer.valueOf(s));
                }
            }
            if (index == 2) {
                if (httpType == HttpType.REQUEST) {
                    httpRequest.setHttpVersion(s);
                } else if (httpType == HttpType.RESPONSE) {
                    httpResponse.setMessage(s);
                }
            }

            index++;
        }
        if (httpType == HttpType.REQUEST) {
            httpParse(headers, httpRequest);
            ByteBuffer byteBuffer = ByteBuffer.wrap(body.getBytes(StandardCharsets.UTF_8));
            httpRequest.setRawData(byteBuffer);
            return httpRequest;
        } else if (httpType == HttpType.RESPONSE) {
            httpParse(headers, httpResponse);
            ByteBuffer byteBuffer = ByteBuffer.wrap(body.getBytes(StandardCharsets.UTF_8));
            httpResponse.setRawData(byteBuffer);
            return httpResponse;
        }

        return null;
    }

    public static void uriParse(String uri, HttpRequest httpRequest){
        int keyEndIndex = uri.indexOf("=");
        int keyBeginIndex = 0;
        String key = uri.substring(keyBeginIndex,keyEndIndex);

        int valueEndIndex = uri.indexOf("&");
        int valueBeginIndex = keyEndIndex+1;


        if (valueEndIndex == -1){
            String value = uri.substring(valueBeginIndex);
            httpRequest.addParameter(key,value);
        }else {
            String value = uri.substring(valueBeginIndex, valueEndIndex);
            httpRequest.addParameter(key, value);
            String lastParams = uri.substring(valueEndIndex + 1);
            uriParse(lastParams, httpRequest);
        }

    }

    public static LinkedList<String> startLineParse(String startLine, LinkedList<String> startLineLinkedList) throws Exception {
        int startLineElementEndIndex = startLine.indexOf(" ");
        int startLineElementBeginIndex = 0;

        String startLineElement;
        if (startLineElementEndIndex == -1) {
            startLineElement = startLine.substring(startLineElementBeginIndex);
            startLineLinkedList.add(startLineElement);
            return startLineLinkedList;
        } else {
            startLineElement = startLine.substring(startLineElementBeginIndex, startLineElementEndIndex);
            startLineLinkedList.add(startLineElement);
            startLineElementBeginIndex = startLineElementEndIndex + 1;
            String lastStartLine = startLine.substring(startLineElementBeginIndex);
            startLineParse(lastStartLine, startLineLinkedList);
            return startLineLinkedList;
        }
    }

    public static void httpParse(String headers, Http http) {
        int headersBeginIndex = 0;
        int headersEndIndex = headers.indexOf("\r\n");

        String header;
        if (headersEndIndex == -1) {
            header = headers;

            int keyIndex = header.indexOf(":");
            String key = header.substring(0, keyIndex);
            String value = header.substring(keyIndex + 2);

            http.addHeader(key, value);
        } else {
            header = headers.substring(headersBeginIndex, headersEndIndex);

            int keyIndex = header.indexOf(":");
            String key = header.substring(0, keyIndex);
            String value = header.substring(keyIndex + 2);

            http.addHeader(key, value);

            headersBeginIndex = headersEndIndex + 2;
            String lastHeader = headers.substring(headersBeginIndex);

            httpParse(lastHeader, http);
        }
    }
}
