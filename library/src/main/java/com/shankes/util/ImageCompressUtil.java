package com.shankes.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * BitmapUtil
 *
 * @author shankes
 * @date 2018/08/02
 */
public class ImageCompressUtil {

    /**
     * 根据要显示图片的控件大小对图片进行压缩
     *
     * @param photoPath 图片路径
     * @param view      要显示图片的控件
     * @return
     */
    public static Bitmap compressImage(String photoPath, View view) {
        //获取view的宽和高
        int targetWidth = view.getWidth();
        int targetHeight = view.getHeight();
        return compressImage(photoPath, targetWidth, targetHeight);
    }

    /**
     * 根据指定宽高对图片进行压缩
     *
     * @param photoPath    图片路径
     * @param targetWidth  压缩后图片的宽度
     * @param targetHeight 压缩后图片的高度
     * @return
     */
    public static Bitmap compressImage(String photoPath, int targetWidth, int targetHeight) {
        //根据图片路径，获取bitmap的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        int photoWidth = options.outWidth;
        int photoHeight = options.outHeight;

        //获取缩放比例
        int inSampleSize = 1;
        if (photoWidth > targetWidth || photoHeight > targetHeight) {
            int widthRatio = Math.round((float) photoWidth / targetWidth);
            int heightRatio = Math.round((float) photoHeight / targetHeight);
            inSampleSize = Math.min(widthRatio, heightRatio);
        }

        //使用现在的options获取Bitmap
        options.inSampleSize = inSampleSize;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(photoPath, options);
    }
}
