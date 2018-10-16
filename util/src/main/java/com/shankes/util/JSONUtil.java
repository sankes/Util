package com.shankes.util;

import android.util.Log;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("deprecation")
public class JSONUtil {
    private static final String TAG = "JSONUtil";

    private static SimpleDateFormat sDefaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 指定时间格式
     */
    private static JacksonObject objectMapper = new JacksonObject(sDefaultFormat);

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @return JSON字符串
     */
    public static String serialize(Object object) {
        return serialize(object, sDefaultFormat);
    }

    /**
     * 将对象序列化为JSON字符串
     *
     * @param object
     * @param format
     * @return
     */
    public static String serialize(Object object, DateFormat format) {
        Writer write = new StringWriter();
        try {
            new JacksonObject(format).writeValue(write, object);
        } catch (JsonGenerationException e) {
            Log.d(TAG, "JsonGenerationException when serialize object to json:" + e);
        } catch (JsonMappingException e) {
            Log.d(TAG, "JsonMappingException when serialize object to json:" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException when serialize object to json:" + e);
        }
        return write.toString();
    }

    public static <E> List<E> deserializeList(String json, Class<E> clazz) {
        List<E> list = new ArrayList<>();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            list = objectMapper.readValue(json, javaType);
        } catch (JsonGenerationException e) {
            Log.d(TAG, "JsonGenerationException when deserialize json to object:" + e);
        } catch (JsonMappingException e) {
            Log.d(TAG, "JsonMappingException when deserialize json to object:" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException when deserialize json to object:" + e);
        }
        return list;
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @param format 指定时间格式
     * @return JSON字符串
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, Class<T> clazz, DateFormat format) {
        if (json == null) {
            return null;
        }
        Object object = null;
        try {
            object = new JacksonObject(format).readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonGenerationException e) {
            Log.d(TAG, "JsonGenerationException when deserialize json to object:" + e);
        } catch (JsonMappingException e) {
            Log.d(TAG, "JsonMappingException when deserialize json to object:" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException when deserialize json to object:" + e);
        }
        return (T) object;
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, Class<T> clazz) {
        if (json == null) {
            return null;
        }
        Object object = null;
        try {
            object = objectMapper.readValue(json, TypeFactory.rawClass(clazz));
        } catch (JsonGenerationException e) {
            Log.d(TAG, "JsonGenerationException when deserialize json to object:" + e);
        } catch (JsonMappingException e) {
            Log.d(TAG, "JsonMappingException when deserialize json to object:" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException when deserialize json to object:" + e);
        }
        return (T) object;
    }

    /**
     * 将JSON字符串反序列化为对象
     *
     * @param json
     * @return JSON字符串
     */
    @SuppressWarnings("unchecked")
    public static <T> T deserialize(String json, TypeReference<T> typeRef) {
        try {
            return (T) objectMapper.readValue(json, typeRef);
        } catch (JsonGenerationException e) {
            Log.d(TAG, "JsonGenerationException when deserialize json to object:" + e);
        } catch (JsonMappingException e) {
            Log.d(TAG, "JsonMappingException when deserialize json to object:" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException when deserialize json to object:" + e);
        }
        return null;
    }

    public static <T> List<T> deserializeList(InputStream inputStream, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            list = objectMapper.readValue(inputStream, javaType);
        } catch (JsonGenerationException e) {
            Log.d(TAG, "JsonGenerationException when deserialize json to object:" + e);
        } catch (JsonMappingException e) {
            Log.d(TAG, "JsonMappingException when deserialize json to object:" + e);
        } catch (IOException e) {
            Log.d(TAG, "IOException when deserialize json to object:" + e);
        }
        return list;
    }
}
