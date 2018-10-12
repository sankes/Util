package com.shankes.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * description
 *
 * @author shankes
 * @date 2018/6/5
 */

public class BrowserUtil {
    public static void openBrowser(Context context, String url) {
        if (!TextUtils.isEmpty(url)) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            context.startActivity(intent);
        }
    }
}
