package com.michong.js.kernel;

import com.michong.js.entity.JsIntentEntity;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

final public class ProcessIntentThread extends Thread {
    private JsProcessor mProcessor;
    private LinkedBlockingQueue<String> mQueue;// 等待队列
    private String mReceivedData;

    public ProcessIntentThread(JsProcessor processor, LinkedBlockingQueue<String> queue) {
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
            if (mReceivedData != null) {
                JsIntentEntity intent = mProcessor.parseJsMessage(mReceivedData);
                mProcessor.processJsIntent(intent);
            }
            mReceivedData = null;
        }
    }
}
