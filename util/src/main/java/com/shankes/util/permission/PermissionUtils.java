package com.shankes.util.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：mooney
 * 日期：2018/4/25
 * 邮箱：yanshilioo@gmail.com
 * 描述：
 */

class PermissionUtils {

    private static List<String> deniedPermission;

    // 只进行静态方法的调用，不让别人实例化对象
    private PermissionUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断版本是否是6.0以上 Marshmallow（棉花糖）
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 获取所有没有授予或者拒绝的权限
     *
     * @param activity           Activity / Fragment
     * @param requestPermissions
     * @return
     */
    public static List<String> getDeniedPermissions(Activity activity, String[] requestPermissions) {
        List<String> deniedPermissions = new ArrayList<>();
        for (String requestPermission : requestPermissions) {
            if (ContextCompat.checkSelfPermission(activity, requestPermission) == PackageManager.PERMISSION_DENIED) {
                deniedPermissions.add(requestPermission);
            }
        }
        return deniedPermissions;
    }


    /**
     * 反射执行方法
     */
    private static void executeMethod(Object reflectObject, Method method) {

        try {
            // 允许执行私有方法
            method.setAccessible(true);
            // 参数一：该方法属于哪一个类  参数二：传对应的参数
            method.invoke(reflectObject, new Object[]{});
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断并请求剩余的未授权的权限
     *
     * @param activity
     * @param mRequestPermission
     * @param requestCode
     */
    public static void requestPermissions(Activity activity, String[] mRequestPermission, int requestCode) {
        //获取未授权的权限
        deniedPermission = getDeniedPermissions(activity, mRequestPermission);
        ActivityCompat.requestPermissions(activity,
                deniedPermission.toArray(new String[deniedPermission.size()]),
                requestCode);
    }


    /**
     * 跳转至该应用的设置页面
     *
     * @param activity
     */
    public static void startAppSettings(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 所有的权限都已经授权
     *
     * @param grantResults
     * @return
     */
    public static boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是否已经全部授予权限
     *
     * @param permissions
     * @return
     */
    public static boolean hasAllPermissionsGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_DENIED)
                return false;
        }
        return true;
    }
}
