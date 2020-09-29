package com.jkstack.dsm.utils;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href='fz_du@people2000.net'>dufazuo</a>
 * @version 1.0
 * @Title：健康云平台
 * @Description：JsonUtil JSON工具类
 * @Time 2014年10月28日 下午5:28:24 create
 */
public class GsonUtil {
    public static final Gson GSON = gsonBuilder().create();

    public static final Gson GSON_WITH_NULL = gsonBuilder().serializeNulls().create();

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    //转换数据类型出现 1 —>  1.0  慎用
    @Deprecated
    public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
        List<T> tList = Lists.newArrayList();
        JSONArray jsonArray = JSONArray.parseArray(json);
        jsonArray.forEach(a -> {
            T t = GsonUtil.GSON.fromJson(GsonUtil.GSON.toJson(a), clazz);
            tList.add(t);
        });
        return tList;
    }

    public static Map<Long, String> fromJson2Map(String json) {
        return StringUtil.isEmpty(json) ? Maps.newLinkedHashMap() : GsonUtil.GSON
                .fromJson(json, new TypeToken<Map<Long, String>>() {
                }.getType());
    }

    public static Map<String, String> fromJson3Map(String json) {
        return StringUtil.isEmpty(json) ? Maps.newLinkedHashMap() : GsonUtil.GSON
                .fromJson(json, new TypeToken<Map<String, String>>() {
                }.getType());
    }

    private static GsonBuilder gsonBuilder() {
        ExclusionStrategy strategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                Annotation annotation = f.getAnnotation(JsonIgnore.class);
                return annotation != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                Annotation annotation = clazz.getAnnotation(JsonIgnore.class);
                return annotation != null;
            }
        };

        return new GsonBuilder()
                //.excludeFieldsWithModifiers(Modifier.TRANSIENT)
                .setExclusionStrategies(strategy)
                .registerTypeAdapter(Double.class, (JsonSerializer<Double>)
                        (src, type, jsonSerializer) ->
                                new JsonPrimitive(NumberUtil.formatDouble2String(src)))
                .registerTypeAdapter(Double.class, (JsonDeserializer<Double>)
                        (json, typeOfT, context) ->
                                NumberUtil.formatString2Double(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>)
                        (json, typeOfT, context) ->
                                new Date(json.getAsJsonPrimitive().getAsLong()))
                .registerTypeAdapter(Long.class, (JsonDeserializer<Long>)
                        (json, typeOfT, context) ->
                                StringUtils.isEmpty(json.getAsJsonPrimitive().getAsString())
                                        ? null : json.getAsJsonPrimitive().getAsLong())
                .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
                        if (src == src.longValue()) {
                            return new JsonPrimitive(src.longValue());
                        }
                        return new JsonPrimitive(src);
                    }
                })
                .registerTypeAdapter(Date.class, new DateSerializer());
    }

    static class DateSerializer implements JsonSerializer<Date> {

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime());
        }
    }

}
