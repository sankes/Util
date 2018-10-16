package com.shankes.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * description
 *
 * @author shankes
 * @date 2018/5/29
 */

public class DpiUtil {
    public static int getPxFromDpi(Context _context, int _px) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                (float) _px, _context.getResources().getDisplayMetrics());
        return value;

    }

    public static int getDpiFromPx(Activity activity, int dp) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return (int) (dp * logicalDensity + 0.5);
    }

}
