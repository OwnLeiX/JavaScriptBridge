package com.michong.js;

import android.webkit.JavascriptInterface;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

final public class Bridge {
    private LinkedBlockingQueue<String> mQueue;

    Bridge(LinkedBlockingQueue<String> mQueue) {
        this.mQueue = mQueue;
    }

    @JavascriptInterface
    public void call(String msg) {
        if (msg != null)
            mQueue.offer(msg);
    }
}
