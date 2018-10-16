package com.shankes.util;

import android.text.TextUtils;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by Administrator on 2016/4/28.
 */
public class NumeralUtils {
    private static String TAG = "NumeralUtils";

    /**
     * 将字符串用符号分隔 例如：分隔前为 3538832322  分隔后为 3,538,832,322
     *
     * @param splitText 要分隔的文字
     * @param step      分隔的步距
     * @param symbol    分隔的符号
     * @return 返回分隔后的文字
     */
    public static String splitNum(String splitText, int step, String symbol) {
        if (splitText == null) {
            return "0";
        }

        splitText = (int) (Float.parseFloat(splitText) + 0.5f) + "";
        String prefix = splitText;
//        String suffix=null;
//        if (splitText.contains(".")
//                &&!splitText.endsWith(".")
//                &&!splitText.startsWith(".")
//                &&splitText.split("\\.").length==2){
//            prefix=splitText.split("\\.")[0];
//            suffix=splitText.split("\\.")[1];
//        }
//
//        if (prefix==null||prefix.length()<step){
//            return prefix;
//        }

        StringBuilder sb = new StringBuilder();
        int endPosition = prefix.length() % step;
        Log.i(TAG, "分隔前==" + prefix);
        if (endPosition > 0) {
            if (endPosition == 1) {
                sb.append(prefix.charAt(0));
            } else {
                sb.append(prefix.substring(0, endPosition));
            }
            prefix = prefix.substring(endPosition);
        } else {
            sb.append(prefix.substring(0, step));
            prefix = prefix.substring(step);
        }

        if (prefix != null && prefix.length() > 0) {
            for (int i = 0; i < prefix.length() - step + 1; i = i + step) {
                sb.append(symbol + prefix.substring(i, i + step));
            }
        }
//        if (suffix!=null){
//            sb.append("."+suffix);
//        }
        return sb.toString();
    }

    /**
     * 转为保留两位小数字符串
     *
     * @param d
     * @return
     */
    public static String decimalFormat(double d) {
        return decimalFormat(d, 2);
    }

    /**
     * 转为保留两位小数字符串
     *
     * @param d
     * @return
     */
    public static String decimalFormat(float d) {
        return decimalFormat(d, 2);
    }

    /**
     * 金额字符串转换为double类型，精度为2位小数
     *
     * @param doubleText
     * @return
     */
    public static double strToDouble(String doubleText) {
        if (TextUtils.isEmpty(doubleText)) {
            return 0.00;
        }
        return new BigDecimal(doubleText).setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 转为保留numDecimal位小数字符串
     *
     * @param d
     * @param numDecimal 小数位数
     * @return
     */
    public static String decimalFormat(double d, int numDecimal) {
        StringBuilder builder = new StringBuilder("######0");
        if (numDecimal > 0) {
            builder.append(".");
            for (int i = 0; i < numDecimal; i++) {
                builder.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(builder.toString());
        return df.format(d);
    }

    /**
     * 转为保留numDecimal位小数字符串
     *
     * @param d
     * @param numDecimal 小数位数
     * @return
     */
    public static String decimalFormat(float d, int numDecimal) {
        StringBuilder builder = new StringBuilder("######0");
        if (numDecimal > 0) {
            builder.append(".");
            for (int i = 0; i < numDecimal; i++) {
                builder.append("0");
            }
        }
        DecimalFormat df = new DecimalFormat(builder.toString());
        return df.format(d);
    }
}
