package org.example.config.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Field {
    private String name;
    private String type;
    private String label;
}
