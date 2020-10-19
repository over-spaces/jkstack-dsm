package com.jkstack.dsm.common.utils;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author lifang
 * @since 2020-10-10
 */
public final class StringUtil {

    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr){
        return StringUtils.containsIgnoreCase(str, searchStr);
    }

    /**
     * 驼峰转下划线
     * eg1: userName -> user_name
     */
    public static String convertCamelToUnder(String str){
        Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_UNDERSCORE);
        return converter.convert(str);
    }

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str){
        return StringUtils.isBlank(str);
    }

    public static List<String> splitToList(String separator, String str) {
        Iterable<String> iterable = Splitter.on(separator).split(str);
        return Lists.newArrayList(iterable);
    }
}
