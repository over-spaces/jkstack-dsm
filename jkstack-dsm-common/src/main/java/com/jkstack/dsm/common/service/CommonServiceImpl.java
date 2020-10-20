package com.jkstack.dsm.common.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jkstack.dsm.common.annotation.TableBusinessId;
import com.jkstack.dsm.common.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author lifang
 * @since 2020/10/19
 */
public abstract class CommonServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements CommonService<T> {

    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

    /**
     * 根据业务ID查询
     * @param businessId 表的业务ID，非主键ID
     */
    @Override
    public T getByBusinessId(String businessId) {
        Field field = getBusinessIdField();
        if(Objects.isNull(field)){
            logger.error("{}, 未知的业务ID字段.", entityClass.getName());
            return null;
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtil.convertCamelToUnder(field.getName()), businessId);
        return baseMapper.selectOne(wrapper);
    }

    /**
     * 根据业务ID批量查询
     * @param businessIds 表业务ID集合
     */
    @Override
    public List<T> listByBusinessIds(Collection<String> businessIds) {
        if(CollectionUtils.isEmpty(businessIds)){
            return Collections.emptyList();
        }
        Field field = getBusinessIdField();
        if(Objects.isNull(field)){
            logger.error("{}, 未知的业务ID字段.", entityClass.getName());
            return Collections.emptyList();
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.in(StringUtil.convertCamelToUnder(field.getName()), businessIds);
        return baseMapper.selectList(wrapper);
    }

    /**
     * 根据业务ID删除记录
     * @param businessId
     */
    @Override
    public void removeByBusinessId(String businessId) {
        Field field = getBusinessIdField();
        if(Objects.isNull(field)){
            logger.error("{}, 未知的业务ID字段.", entityClass.getName());
            return;
        }
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtil.convertCamelToUnder(field.getName()), businessId);
        baseMapper.delete(wrapper);
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