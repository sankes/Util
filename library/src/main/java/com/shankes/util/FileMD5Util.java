package com.shankes.util;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * 获取文件md5值
 * 作者： yanshili
 * 日期： 2016/9/6 12:25
 * 邮箱： shili_yan@sina.com
 */
public class FileMD5Util {

    /**
     * 获取单个文件的MD5值
     * @param file
     * @param listener
     */
    public static void getFileMD5(final File file, final MD5Listener listener){
        ThreadPool.getThreadPool(ThreadPool.CANCHED_THREAD).execut(new Runnable() {
            @Override
            public void run() {
                final String md5=getFileMD5(file);
                Log.i("UpdateManager","file md5=="+md5);
                executor.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listen(md5);
                    }
                });

            }
        });
    }

    /**
     * 获取文件夹中文件的MD5值
     * @param file
     * @param listChild
     * @param listener
     */
    public static void getDirMD5(final File file, final boolean listChild, final MD5ListListener listener){
        ThreadPool.getThreadPool(ThreadPool.CANCHED_THREAD).execut(new Runnable() {
            @Override
            public void run() {
                final Map<String, String> map=getDirMD5(file,listChild);
                executor.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listen(map);
                    }
                });
            }
        });
    }

    /**
     * 获取单个文件的MD5值！耗时操作
     * @param file
     * @return
     */

    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    /**
     * 获取文件夹中文件的MD5值
     *
     * @param file
     * @param listChild
     *            ;true递归子目录中的文件
     * @return
     */
    public static Map<String, String> getDirMD5(File file, boolean listChild) {
        if (!file.isDirectory()) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>();
        String md5;
        File files[] = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            if (f.isDirectory() && listChild) {
                map.putAll(getDirMD5(f, listChild));
            } else {
                md5 = getFileMD5(f);
                if (md5 != null) {
                    map.put(f.getPath(), md5);
                }
            }
        }
        return map;
    }

    public interface MD5Listener{
        void listen(String md5);
    }

    public interface MD5ListListener{
        void listen(Map<String, String> mapMD5);
    }

    private final static HandlerExecutor executor = new HandlerExecutor(new Handler(Looper.getMainLooper()));

    static class HandlerExecutor {

        private final Executor mPoster;

        public HandlerExecutor(final Handler handler) {
            mPoster = new Executor() {
                @Override
                public void execute(Runnable command) {
                    handler.post(command);
                }
            };
        }

        public void post(Runnable runnable) {
            mPoster.execute(runnable);
        }

    }
}
