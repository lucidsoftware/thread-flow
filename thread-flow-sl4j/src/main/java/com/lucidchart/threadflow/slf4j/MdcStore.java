package com.lucidchart.threadflow.slf4j;

import com.lucidchart.threadflow.FlowStore;
import org.slf4j.MDC;

public class MdcStore implements FlowStore {

    final private String key;

    public MdcStore(String key) {
        this.key = key;
    }

    public String get() {
        return MDC.get(key);
    }

    public void set(String value) {
        if (value == null) {
            MDC.remove(key);
        } else {
            MDC.put(key, value);
        }
    }

}
