package com.shankes.util.permission;

import android.app.Activity;
import android.support.annotation.NonNull;

/**
 * 作者：mooney
 * 日期：2018/4/25
 * 邮箱：yanshilioo@gmail.com
 * 描述：
 */

public class PermissionActivity extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 0x11;
    private Boolean granted;

    private PermissionEngine mPermissionEngine;

    private PermissionEngine.OnPermissionListener mListener = new PermissionEngine.OnPermissionListener() {
        @Override
        public void onGranted() {
            granted = true;
            finish();
        }

        @Override
        public void onDenied() {
            granted = false;
            finish();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        String description = getIntent().getStringExtra("permission_description");
        String[] permissions = getIntent().getStringArrayExtra("permissions");

        mPermissionEngine = PermissionEngine.with(this)
                .requestCode(PERMISSION_REQUEST_CODE)
                .requestSettingCode(PERMISSION_REQUEST_CODE)
                .requestPermissions(permissions)
                .setPermissionDes(description)
                .setListener(mListener);

        mPermissionEngine.request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPermissionEngine.requestPermissionResult(requestCode, grantResults);
    }

    public interface OnPermissionListener {
        void onGranted();

        void onDenied();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (PermissionHelper.mListener != null) {
            if (granted == null) {

            } else if (granted) {
                PermissionHelper.mListener.onGranted();
            } else {
                PermissionHelper.mListener.onDenied();
            }
        }
    }
}
