package org.example.config.common;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.config.search.OptionData;

@Data
@Accessors(chain = true)
public class Component {
    private ComponentType type;
    private OptionData optionData;
}
