package com.michong.js.kernel;

import com.michong.js.entity.JsIntentEntity;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

final public class ProcessCallbackThread extends Thread {
    private JsProcessor mProcessor;
    private LinkedBlockingQueue<JsIntentEntity> mQueue;// 等待队列
    private JsIntentEntity mReceivedData;

    public ProcessCallbackThread(JsProcessor processor, LinkedBlockingQueue<JsIntentEntity> queue) {
        this.mProcessor = processor;
        this.mQueue = queue;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                mReceivedData = mQueue.take();
            } catch (InterruptedException e) {
                continue;
            }
            if (mReceivedData != null && mProcessor.checkCanBuildCallback(mReceivedData)) {
                mProcessor.buildJsCallback(mReceivedData);
            }
            mReceivedData = null;
        }
    }
}
