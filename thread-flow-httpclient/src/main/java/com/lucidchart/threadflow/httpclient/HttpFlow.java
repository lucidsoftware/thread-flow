package com.lucidchart.threadflow.httpclient;

import com.lucidchart.threadflow.BaseFlow;
import com.lucidchart.threadflow.FlowStore;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpFlow extends BaseFlow {

    public HttpFlow(FlowStore store) {
        super(store);
    }

    public HttpClientBuilder addFlowHeader(HttpClientBuilder builder) {
        return builder.addInterceptorFirst(new FlowHttpRequestInterceptor(getStore()));
    }

}
