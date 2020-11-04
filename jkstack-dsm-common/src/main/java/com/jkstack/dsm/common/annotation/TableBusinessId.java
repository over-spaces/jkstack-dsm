package com.jkstack.dsm.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，标注表的业务ID
 * @author lifang
 * @since 2020/10/19
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface TableBusinessId {

    /**
     * 业务ID前缀，方便辨认。
     */
    String prefix() default "";

}
