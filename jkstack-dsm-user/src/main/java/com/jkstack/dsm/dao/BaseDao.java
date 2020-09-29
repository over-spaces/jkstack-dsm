package com.jkstack.dsm.dao;


import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.io.Serializable;

/**
 * CURD mapper封装
 */
public interface BaseDao<T, E extends Serializable> extends tk.mybatis.mapper.common.BaseMapper<T>, MySqlMapper<T>, IdsMapper<T> {

}
