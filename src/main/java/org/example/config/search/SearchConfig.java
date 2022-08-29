package org.example.config.search;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SearchConfig {
    private boolean resetOn;
    private List<Definer> definers;
}
