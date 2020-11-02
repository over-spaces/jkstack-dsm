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
     * 中文字母组合，中文+字母
     */
    private static final Pattern REG_CHINESE_LETTER = Pattern.compile("^[a-zA-Z\\u4E00-\\u9FA5]+$");

    /**
     * 字母+数字
     */
    private static final Pattern REG_LETTER_DIGITAL = Pattern.compile("^[a-zA-Z\\d+]+$");

    /**
     * 是否是email格式
     */
    public static boolean isEmail(CharSequence value){
        return Validator.isEmail(value);
    }

    /**
     * 是否电话
     */
    public static boolean isPhone(CharSequence value){
        return Validator.isMatchRegex(REG_PHONE, value);
    }

    /**
     * 是否是中文+字母组合
     */
    public static boolean isChineseAndLetter(CharSequence value){
        return Validator.isMatchRegex(REG_CHINESE_LETTER, value);
    }

    /**
     * 是否字母+数字组合
     */
    public static boolean isLetterAndDigital(CharSequence value){
        return Validator.isMatchRegex(REG_LETTER_DIGITAL, value);
    }
}
