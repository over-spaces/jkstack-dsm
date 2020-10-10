package com.jkstack.dsm.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cheng_xu on 2016/1/20.
 */
public class RegexUtil {
    public static final String DISPLAY_ID_REGEX = ".*\\$\\{(\\d*)\\}";

    public static final String DISPLAY_NAME_REGEX = "(.*)\\$\\{\\d*\\}";

    public static final String JSON_TEXT_TEGEX = "(\\{.*\\})";

    public static String matchName(String line) {
        return match(line, DISPLAY_NAME_REGEX);
    }

    public static String matchId(String line) {
        return match(line, DISPLAY_ID_REGEX);
    }

    public static String matchJsonText(String line) {
        return match(line, JSON_TEXT_TEGEX, Pattern.DOTALL);
    }

    public static String match(String line, String pattern) {
        return match(line, pattern, 0);
    }

    public static String addIsNull(String line) {
        String item = match(line, "([^ ]*)");
        return "isnull(" + item + ") asc," + line;
    }

    public static String match(String line, String pattern, int flags) {
        if (StringUtils.isEmpty(line)) {
            return null;
        }
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern, flags);
        // 现在创建 matcher 对象
        Matcher m = r.matcher(line);
        if (m.find()) {
            return m.group(1);
        } else {
            return null;
        }
    }
}
