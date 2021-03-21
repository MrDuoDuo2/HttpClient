package org.geekcamp.http.client.header;

public class HeaderParser {
    public static void parserList(Header header) {
        if (header.getName().equals("User-Agent")) {
            return;
        }

        String[] parts = header.getValue().split(",");

        if (parts.length == 1) {
            return;
        }

        for (String part : parts) {
            // TODO 20210321作业，新建Class保存 "application/xml;q=0.9,"的属性
            header.addListValue(part.trim());
        }
    }

    public static void parserCookie(Header header) {
        if (!header.getName().equals("Cookie")) {
            return;
        }

        final String delimiter = "; ";
        final String headerValue = header.getValue();

        final String[] pairs = headerValue.split(delimiter);

        for (String pair : pairs) {
            final int index = pair.indexOf("=");

            final String name = pair.substring(0, index);
            final String value = pair.substring(index + 1);
            header.addCookie(name, value);
        }
    }
}
