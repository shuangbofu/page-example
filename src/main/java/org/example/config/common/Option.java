package org.example.config.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Option {
    private String label;
    private Object value;
}
