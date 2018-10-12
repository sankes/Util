package com.shankes.util;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

/**
 * 作者：mooney
 * 日期：2017/5/5 0005
 * 邮箱：shili_yan@sina.com
 * 描述：
 */

public class SystemPermissionUtils {

    public static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION=0x11;

    public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION=0x12;

    public static final int REQUEST_CALL_PHONE_PERMISSION=0x15;


    private static boolean checkPermission(final Activity activity
            , String message
            , final int requestCode
            ,final String... permissions){

        boolean granted=true;

        for (String permission:permissions){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){

                granted=false;

                // 第一次请求权限时，用户如果拒绝，下一次请求shouldShowRequestPermissionRationale()返回true
                // 向用户解释为什么需要这个权限
                new AlertDialog.Builder(activity)
                        .setMessage(message)
                        .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(activity, permissions, requestCode);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();

            }else if (ContextCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                // Here, thisActivity is the current activity

                granted=false;
                ActivityCompat.requestPermissions(activity, permissions, requestCode);
            }

            if (!granted){
                break;
            }
        }

        return granted;
    }

    /**
     * 检测文件读取权限
     * @param activity
     * @return
     */
    public static boolean checkStoragePermission(Activity activity, String message){

        return  checkPermission(activity ,message,REQUEST_STORAGE_WRITE_ACCESS_PERMISSION
                , Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 检测文件读取权限
     * @param activity
     * @return
     */
    public static boolean checkStoragePermission(Activity activity){

        return checkPermission(activity,"请授权文件读写权限，以便正常使用app"
                ,REQUEST_STORAGE_WRITE_ACCESS_PERMISSION
                , Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /**
     * 检测调用相机、相册权限
     * @param activity
     * @return
     */
    public static boolean checkCameraPermission(Activity activity){
        return checkCameraPermission(activity,"请到授权相机权限,以便正常使用拍照功能");
    }

    /**
     * 检测调用相机、相册权限
     * @param activity
     * @return
     */
    public static boolean checkCameraPermission(Activity activity, String message){
        if (!checkStoragePermission(activity,"请授权文件的读取权限,以便正常使用拍照功能")){
            return false;
        }
        if (!checkPermission(activity,message ,REQUEST_STORAGE_READ_ACCESS_PERMISSION
                , Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return false;
        }
        return true;

    }

    /**
     * 检测录制声音权限
     * @param activity
     * @return
     */
    public static boolean checkRecordAudioPermission(Activity activity){
        if (!checkStoragePermission(activity,"请授权音频文件的读取权限,以便正常使用音频录制及上传功能")){
            return false;
        }
        if (!checkPermission(activity,"请授权音频录制权限,以便正常使用音频录制及上传功能"
                ,0x13
                , Manifest.permission.RECORD_AUDIO
                , Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            return false;
        }
        return true;
    }

    /**
     * 检测定位权限
     * @param activity
     * @return
     */
    public static boolean checkLocationPermission(Activity activity){
        boolean granted=true;
        if (!checkPermission(activity,"请授权位置定位权限,以便正常使用app"
                ,0x14
                , Manifest.permission.ACCESS_FINE_LOCATION)){
            granted=false;
        }
        return granted;
    }
    /**
     * 检测定位权限
     * @param activity
     * @return
     */
    public static boolean checkCallPermission(Activity activity){
        boolean granted=true;
        if (!checkPermission(activity,"请授权拨号权限,以便正常使用拨打电话功能"
                ,REQUEST_CALL_PHONE_PERMISSION
                , Manifest.permission.CALL_PHONE)){
            granted=false;
        }
        return granted;
    }

}
