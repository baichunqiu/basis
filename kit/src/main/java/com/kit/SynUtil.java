package com.kit;

import java.util.concurrent.CountDownLatch;

/**
 * @author: BaiCQ
 * @ClassName: SynUtil
 * @date: 2018/8/17
 * @Description: 异步执行变同步的工具类 避免在原有异步执行的代码中添加回调
 */
public class SynUtil {

    public interface OnExecuteListeren<T> {
        /**
         * 任务执行子线回调
         * 注意:在执行结束（成功或失败时）执行latch.countDown();
         *
         * @param latch
         */
        T onExecute(CountDownLatch latch);

        /**
         * 任务结束子线程回调
         */
        void onEnd(T t);
    }

    /**
     * 异步任务变同步回调结束
     *
     * @param onExecuteListeren
     */
    public static <T> void synTask(final OnExecuteListeren<T> onExecuteListeren) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                CountDownLatch latch = new CountDownLatch(1);
                T t = null;
                if (null != onExecuteListeren) {
                    t = onExecuteListeren.onExecute(latch);
                }
                try {
                    latch.await();
                } catch (Exception e) {
                }
                if (null != onExecuteListeren) {
                    onExecuteListeren.onEnd(t);
                }
            }
        }).start();
    }
}
