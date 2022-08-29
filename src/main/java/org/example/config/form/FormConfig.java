package org.example.config.form;

import lombok.Data;
import org.example.config.common.Component;

import java.util.List;
import java.util.Map;

@Data
public class FormConfig {
    private Map<String, String> interfaceDefiner;
    private List<Component> components;
}
