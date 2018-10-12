package com.shankes.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘显示隐藏
 *
 * @author shankes
 * @date 2018/7/6
 */

public class KeyBoardUtil {

    /**
     * 显示软键盘最可靠的方法
     * <p>
     * 当前界面必须已经加载完成
     * 不能直接在Activity的onCreate()，onResume()，onAttachedToWindow()中使用
     * 可以在这些方法中通过postDelayed的方式来延迟执行showSoftInput()。
     * <p>
     * 在 onCreate() 中，如果立即调用 showSoftInput() 是不会生效的
     * 想要在页面一启动的时候就弹出键盘
     * 可以在 Activity 上，设置 android:windowSoftInputMode 属性来完成
     * 或者做一个延迟加载，View.postDelayed() 也是一个解决方案
     *
     * @param view 最好是VISIBLE的EditText或者它的子类，如果不是VISIBLE的，需要先将其设置为VISIBLE。
     *             如果不是一个 EditText ，就必须要求这个 View 有两个属性
     *             分别是：android:focusable="true" 和android:focusableInTouchMode="true"
     */
    public static void show(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    /**
     * 隐藏软键盘最方便的方法
     *
     * @param view 可以当前布局中已经存在的任何一个View，如果找不到可以用getWindow().getDecorView()
     */
    public static void hide(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 切换键盘的弹出和隐藏
     *
     * @param view
     */
    public static void toggle(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 获取输入法当前是否打开
     *
     * @param view
     * @return isOpen若返回true，则表示输入法打开
     */
    public static boolean isOpen(View view) {
        boolean isOpen = false;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            isOpen = imm.isActive(view);
        }
        return isOpen;
    }
}
