package org.example.config.table;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ColumnDefiner {
    private String title = "";
    private ColumnType type;
    private String dataIndex;
    private String key;
    private String renderFunction;
}
