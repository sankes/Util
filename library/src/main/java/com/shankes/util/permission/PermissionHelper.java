package com.shankes.util.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * 作者：mooney
 * 日期：2018/4/25
 * 邮箱：yanshilioo@gmail.com
 * 描述：
 */

public class PermissionHelper {

    static PermissionActivity.OnPermissionListener mListener;

    public static void checkPermissions(Activity activity
            , PermissionActivity.OnPermissionListener listener
            , String permissionDescription
            , String... permissions) {

        mListener = listener;

        if (PermissionEngine.checkPermissions(activity, permissions)) {
            if (listener != null)
                listener.onGranted();
            return;
        }

        Intent intent = new Intent(activity, PermissionActivity.class);

        intent.putExtra("permission_description", permissionDescription);
        intent.putExtra("permissions", permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


}

class PermissionEngine {

    private Activity mActivity;              // 需要传入的Activity Fragment
    private int mPermissionRequestCode;            // 请求码
    private String[] mRequestPermission; // 需要请求的权限集合
    private String mPermissionDes;       // 需要显示的权限说明

    private int mSettingRequestCode;

    private OnPermissionListener mListener;


    public PermissionEngine(Activity activity) {
        this.mActivity = activity;
    }

    public static PermissionEngine with(Activity activity) {
        return new PermissionEngine(activity);
    }


    public PermissionEngine requestCode(int requestCode) {
        this.mPermissionRequestCode = requestCode;
        return this;
    }

    public PermissionEngine requestSettingCode(int requestCode) {
        this.mSettingRequestCode = requestCode;
        return this;
    }

    public PermissionEngine setPermissionDes(String permissionDes) {
        this.mPermissionDes = permissionDes;
        return this;
    }

    public PermissionEngine requestPermissions(String... requestPermission) {
        this.mRequestPermission = requestPermission;
        return this;
    }

    public PermissionEngine setListener(OnPermissionListener listener) {
        mListener = listener;
        return this;
    }


    /**
     * 判断是否已获取权限
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static boolean checkPermissions(Activity activity, String[] permissions) {
        // 1.判断是否是6.0版本以上
        if (!PermissionUtils.isOverMarshmallow()) {
            return true;
        } else {
            if (PermissionUtils.hasAllPermissionsGranted(activity, permissions)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 权限的判断和请求的发送
     */
    public void request() {
        if (!checkPermissions(mActivity, mRequestPermission)) {
            PermissionUtils.requestPermissions(mActivity, mRequestPermission, mPermissionRequestCode);
        } else {
            if (mListener != null)
                mListener.onGranted();
        }
    }

    /**
     * 处理权限申请的回调
     *
     * @param requestCode
     * @param grantResults
     */
    public void requestPermissionResult(int requestCode, int[] grantResults) {
        if (requestCode == mPermissionRequestCode) {
            if (PermissionUtils.hasAllPermissionsGranted(grantResults)) {
                //权限全部授予 执行方法
                if (mListener != null)
                    mListener.onGranted();
            } else {
                //权限没有全部授予，再次请求权限
                showMissingPermissionDialog(mActivity, mPermissionDes);
            }
        }
    }

    /**
     * 显示权限对话框
     *
     * @param activity
     * @param permissionDes
     */
    public void showMissingPermissionDialog(final Activity activity, String permissionDes) {
        String message = TextUtils.isEmpty(permissionDes) ? "APP需要必要的权限为您提供服务,是否去设置" : permissionDes;
        new AlertDialog.Builder(activity)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        PermissionUtils.startAppSettings(activity, mSettingRequestCode);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mListener.onDenied();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public abstract static class OnPermissionListener {

        public abstract void onGranted();

        public abstract void onDenied();
    }
}