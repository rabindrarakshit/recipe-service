package com.cuisine.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestUtil {
    public static <T> T getObject(String filePath, Class<T> clazz) {
        try {
            String json = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
            return Serialization.jsonToObject(json, clazz);
        } catch (IOException e) {
            return null;
        }
    }
}
