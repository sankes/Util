package com.shankes.util;

import android.app.Activity;
import android.view.WindowManager;

public class PopupWindowUtils {

	/**
	 * 设置添加屏幕的背景透明度
	 * 
	 * @param bgAlpha
	 */
	public static void backgroundAlpha(float bgAlpha, Activity activity) {
		WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
		lp.alpha = bgAlpha; // 0.0-1.0
		if (bgAlpha<1){
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}else {
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		}
		activity.getWindow().setAttributes(lp);
	}

}
