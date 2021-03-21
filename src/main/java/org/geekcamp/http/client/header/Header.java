package org.geekcamp.http.client.header;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Header {
    String name;
    String value;
    List<String> listValue = new LinkedList<>();
    Map<String, String> cookies = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getListValue() {
        return listValue;
    }

    public void addListValue(String v) {
        this.listValue.add(v);
    }

    public String getCookie(String name) {
        return cookies.get(name);
    }

    public void addCookie(String name, String value) {
        this.cookies.put(name, value);
    }
}
