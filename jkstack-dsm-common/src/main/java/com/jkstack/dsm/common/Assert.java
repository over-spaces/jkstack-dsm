package com.jkstack.dsm.common;

import java.util.Collection;

/**
 * @author lifang
 * @since 2020/10/27
 */
public final class Assert {

    public static void isFalse(boolean flag, String message) throws MessageException {
        if (!flag) {
            throw new MessageException(message);
        }
    }

    /**
     * value == null, throws exception
     * @param value 值
     * @param message 异常信息
     * @throws MessageException 提示异常类
     */
    public static void isNull(Object value, String message) throws MessageException {
        if (value == null) {
            throw new MessageException(message);
        }
    }

    /**
     * 空集合，抛异常。
     * @param collection 集合
     * @param message 异常信息
     * @param <T>
     * @throws MessageException 提示异常类
     */
    public static <T> void isEmpty(Collection<T> collection, String message) throws MessageException {
        if (collection == null || collection.size() == 0) {
            throw new MessageException(message);
        }
    }
}
