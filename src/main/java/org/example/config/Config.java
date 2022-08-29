package org.example.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.example.config.common.Field;
import org.example.config.search.SearchConfig;
import org.example.config.table.TableConfig;

import java.util.Set;

@Data
@Accessors(chain = true)
public class Config {
    private String pageName;
    private Set<Field> fields;
    private SearchConfig searchConfig;
    private TableConfig tableConfig;
    private boolean newFormOn;
}
