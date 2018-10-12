package com.shankes.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池
 *
 * @author shankes
 * @date 2018/4/25
 */

public final class ThreadPool {

    public static final int SINGLE_THREAD = 1;
    public static final int CANCHED_THREAD = 2;

    private static ThreadPool mThreadPool;
    private static int currType;
    ExecutorService threadPool;

    private ThreadPool() {
        this(SINGLE_THREAD);
    }

    private ThreadPool(int type) {
        currType = type;
        switch (type) {
            case SINGLE_THREAD:
                threadPool = Executors.newSingleThreadExecutor();
                break;
            case CANCHED_THREAD:
                threadPool = Executors.newCachedThreadPool();
                break;
        }
    }

    public static ThreadPool getThreadPool(int type) {
        if (currType == type) {
            if (mThreadPool == null) {
                mThreadPool = new ThreadPool(type);
            }
        } else {
            mThreadPool = new ThreadPool(type);
        }
        return mThreadPool;
    }

    public void execut(Runnable runnable) {
        if (threadPool != null) {
            threadPool.submit(runnable);
        }
    }
}
