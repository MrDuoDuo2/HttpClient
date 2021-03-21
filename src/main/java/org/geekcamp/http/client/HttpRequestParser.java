package org.geekcamp.http.client;

import org.geekcamp.http.client.header.Header;
import org.geekcamp.http.client.header.HeaderParser;

public class HttpRequestParser {
    final static String oneCrlf = "\r\n";
    final static int oneCrlfSize = oneCrlf.length();

    final static String twoCrlf = "\r\n\r\n";
    final static int twoCrlfSize = twoCrlf.length();

    final static String space = " ";
    final static int spaceSize = space.length();

    final static String delimiter = ": ";
    final static int delimiterSize = delimiter.length();

    private static int parserStartLine(String requestStream, HttpRequest request) throws Exception {
        final int startLineIndex = requestStream.indexOf(oneCrlf);
        final String startLine = requestStream.substring(0, startLineIndex);

        final int firstSpaceindex = startLine.indexOf(space);
        final String method = startLine.substring(0, firstSpaceindex);
        request.setMethod(method);

        final int secondSpaceIndex = startLine.indexOf(space, firstSpaceindex + spaceSize);
        final String uri = startLine.substring(firstSpaceindex + spaceSize, secondSpaceIndex);
        request.setUri(uri);

        final String version = startLine.substring(secondSpaceIndex + spaceSize);
        request.setHttpVersion(version);

        return startLineIndex + oneCrlfSize;
    }

    private static void buildHeader(String headers, int lastIndex, HttpRequest request) throws Exception {
        final int lineEndIndex = headers.indexOf(oneCrlf, lastIndex);
        String headerStr;

        if (lineEndIndex == -1) {
            // 最后一句话没有"\r\n"，直接截取
            headerStr = headers.substring(lastIndex);
        } else {
            headerStr = headers.substring(lastIndex, lineEndIndex);
            lastIndex = lineEndIndex + oneCrlfSize;
        }

        final int kvIndex = headerStr.indexOf(delimiter);
        final String key = headerStr.substring(0, kvIndex);
        final String valueStr = headerStr.substring(kvIndex + delimiterSize);

        Header header = new Header();
        header.setName(key);
        header.setValue(valueStr);

        HeaderParser.parserCookie(header);
        HeaderParser.parserList(header);

        request.addHeader(key, header);

        if (lineEndIndex > 0) {
            // 如果出现"\r\n"则表示还有后续HTTP Header行
            buildHeader(headers, lastIndex, request);
        }
    }

    private static void parserHeaders(String requestStream,
                                      HttpRequest request,
                                      int startLineEndIndex,
                                      int bodyBeginIndex) throws Exception {
        final String headers = requestStream.substring(startLineEndIndex, bodyBeginIndex);
        buildHeader(headers, 0, request);
    }

    private static int parserBody(String requestStream, HttpRequest request) {
        final int bodyIndex = requestStream.indexOf(twoCrlf);
        final String body = requestStream.substring(bodyIndex + twoCrlfSize);
        request.setBody(body);

        return bodyIndex;
    }

    public static HttpRequest run(String requestStream) throws Exception {
        HttpRequest request = new HttpRequest();

        final int startLineEndIndex = parserStartLine(requestStream, request);
        final int bodyBeginIndex = parserBody(requestStream, request);
        parserHeaders(requestStream, request, startLineEndIndex, bodyBeginIndex);

        return request;
    }
}
