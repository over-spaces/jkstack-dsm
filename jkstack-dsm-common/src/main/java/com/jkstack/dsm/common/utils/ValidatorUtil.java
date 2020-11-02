package com.jkstack.dsm.common.utils;

import cn.hutool.core.lang.Validator;

/**
 * @author lifang
 * @since 2020/11/2
 */
public final class ValidatorUtil {

    /**
     * 是否是email格式
     */
    public static boolean isEmail(CharSequence value){
        return Validator.isEmail(value);
    }

}
