package org.example.config.search;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.config.common.Option;

import java.util.List;

@Data
@Accessors(chain = true)
public class OptionData {
    /**
     * 从接口中获取
     */
    private String dataUrl;
    private String mapFunction;
    /**
     * 数据
     */
    private List<Option> data;
}
