package com.jkstack.dsm.user.entity;

import java.util.Arrays;

/**
 * 用户状态
 * @author lifang
 * @since 2020/10/12
 */
public enum UserStatusEnum {

    /**
     * 用户状态-在职
     */
    IN_SERVICE("在职"),

    /**
     * 用户状态-休假
     */
    LEAVE("休假"),

    /**
     * 用户状态-离职
     */
    RESIGN("离职");

    public String text;

    UserStatusEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static String[] getStatusTextArray(){
        return Arrays.stream(UserStatusEnum.values())
                .map(UserStatusEnum::getText)
                .toArray(String[]::new);
    }
}
