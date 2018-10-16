package com.shankes.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 作者： yanshili
 * 日期： 2016/8/10 11:09
 * 邮箱： shili_yan@sina.com
 */
public class URLUtils {
    /**
     * URLEncoder charset
     */
    public static final String CHARSET = "UTF-8";

    public static Map objectToMap(Object obj) throws Exception {
        return JSONUtil.deserialize(JSONUtil.serialize(obj),Map.class);
    }

    /**
     * 网络地址参数的拼接
     * @param urlPrefix
     * @param queryString
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unchecked")
    public static String httpBuildQuery(String urlPrefix, Object queryString) throws UnsupportedEncodingException {
        Map map= null;
        try {
            map = objectToMap(queryString);
        } catch (Exception e) {
//            e.printStackTrace();
        }
        if (map==null||map.isEmpty()){
            return urlPrefix;
        }
        return urlPrefix+"?"+httpBuildQuery(map);
    }

    /**
     * 网络地址参数的拼接
     * @param urlPrefix
     * @param queryString
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String httpBuildQuery(String urlPrefix, Map<String, String> queryString) throws UnsupportedEncodingException {

        if (queryString==null||queryString.isEmpty()){
            return urlPrefix;
        }

        return urlPrefix+"?"+httpBuildQuery(queryString);
    }

    /**
     * 网络地址参数的拼接
     * @param queryString
     * @return  返回地址参数字符串，拼接：http://www.xxx.xx///..?+地址参数
     * @throws UnsupportedEncodingException
     */
    public static String httpBuildQuery(Map<String, String> queryString) throws UnsupportedEncodingException {
        if (queryString==null) return "";

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : queryString.entrySet()) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(URLEncoder.encode(e.getKey(), CHARSET)).append('=').append(URLEncoder.encode(e.getValue(), CHARSET));
        }

        return sb.toString();
    }
}
