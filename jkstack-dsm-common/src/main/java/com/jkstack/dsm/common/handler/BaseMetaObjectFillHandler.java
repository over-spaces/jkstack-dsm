package com.jkstack.dsm.common.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-plus 自动填充属性值
 * @author lifang
 * @since 2020/10/14
 */
@Component
public class BaseMetaObjectFillHandler implements MetaObjectHandler {

    /**
     * 数据库表insert操作，自动填充属性值
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("modifyTime",new Date(),metaObject);
    }

    /**
     * 数据库表update操作，自动填充属性值
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifyTime",new Date(),metaObject);
    }
}
