package com.jkstack.dsm.sheet.enums;

import java.util.Arrays;

/**
 * 用户状态
 * @author lifang
 * @since 2020/10/12
 */
public enum FileTypeEnum {

    /**
     * 图片
     */
    IMAGE("image"),

    /**
     * 文件
     */
    FILE("file");

    public String text;

    FileTypeEnum(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static String[] getStatusTextArray(){
        return Arrays.stream(FileTypeEnum.values())
                .map(FileTypeEnum::getText)
                .toArray(String[]::new);
    }
}
