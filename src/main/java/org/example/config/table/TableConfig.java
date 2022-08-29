package org.example.config.table;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class TableConfig {
    private Set<String> definers;
    private Set<ColumnDefiner> columnDefiners;
    private boolean editFormOn;
    private boolean detailOn;
    private String pageUrl;
}
