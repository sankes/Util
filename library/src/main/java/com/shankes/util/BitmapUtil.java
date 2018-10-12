package com.shankes.util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

/**
 * 不使用OpenCV处理图片的工具类
 *
 * @author shankes
 * @date 2018/8/2
 */
public class BitmapUtil {

    /**
     * （额外附上bitmap的处理方法）灰度化
     *
     * @param bmSrc Bitmap
     * @return Bitmap
     */
    public static Bitmap bitmapGray(Bitmap bmSrc) {
        // 得到图片的长和宽
        int width = bmSrc.getWidth();
        int height = bmSrc.getHeight();
        // 创建目标灰度图像
        Bitmap bmpGray = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        // 创建画布
        Canvas c = new Canvas(bmpGray);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmSrc, 0, 0, paint);
        return bmpGray;
    }

    /**
     * 旋转
     *
     * @param angle  旋转角度
     * @param bitmap Bitmap
     * @return Bitmap
     */
    public static Bitmap bitmapRotate(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    /**
     * （额外附上bitmap方法）RGB转换
     *
     * @param bmp Bitmap
     * @return Bitmap
     */
    public static Bitmap bitmapTransparent(Bitmap bmp) {

/*       pixels      接收位图颜色值的数组
         offset      写入到pixels[]中的第一个像素索引值
         stride      pixels[]中的行间距个数值(必须大于等于位图宽度)。可以为负数
           x          　       从位图中读取的第一个像素的x坐标值。
         y           从位图中读取的第一个像素的y坐标值
         width    　 从每一行中读取的像素宽度
         height 　　 读取的行数
*/
        int mImageWidth, mImageHeigth;
        mImageWidth = bmp.getWidth();
        mImageHeigth = bmp.getHeight();
        int[] mBmpPixel = new int[mImageWidth * mImageHeigth];
        bmp.getPixels(mBmpPixel, 0, mImageWidth, 0, 0, mImageWidth,
                mImageHeigth);
        for (int i = 0; i < mImageWidth * mImageHeigth; i++) {
            if (mBmpPixel[i] == Color.WHITE) {
                mBmpPixel[i] = Color.BLACK;
            }
        }
        Bitmap pro = Bitmap.createBitmap(mImageWidth, mImageHeigth, Bitmap.Config.ARGB_8888);
        pro.setPixels(mBmpPixel, 0, mImageWidth, 0, 0, mImageWidth, mImageHeigth);
        return pro;
    }

    /**
     * （额外附上bitmap方法）二值化
     *
     * @param graymap
     * @return Bitmap
     */
    public static Bitmap bitmapBinary(Bitmap graymap) {
        // 得到图形的宽度和长度
        int width = graymap.getWidth();
        int height = graymap.getHeight();
        // 创建二值化图像
        Bitmap binarymap = graymap.copy(Bitmap.Config.ARGB_8888, true);
        // 依次循环，对图像的像素进行处理
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 得到当前像素的值
                int col = binarymap.getPixel(i, j);
                // 得到alpha通道的值
                int alpha = col & 0xFF000000;
                // 得到图像的像素RGB的值
                int red = (col & 0x00FF0000) >> 16;
                int green = (col & 0x0000FF00) >> 8;
                int blue = (col & 0x000000FF);
                // 用公式X = 0.3×R+0.59×G+0.11×B计算出X代替原来的RGB
                int gray = (int) ((float) red * 0.3 + (float) green * 0.59 + (float) blue * 0.11);
                //对图像进行二值化处理
                if (gray <= 130) {
                    gray = 0;
                } else {
                    gray = 255;
                }
                // 新的ARGB
                int newColor = alpha | (gray << 16) | (gray << 8) | gray;
                // 设置新图像的当前像素值
                binarymap.setPixel(i, j, newColor);
            }
        }
        return binarymap;
    }
}
