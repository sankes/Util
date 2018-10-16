package com.shankes.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.DateFormat;

/**
 * 配置好的json解析工具类
 */
public class JacksonObject extends ObjectMapper {
    public JacksonObject(DateFormat format) {
        if (format!=null) {
            setDateFormat(format);
        }else {
            configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        }
        //值为null属性不参与序列化
        setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //解析器支持解析单引号
        configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
        //解析器支持解析结束符
        configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //解析时未知的属性会忽略掉，
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

}
