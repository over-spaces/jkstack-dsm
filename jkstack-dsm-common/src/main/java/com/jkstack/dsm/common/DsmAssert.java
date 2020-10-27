package com.jkstack.dsm.common;

import java.util.Collection;

/**
 * @author lifang
 * @since 2020/10/27
 */
public final class DsmAssert {

    public static void isFalse(boolean flag, String message) throws MessageException {
        if (!flag) {
            throw new MessageException(message);
        }
    }

    public static void isNull(Object value, String message) throws MessageException {
        if (value == null) {
            throw new MessageException(message);
        }
    }

    public static <T> void isEmpty(Collection<T> collection, String message) throws MessageException {
        if (collection == null || collection.size() == 0) {
            throw new MessageException(message);
        }
    }
}
