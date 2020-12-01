package com.msa.jrg.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    static ObjectMapper mapper = new ObjectMapper();

    protected static String toJson(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    protected static <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }
}
