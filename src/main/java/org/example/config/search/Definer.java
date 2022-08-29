package org.example.config.search;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.config.common.Component;

@Data
@Accessors(chain = true)
public class Definer {
    private String field;
    private Object defaultValue;
    private Component component;
}
