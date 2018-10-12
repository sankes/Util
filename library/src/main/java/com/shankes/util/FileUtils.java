package com.shankes.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者： yanshili
 * 日期： 2016/9/20 15:27
 * 邮箱： shili_yan@sina.com
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    /**
     * 将assets中的识别库复制到SD卡中
     *
     * @param context 上下文
     * @param path    要存放在SD卡中的 完整的文件名。这里是"/storage/emulated/0//tessdata/chi_sim.traineddata"
     * @param name    assets中的文件名 这里是 "chi_sim.traineddata"
     */
    public static void copyFromAssetsToSDCardIfNotExist(Context context, String path, String name) {
        //如果存在就删掉
        File f = new File(path);
        if (f.exists()) {
            return;
            // f.delete();
        }
        if (!f.exists()) {
            File p = new File(f.getParent());
            if (!p.exists()) {
                p.mkdirs();
            }
//            try {
//                f.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
        copyFromAssetsToSDCard(context, path, name);
    }

    /**
     * 将assets中的识别库复制到SD卡中
     *
     * @param context 上下文
     * @param path    要存放在SD卡中的 完整的文件名。这里是"/storage/emulated/0//tessdata/chi_sim.traineddata"
     * @param name    assets中的文件名 这里是 "chi_sim.traineddata"
     */
    private static void copyFromAssetsToSDCard(Context context, String path, String name) {
        InputStream is = null;
        OutputStream os = null;
        try {
            Log.i(TAG, "copy " + name + " to " + path + " start");
            is = context.getAssets().open(name);
            File file = new File(path);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
            Log.i(TAG, "copy " + name + " to " + path + " finished");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "请检查assets文件夹下是否存在 " + name + " 文件", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "copy " + name + " to " + path + " failed");
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(File file, Context context) {

        if (file == null || !file.exists()) {
            return;
        }
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        openFile(Uri.fromFile(file), type, context);
    }

    /**
     * 打开网址文件
     */
    public static void openUrl(String url, Context context) {
        //获取文件file的MIME类型
        String type = getMIMEType(url);
        openFile(Uri.parse(url), type, context);
    }

    //禁掉QQ打开方式
    public static void openFileExitQQ(String uri, String type, Context context) {
        Intent it = new Intent(Intent.ACTION_VIEW);
        it.setDataAndType(Uri.parse(uri), type);
        List<ResolveInfo> resInfo = context.getPackageManager().queryIntentActivities(it, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {
                Intent targeted = new Intent(Intent.ACTION_VIEW);
                targeted.setDataAndType(Uri.parse(uri), type);
                ActivityInfo activityInfo = info.activityInfo;
                if (activityInfo.packageName.contains("com.tencent.mobileqq")) {
                    continue;
                }
                targeted.setPackage(activityInfo.packageName);
                targetedShareIntents.add(targeted);
            }
            if (targetedShareIntents.size() != 0) {
                Intent chooserIntent = Intent.createChooser(targetedShareIntents.remove(0), "选择一款软件打开!");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetedShareIntents.toArray(new Parcelable[]{}));
                context.startActivity(chooserIntent);
            } else {
                Toast.makeText(context, "没有可选程序", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //禁掉QQ打开方式
    public static void openFileExitQQ(File file, Context context) {
        String type = getMIMEType(file);
        openFileExitQQ("file://" + file.getPath(), type, context);
    }


    public static void openFile(Uri uri, String mimetype, Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);

        //设置intent的data和Type属性。
        intent.setDataAndType(uri, mimetype);
        //跳转
        try {
            context.startActivity(intent);     //这里最好try一下，有可能会报错。
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 比如说你的MIME类型是打开邮箱，但是你手机里面没装邮箱客户端，就会报错。
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    public static String getMIMEType(File file) {

        if (file == null) {
            return null;
        }

        String fName = file.getName();
        return getMIMEType(fName);
    }

    public static String getMIMEType(String name) {
        if (TextUtils.isEmpty(name)) {
            return null;
        }
        String type = "*/*";
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = name.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
    /* 获取文件的后缀名*/
        String end = name.substring(dotIndex, name.length()).toLowerCase();
        if (end == "") return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) { //MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0])) {
                type = MIME_MapTable[i][1];
            }
        }
        return type;
    }

    public static boolean isVideo(File file) {
        String type = getMIMEType(file);
        return type != null && type.startsWith("video");
    }

    public static boolean isAudio(File file) {
        String type = getMIMEType(file);
        return type != null && type.startsWith("audio");
    }

    public static boolean isVideo(String name) {
        String type = getMIMEType(name);
        return type != null && type.startsWith("video");
    }

    public static boolean isAudio(String name) {
        String type = getMIMEType(name);
        return type != null && type.startsWith("audio");
    }

    public static boolean isImage(String name) {
        String type = getMIMEType(name);
        return type != null && type.startsWith("image");
    }

    public static boolean isText(String name) {
        String type = getMIMEType(name);
        return type != null && type.startsWith("text");
    }

    public static boolean isGIF(String name) {
        String type = getMIMEType(name);
        return type != null && type.endsWith("gif");
    }

    // MIME_MapTable是所有文件的后缀名所对应的MIME类型的一个String数组
    private static final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".apk", "application/vnd.android.package-archive"},
            {".bin", "application/octet-stream"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".exe", "application/octet-stream"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".msg", "application/vnd.ms-outlook"},
            {".pdf", "application/pdf"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".wps", "application/vnd.ms-works"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},

            {".mp2", "audio/x-mpeg"},
            {".mpga", "audio/mpeg"},
            {".ogg", "audio/ogg"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".m4p", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4a", "audio/mp4a-latm"},
            {".au", "audio/basic"},
            {".snd", "audio/basic"},
            {".mid", "audio/mid"},
            {".rmi", "audio/mid"},
            {".mp3", "audio/mpeg"},
            {".aif", "audio/x-aiff"},
            {".aifc", "audio/x-aiff"},
            {".aiff", "audio/x-aiff"},
            {".m3u", "audio/x-mpegurl"},
            {".ra", "audio/x-pn-realaudio"},
            {".ram", "audio/x-pn-realaudio"},
            {".wav", "audio/x-wav"},

            {".qt", "video/quicktime"},
            {".rm", "video/vnd.rn-realvideo"},
            {".rmvb", "video/vnd.rn-realvideo"},
            {".mp4", "video/mp4"},
            {".mpg4", "video/mp4"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".flv", "video/x-flv"},
            {".3gp", "video/3gpp"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".viv", "video/vnd.vivo"},
            {".vivo", "video/vnd.vivo"},
            {".mp2", "video/mpeg"},
            {".mpa", "video/mpeg"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpv2", "video/mpeg"},
            {".mov", "video/quicktime"},
            {".qt", "video/quicktime"},
            {".lsf", "video/x-la-asf"},
            {".lsx", "video/x-la-asf"},
            {".asf", "video/x-ms-asf"},
            {".asr", "video/x-ms-asf"},
            {".asx", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".movie", "video/x-sgi-movie"},

            {".png", "image/png"},
            {".bmp", "image/bmp"},
            {".cod", "image/cis-cod"},
            {".gif", "image/gif"},
            {".ief", "image/ief"},
            {".jpe", "image/jpeg"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".jfif", "image/jpeg"},
            {".svg", "image/svg+xml"},
            {".tif", "image/tiff"},
            {".tiff", "image/tiff"},
            {".ras", "image/x-cmu-raster"},
            {".cmx", "image/x-cmx"},
            {".ico", "image/x-icon"},
            {".pnm", "image/x-portable-anymap"},
            {".pbm", "image/x-portable-bitmap"},
            {".pgm", "image/-portable-graymap"},
            {".ppm", "image/x-portable-pixmap"},
            {".rgb", "image/x-rgb"},
            {".xbm", "image/x-xbitmap"},
            {".xpm", "image/x-xpixmap"},
            {".xwd", "image/x-xwindowdump"},

            {"", "*/*"}
    };
}
