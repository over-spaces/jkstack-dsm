package com.jkstack.dsm.utils;


import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Created by denni on 2016/8/1.
 */
public class NumberUtil {
    public static Long toEntity(String object) {
        Long id = NumberUtils.toLong(RegexUtil.matchId(object));
        if (id <= 0) {
            return null;
        }
        return id;
    }

    public static String getNum(String head) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        return head + format + "" + new Random().nextInt(1000);
    }

    public static String getNum() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String format = simpleDateFormat.format(new Date(System.currentTimeMillis()));
        return format + "" + new Random().nextInt(1000);
    }

    public static double formatDouble(double src) {
        BigDecimal b = BigDecimal.valueOf(src);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static String formatDouble2String(double src) {
        BigDecimal b = BigDecimal.valueOf(src);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static double formatString2Double(String src) {
        BigDecimal b = new BigDecimal(src);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static Integer toInteger(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        Double doubleValue = NumberUtils.toDouble(str);
        return doubleValue.intValue();
    }

    public static Long toLong(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        Double doubleValue = NumberUtils.toDouble(str);
        return doubleValue.longValue();
    }

    public static Long toLong(Integer integer) {
        if (integer == null) {
            return null;
        }
        return (long) integer;
    }

    public static long max(Long a, Long b) {
        if (a == null && b == null) {
            return 0;
        } else if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }
        return Math.max(a, b);
    }
}
