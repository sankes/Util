package com.shankes.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.View;


/**
 */
public class TextViewUtils {

    public static class OnSpClickableListener extends ClickableSpan {
        private final View.OnClickListener mListener;

        public OnSpClickableListener(View.OnClickListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.setUnderlineText(false);    //去除超链接的下划线
        }
    }

    /**
     * 指定位置的文字着色
     * @param spText
     * @param sp
     * @return
     */
    public static SpannableString setSpecificTextOnclick(String spText, View.OnClickListener listener , SpannableString sp){
        int startP;
        int endP;
        try {
            String s=sp.toString();
            startP = s.indexOf(spText);
            endP = startP+spText.length();
        } catch (Exception e) {
            e.printStackTrace();
            return sp;
        }

        sp.setSpan(new OnSpClickableListener(listener), startP, endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 指定位置的文字着色
     * @param spText
     * @param sp
     * @return
     */
    public static SpannableString underlineSpecificText(String spText, SpannableString sp){
        int startP;
        int endP;
        try {
            String s=sp.toString();
            startP = s.indexOf(spText);
            endP = startP+spText.length();
        } catch (Exception e) {
            e.printStackTrace();
            return sp;
        }


        sp.setSpan(new UnderlineSpan(),startP,endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
    /**
     * 指定位置的文字着色
     * @param spText
     * @param color
     * @param sp
     * @return
     */
    public static SpannableString tintSpecificText(String spText, int color, SpannableString sp){
        int startP;
        int endP;
        try {
            String s=sp.toString();
            startP = s.indexOf(spText);
            endP = startP+spText.length();
        } catch (Exception e) {
            e.printStackTrace();
            return sp;
        }

        sp.setSpan(new ForegroundColorSpan(color),startP,endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 指定位置的文字着色
     * @param startP
     * @param endP
     * @param color
     * @param sp
     * @return
     */
    public static SpannableString tintSpecificText(int startP, int endP, int color, SpannableString sp){
        sp.setSpan(new ForegroundColorSpan(color),startP,endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 指定位置的文字设置背景
     * @param startP
     * @param endP
     * @param drawable      设置图片
     * @param sp
     * @return
     */
    public static SpannableString tintSpecialTextBg(int startP, int endP, Drawable drawable, SpannableString sp){
        sp.setSpan(new ImageSpan(drawable),startP,endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 指定位置的文字设置背景
     * @param startP
     * @param endP
     * @param bgColor
     * @param sp
     * @return
     */
    public static SpannableString tintSpecialTextBg(int startP, int endP, int bgColor, SpannableString sp){
        sp.setSpan(new BackgroundColorSpan(bgColor),startP,endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 指定位置的文字调整大小尺寸
     * @param startP
     * @param endP
     * @param relativeSize
     * @param sp
     * @return
     */
    public static SpannableString resizeSpecificText(int startP, int endP, float relativeSize, SpannableString sp){
        sp.setSpan(new RelativeSizeSpan(relativeSize),startP,endP, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }

    /**
     * 设置倒计时文字效果
     * @param prefix            前缀
     * @param day               天数
     * @param dayText           天数后跟随的文字
     * @param hour              小时数
     * @param hourText          小时后跟随的文字
     * @param minute            分钟数
     * @param minuteText        分钟后跟随的文字
     * @param second            秒数
     * @param secondText        秒后跟随的文字
     * @param suffix            后缀
     * @param context           上下文
     * @param bgColor           指定文字的背景颜色
     * @param textColor         指定文字的字体颜色
     * @return
     */
    public static SpannableString setRushTimeText(String prefix
            , String day, String dayText
            , String hour, String hourText
            , String minute, String minuteText
            , String second, String secondText, String suffix
            , Context context
            , int bgColor, int textColor){
//        Resources resources=context.getResources();
        //前缀（距离结束）
//        String prefix=resources.getString(R.string.rush_time_prefix);
        //中文字符 （天）
//        String daySymbol=resources.getString(R.string.day);
        //冒号（:）
//        String timeSymbol=resources.getString(R.string.time_symbol);
        //倒计时文字（距离结束 1 天 02 : 08 : 30 ）
        String text=prefix+day+dayText+hour+hourText+minute+minuteText+second+secondText+suffix;
        SpannableString sp=new SpannableString(text);

        //文字背景颜色
//        int bgColor=Color.GRAY;
        //文字颜色
//        int textColor=Color.WHITE;
        //文字背景
//        Drawable drawable= ContextCompat.getDrawable(context, R.drawable.bg_account_header);
//        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

        int startD=prefix.length();
        int endD=startD+day.length();
        int startH=endD+dayText.length();
        int endH=startH+hour.length();
        int startM=endH+hourText.length();
        int endM=startM+minute.length();
        int startS=endM+minuteText.length();
        int endS=startS+second.length();

        sp=tintSpecialTextBg(startH,endH, bgColor,sp);
        sp=tintSpecificText(startH,endH,textColor,sp);

        sp=tintSpecialTextBg(startM,endM, bgColor,sp);
        sp=tintSpecificText(startM,endM,textColor,sp);

        sp=tintSpecialTextBg(startS,endS, bgColor,sp);
        sp=tintSpecificText(startS,endS,textColor,sp);

        return sp;
    }


}
