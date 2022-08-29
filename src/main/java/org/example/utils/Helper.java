package org.example.utils;

import lombok.Data;
import org.example.config.common.Component;

import java.util.Map;

@Data
public class Helper {

    private Map<String, Component> data;

    public static Helper of() {
        return new Helper();
    }

    public Helper set(String key, Component value) {
        data.put(key, value);
        return this;
    }

    public Map<String, Component> get() {
        return data;
    }
}