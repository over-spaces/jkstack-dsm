package com.jkstack.dsm.common.utils;

import cn.hutool.core.lang.Validator;

import java.util.regex.Pattern;

/**
 * @author lifang
 * @since 2020/11/2
 */
public final class ValidatorUtil {

    /**
     * 电话号码校验
     */
    private static final Pattern REG_PHONE = Pattern.compile("^[0-9-()（）-]{1,20}$");

    /**
     * 名称校验，中文+字母
     */
    private static final Pattern REG_NAME = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]{1,20}$");

    /**
     * 是否是email格式
     */
    public static boolean isEmail(CharSequence value){
        return Validator.isEmail(value);
    }

    public static boolean isPhone(CharSequence value){
        return Validator.isMatchRegex(REG_PHONE, value);
    }

    public static boolean isName(CharSequence value){
        return Validator.isMatchRegex(REG_NAME, value);
    }

}
