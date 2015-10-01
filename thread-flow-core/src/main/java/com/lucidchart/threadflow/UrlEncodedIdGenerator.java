package com.lucidchart.threadflow;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UrlEncodedIdGenerator {

    private final AlphaNumericIdGenerator idGenerator;

    public UrlEncodedIdGenerator(AlphaNumericIdGenerator idGenerator) {
        this.idGenerator = idGenerator;
    }

    private static String encode(String value) {
        try {
            return URLEncoder.encode(value, "utf-8")
                .replace("+", "%20")
                .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String createId(Iterable<? extends ParameterPair> parameters) {
        StringBuilder sb = new StringBuilder();
        sb.append("id")
            .append("=")
            .append(idGenerator.createId());
        for (ParameterPair parameter : parameters) {
            sb.append("&")
                .append(encode(parameter.value))
                .append("=")
                .append(encode(parameter.name));
        }
        return sb.toString();
    }

}
