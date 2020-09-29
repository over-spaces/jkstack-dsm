package com.jkstack.dsm.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Collection;

public final class JsonUtils {
    private static ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    }

    public static <R> R parseObject(String json, Class<R> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.warn("Json deserialize failure:", e);
            return null;
        }
    }

    public static <R> R parseObject(byte[] json, Class<R> valueType) {
        try {
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            logger.warn("Json deserialize failure:", e);
            return null;
        }
    }

    public static <R> R parseObject(InputStream in, Class<R> valueType) {
        try {
            return objectMapper.readValue(in, valueType);
        } catch (Exception e) {
            logger.warn("Json deserialize failure:", e);
            return null;
        }
    }


    public static <R> Collection<R> parseArray(String json, Class<? extends Collection> collectionClass, Class<R> valueType) {
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructParametrizedType(Collection.class, collectionClass, valueType));
        } catch (Exception e) {
            logger.warn("Json deserialize failure .", e);
            return null;
        }
    }

    public static <R> Collection<R> parseArray(InputStream in, Class<? extends Collection> collectionClass, Class<R> valueType) {
        try {
            return objectMapper.readValue(in, objectMapper.getTypeFactory().constructParametrizedType(Collection.class, collectionClass, valueType));
        } catch (Exception e) {
            logger.warn("Json deserialize failure .", e);
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("Json serialize failure .", e);
            return null;
        }
    }


}
