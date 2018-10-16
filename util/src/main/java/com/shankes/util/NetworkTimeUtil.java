package com.shankes.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lzp on 2017/8/22.
 */

public class NetworkTimeUtil {

    //获得后台时的相对开始时间差值
    //当前系统时间比后台网络时间快的差值毫秒数
    public static long sysFasterNetTime =0;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 当前网络时间（long）
     * @return
     */
    public static long getCurrentTimeL(){
        return System.currentTimeMillis() + sysFasterNetTime;
    }

    /**
     * 当前网络时间（Date）
     * @return
     */
    public static Date getCurrentTimeD(){
        Date currentTimeD = new Date(getCurrentTimeL());
        return currentTimeD;
    }

    /**
     * 当前时间（String）
     * @return
     */
    public static String getCurrentTimeS(){
        String currentTimeS = sdf.format(getCurrentTimeD());
        return currentTimeS;
    }
}
