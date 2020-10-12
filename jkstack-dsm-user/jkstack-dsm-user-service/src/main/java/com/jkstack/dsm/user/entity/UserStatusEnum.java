package com.jkstack.dsm.user.entity;

/**
 * 用户状态
 * @author lifang
 * @since 2020/10/12
 */
public enum UserStatusEnum {

    IN_SERVICE("在职"),

    LEAVE("休假"),

    RESIGN("离职");

    public String text;

    UserStatusEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
