package org.example.core;

import org.example.config.Config;
import org.example.config.common.Field;
import org.example.config.search.Definer;
import org.example.config.search.SearchConfig;
import org.example.config.table.ColumnDefiner;
import org.example.config.table.ColumnType;
import org.example.config.table.TableConfig;
import org.example.utils.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Render {
    public static String run(Config config) {
        String pageName = config.getPageName();
        String name = StringUtils.upperFirstChar(pageName);
        StringBuilder codeBuilder = new StringBuilder("<script setup lang=\"ts\">\n");
        codeBuilder.append("import { usePageView } from '~~/hooks'\n" +
                "import { BasePageReq } from '~~/types/common'");
        Set<Field> fields = config.getFields();
        Map<String, Field> fieldsMap = fields.stream().collect(Collectors.toMap(Field::getName, a -> a, (a, b) -> a));

        Code searchCode = buildSearchCode(name, fieldsMap, config);
        Code tableCode = buildTableCode(name, fieldsMap, config);
        codeBuilder.append("\n")
                .append(searchCode.getContent())
                .append("\n")
                .append(tableCode.getContent())
                .append("\n")
                .append(buildMainCode(searchCode, tableCode, config))
                .append("\n")
                .append("</script>");

        String templateCode = buildTemplateCode();
        codeBuilder.append("\n").append(templateCode);

        codeBuilder.append("\n").append("<style scoped></style>");
        return codeBuilder.toString();
    }

    private static String buildTemplateCode() {
        StringBuilder templateCodeBuilder = new StringBuilder();
        templateCodeBuilder.append("<template><div>");
        templateCodeBuilder.append("<a-table :dataSource=\"list\" :columns=\"columns\" :pagination=\"pagination\" @change=\"paginationChange\"></a-table>");
        templateCodeBuilder.append("</div></template>");
        return templateCodeBuilder.toString();
    }

    private static String buildMainCode(Code searchCode, Code tableCode, Config config) {
        StringBuilder mainCodeBuilder = new StringBuilder();

        String usePageView = String.format("const { list, refresh, paginationChange, pagination } " +
                        "= usePageView<%s, %s>('%s', %s)",
                tableCode.getInterfaceName(),
                searchCode.getInterfaceName(),
                config.getTableConfig().getPageUrl(),
                searchCode.getDefaultValueName());
        mainCodeBuilder.append("\n").append(usePageView);

        return mainCodeBuilder.toString();
    }

    /**
     * table部分代码
     *
     * @param name
     * @param fieldMap
     * @param config
     * @return
     */
    private static Code buildTableCode(String name, Map<String, Field> fieldMap, Config config) {
        StringBuilder tableCodeBuilder = new StringBuilder("\n");
        TableConfig tableConfig = config.getTableConfig();
        Set<String> definers = tableConfig.getDefiners();

        tableCodeBuilder.append("interface ").append(name).append("{");
        String content = definers.stream().map(i -> {
            Field field = fieldMap.get(i);
            return field.getName() + ":" + field.getType();
        }).collect(Collectors.joining("\n"));
        tableCodeBuilder.append(content).append("}");

        Set<ColumnDefiner> columnDefiners = tableConfig.getColumnDefiners();

        String columnsContent = columnDefiners.stream().map(i -> {
            if (i.getType().equals(ColumnType.field)) {
                Field field = fieldMap.get(i.getDataIndex());
                String title = i.getTitle();
                if (title.isEmpty()) {
                    title = field.getLabel();
                }
                return "{ title: '" + title + "', dataIndex:'" + i.getDataIndex() + "'}";
            } else if (i.getType().equals(ColumnType.custom)) {
                return "{ title: '" + i.getTitle() + "', key:'" + i.getKey() + "',customRender: " + i.getRenderFunction() + "}";
            }
            return "";
        }).filter(i -> !i.isEmpty()).collect(Collectors.joining(",\n"));
        tableCodeBuilder
                .append("\n\n")
                .append("const columns = [").append(columnsContent)
                .append(",{ title: '操作',key:'action'}")
                .append("]")
        ;

        return new Code().setContent(tableCodeBuilder.toString()).setInterfaceName(name);
    }

    /**
     * search 部分代码
     *
     * @param name
     * @param fieldsMap
     * @param config
     * @return
     */
    private static Code buildSearchCode(String name, Map<String, Field> fieldsMap, Config config) {

        // search部分
        SearchConfig searchConfig = config.getSearchConfig();
        List<Definer> definers = searchConfig.getDefiners();
        StringBuilder searchCodeBuilder = new StringBuilder();

        // 定义接口
        String interfaceName = name + "Filter";
        searchCodeBuilder.append("interface ").append(interfaceName).append(" extends BasePageReq { \n");
        String content = definers.stream().map(i -> i.getField() + ":" + fieldsMap.get(i.getField()).getType()).collect(Collectors.joining("\n"));
        searchCodeBuilder.append(content).append("\n }");

        // 定义默认值
        String defaultFilterName = StringUtils.camelToUnderscore(interfaceName).toUpperCase();
        String defaultValues = definers.stream().map(i -> {
            String type = fieldsMap.get(i.getField()).getType();
            String value = Optional.ofNullable(i.getDefaultValue()).map(Object::toString).orElse("null");
            if ("string".equals(type)) {
                value = "'" + value + "'";
            }
            return i.getField() + ":" + value;
        }).collect(Collectors.joining(",\n"));
        searchCodeBuilder
                .append("\n\n")
                .append("const ").append(defaultFilterName).append(": ")
                .append(interfaceName).append(" = {").append(defaultValues).append(" }");

        return new Code().setContent(searchCodeBuilder.toString())
                .setInterfaceName(interfaceName)
                .setDefaultValueName(defaultFilterName);
    }
}
