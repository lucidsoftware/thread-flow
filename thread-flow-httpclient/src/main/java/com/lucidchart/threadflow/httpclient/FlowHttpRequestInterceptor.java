package com.lucidchart.threadflow.httpclient;

import com.lucidchart.threadflow.FlowStore;
import java.io.IOException;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;

public class FlowHttpRequestInterceptor implements HttpRequestInterceptor {

    private final FlowStore store;

    public FlowHttpRequestInterceptor(FlowStore store) {
        this.store = store;
    }

    protected String getHeaderName() {
        return "X-Flow-Id";
    }

    public void process(HttpRequest request, HttpContext context) throws HttpException, IOException {
        String value = store.get();
        if (value != null) {
            request.addHeader(getHeaderName(), value);
        }
    }

}
