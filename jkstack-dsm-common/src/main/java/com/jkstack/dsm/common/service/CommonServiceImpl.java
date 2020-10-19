package com.jkstack.dsm.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import com.jkstack.dsm.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author lifang
 * @since 2020/10/19
 */
public abstract class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CommonService<T> {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Override
    public T getByBusinessId(String businessId) {
        Field field = getBusinessIdField();
        if(field == null){
            logger.error("{}, 未知的业务ID字段.", entityClass.getName());
            return null;
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtil.convertCamelToUnder(field.getName()), businessId);
        return baseMapper.selectOne(wrapper);
    }

    private Field getBusinessIdField(){
        Field[] fields = entityClass.getDeclaredFields();

        if(fields.length == 0) {
            return null;
        }

        return Arrays.stream(fields)
                .filter(field -> Objects.nonNull(field.getAnnotation(TableBusinessId.class)))
                .findFirst()
                .orElse(null);
    }
}
