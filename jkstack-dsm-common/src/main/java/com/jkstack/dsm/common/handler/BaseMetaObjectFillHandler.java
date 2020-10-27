package com.jkstack.dsm.common.handler;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;

/**
 * mybatis-plus 自动填充属性值
 *
 * @author lifang
 * @since 2020/10/14
 */
@Component
public class BaseMetaObjectFillHandler implements MetaObjectHandler {

    /**
     * 数据库表insert操作，自动填充属性值
     *
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("modifyTime", new Date(), metaObject);

        Class<?> clazz = metaObject.getOriginalObject().getClass();
        Field businessField = getTableBusinessField(clazz);
        if(businessField != null){
            this.setFieldValByName(businessField.getName(), IdUtil.objectId(), metaObject);
        }
    }

    /**
     * 数据库表update操作，自动填充属性值
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("modifyTime", new Date(), metaObject);
    }

    private Field getTableBusinessField(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            TableBusinessId tableBusinessId = field.getDeclaredAnnotation(TableBusinessId.class);
            TableField tableField = field.getDeclaredAnnotation(TableField.class);

            if (tableBusinessId == null || tableField == null) {
                return null;
            }

            if(tableField.fill() == FieldFill.INSERT || tableField.fill() == FieldFill.INSERT_UPDATE){
                return field;
            }
        }
        return null;
    }
}
