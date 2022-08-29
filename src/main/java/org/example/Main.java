package org.example;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.example.config.Config;
import org.example.config.common.Component;
import org.example.config.common.ComponentType;
import org.example.config.common.Field;
import org.example.config.common.Option;
import org.example.config.search.Definer;
import org.example.config.search.OptionData;
import org.example.config.search.SearchConfig;
import org.example.config.table.ColumnDefiner;
import org.example.config.table.ColumnType;
import org.example.config.table.TableConfig;
import org.example.core.Render;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        Config config = defineConfig();
        String code = Render.run(config);
        Files.writeString(Path.of("/Users/shuangbofu/Desktop/1.vue"), code);
    }


    public static Config defineConfig() {
        return new Config()
                .setPageName("storeList")
                .setFields(Sets.newHashSet(
                        new Field().setName("id").setType("number").setLabel("店铺ID"),
                        new Field().setName("nick").setType("string").setLabel("店铺名称"),
                        new Field().setName("username").setType("string").setLabel("店铺账号"),
                        new Field().setName("merchant_type_lable").setType("string").setLabel("商家类型"),
                        new Field().setName("type_name1").setType("string").setLabel(""),
                        new Field().setName("type_name2").setType("string").setLabel(""),
                        new Field().setName("type_name3").setType("string").setLabel(""),
                        new Field().setName("address").setType("string").setLabel(""),
                        new Field().setName("province_name").setType("string").setLabel(""),
                        new Field().setName("city_name").setType("string").setLabel(""),
                        new Field().setName("area_name").setType("string").setLabel(""),
                        new Field().setName("contact_phone").setType("string").setLabel("联系人"),
                        new Field().setName("merchant_type").setType("number").setLabel("类型")
                ))
                .setSearchConfig(new SearchConfig()
                        .setResetOn(true)
                        .setDefiners(Lists.newArrayList(
                                new Definer().setField("nick")
                                        .setComponent(new Component().setType(ComponentType.INPUT))
                                        .setDefaultValue(""),
                                new Definer().setField("merchant_type")
                                        .setComponent(new Component()
                                                .setType(ComponentType.SELECT)
                                                .setOptionData(new OptionData()
                                                        .setData(Lists.newArrayList(
                                                                new Option().setLabel("停车场").setValue(10),
                                                                new Option().setLabel("普通商家").setValue(0),
                                                                new Option().setLabel("运营商").setValue(9)
                                                        ))))
                                        .setDefaultValue(null)
                        ))
                )
                .setTableConfig(new TableConfig()
                        .setDefiners(
                                Sets.newHashSet("id", "nick", "username", "merchant_type_lable"))
                        .setColumnDefiners(
                                Sets.newHashSet(
                                        new ColumnDefiner().setDataIndex("id").setType(ColumnType.field),
                                        new ColumnDefiner().setDataIndex("nick").setType(ColumnType.field),
                                        new ColumnDefiner().setDataIndex("username").setType(ColumnType.field),
                                        new ColumnDefiner().setDataIndex("merchant_type_lable").setType(ColumnType.field),
                                        new ColumnDefiner().setTitle("行业分类")
                                                .setType(ColumnType.custom).setKey("type").setRenderFunction(" ({ record }) => [record.type_name1, record.type_name2, record.type_name3].filter(i => i != null).join('/')"),
                                        new ColumnDefiner().setTitle("店铺地址")
                                                .setType(ColumnType.custom)
                                                .setKey("address")
                                                .setRenderFunction("({record})=>record.province_name+record.city_name+record.area_name"),
                                        new ColumnDefiner().setDataIndex("contact_phone").setType(ColumnType.field)
                                )
                        ).setEditFormOn(true)
                        .setDetailOn(true)
                        .setPageUrl("/shopinfo/findSubShopperListForPage")
                )
                .setNewFormOn(true);
    }
}