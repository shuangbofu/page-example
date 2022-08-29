package org.example.utils;

import com.google.common.base.CaseFormat;

public class StringUtils {
    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 结果
     */
    public static String upperFirstChar(String str) {
        char[] cs = str.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    /**
     * 驼峰转小写
     *
     * @param str 字符串
     * @return 结果
     */
    public static String camelToUnderscore(String str) {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, str);
    }
}
