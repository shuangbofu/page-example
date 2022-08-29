package org.example.core;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Code {
    private String content;
    private String interfaceName;
    private String defaultValueName;
}
