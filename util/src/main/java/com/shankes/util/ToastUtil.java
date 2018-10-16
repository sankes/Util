package com.shankes.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.concurrent.Executor;

/**
 * Toast统一管理类
 *
 * @author shankes
 * @date 2018/4/25
 */
public class ToastUtil {

    protected static String oldMsg;
    protected static long lastTime;
    private static boolean isDebug = true;

    protected static HandlerExecutor executor = new HandlerExecutor(new Handler(Looper.getMainLooper()));

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

    protected ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showShort(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showShort(Context context, @StringRes int message) {
        show(context, message, Toast.LENGTH_SHORT);
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showShortDebug(Context context, CharSequence message) {
        if (isDebug) {
            show(context, message, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 短时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showShortDebug(Context context, @StringRes int message) {
        if (isDebug) {
            show(context, message, Toast.LENGTH_SHORT);
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showLong(Context context, CharSequence message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showLong(Context context, @StringRes int message) {
        show(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showLongDebug(Context context, CharSequence message) {
        if (isDebug) {
            show(context, message, Toast.LENGTH_LONG);
        }
    }

    /**
     * 长时间显示Toast
     *
     * @param context 上下文
     * @param message 内容
     */
    public static void showLongDebug(Context context, @StringRes int message) {
        if (isDebug) {
            show(context, message, Toast.LENGTH_LONG);
        }
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  内容
     * @param duration 显示时长
     */
    public static void show(Context context, @StringRes int message, int duration) {
        final String msg = context.getResources().getString(message);
        show(context, msg, duration);
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  内容
     * @param duration 显示时长
     */
    public static void show(final Context context, final CharSequence message, final int duration) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        ThreadPool.getThreadPool(ThreadPool.CANCHED_THREAD).execut(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.equals(oldMsg, message)) {
                    if (System.currentTimeMillis() - lastTime < 2000) {
                        try {
                            Thread.sleep(2000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    executor.post(new Runnable() {
                        @Override
                        public void run() {
                            oldMsg = message.toString();
                            if (context == null) {
                                return;
                            }
                            Toast.makeText(context, message, duration).show();
                        }
                    });
                    lastTime = System.currentTimeMillis();

                } else if (System.currentTimeMillis() - lastTime > 2000) {
                    executor.post(new Runnable() {
                        @Override
                        public void run() {
                            oldMsg = message.toString();
                            if (context == null) {
                                return;
                            }
                            Toast.makeText(context, message, duration).show();
                        }
                    });
                    lastTime = System.currentTimeMillis();
                }
            }
        });
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context  上下文
     * @param message  内容
     * @param duration 显示时长
     */
    public static void showDebug(final Context context, final CharSequence message, final int duration) {
        if (isDebug) {
            if (TextUtils.isEmpty(message)) {
                return;
            }
            ThreadPool.getThreadPool(ThreadPool.CANCHED_THREAD).execut(new Runnable() {
                @Override
                public void run() {
                    if (!TextUtils.equals(oldMsg, message)) {
                        if (System.currentTimeMillis() - lastTime < 2000) {
                            try {
                                Thread.sleep(2000L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        executor.post(new Runnable() {
                            @Override
                            public void run() {
                                oldMsg = message.toString();
                                if (context == null) {
                                    return;
                                }
                                Toast.makeText(context, message, duration).show();
                            }
                        });
                        lastTime = System.currentTimeMillis();

                    } else if (System.currentTimeMillis() - lastTime > 2000) {
                        executor.post(new Runnable() {
                            @Override
                            public void run() {
                                oldMsg = message.toString();
                                if (context == null) {
                                    return;
                                }
                                Toast.makeText(context, message, duration).show();
                            }
                        });
                        lastTime = System.currentTimeMillis();
                    }
                }
            });
        }
    }
}
