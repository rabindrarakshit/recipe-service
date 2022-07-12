package com.cuisine.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;


public final class Serialization {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JodaModule());

    private Serialization() {
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
