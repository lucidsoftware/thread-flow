package com.lucidchart.threadflow;

import java.util.HashMap;
import java.util.Map;

/**
 * A store that is identified by a name. Values are shared among any NamedStores with the same name.
 */
public class NamedStore implements FlowStore {

    private static final ThreadLocal<Map<String, String>> flowIds = new ThreadLocal<Map<String, String>>() {
        protected Map<String, String> initialValue() {
            return new HashMap<>();
        }
    };

    public final String name;

    public NamedStore(String name) {
        this.name = name;
    }

    public String get() {
        return flowIds.get().get(name);
    }

    public void set(String value) {
        if (value == null) {
            flowIds.get().remove(name);
        } else {
            flowIds.get().put(name, value);
        }
    }

}
