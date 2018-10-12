package com.shankes.util;

import android.view.View;
import android.view.ViewGroup;

/**
 * 设置某个View的margin
 *
 * @author shankes
 * @date 2018/5/29
 */

public class MarginUtil {

    /**
     * 设置某个View的margin
     *
     * @param view   需要设置的view
     * @param isDp   需要设置的数值是否为DP
     * @param left   左边距
     * @param right  右边距
     * @param top    上边距
     * @param bottom 下边距
     * @return 可以返回为null
     */
    public static ViewGroup.LayoutParams setViewMargin(View view, boolean isDp, int left, int right, int top, int bottom) {
        if (view == null) {
            return null;
        }

        int leftPx = left;
        int rightPx = right;
        int topPx = top;
        int bottomPx = bottom;
        ViewGroup.LayoutParams params = view.getLayoutParams();
        ViewGroup.MarginLayoutParams marginParams = null;
        //获取view的margin设置参数
        if (params instanceof ViewGroup.MarginLayoutParams) {
            marginParams = (ViewGroup.MarginLayoutParams) params;
        } else {
            //不存在时创建一个新的参数
            marginParams = new ViewGroup.MarginLayoutParams(params);
        }

        //根据DP与PX转换计算值
        if (isDp) {
            leftPx = DpiUtil.getPxFromDpi(view.getContext(), left);
            rightPx = DpiUtil.getPxFromDpi(view.getContext(), right);
            topPx = DpiUtil.getPxFromDpi(view.getContext(), top);
            bottomPx = DpiUtil.getPxFromDpi(view.getContext(), bottom);
        }
        //设置margin
        marginParams.setMargins(leftPx, topPx, rightPx, bottomPx);
        view.setLayoutParams(marginParams);
        return marginParams;
    }
}
