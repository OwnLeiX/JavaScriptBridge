package com.michong.js;

import android.util.Base64;
import android.webkit.WebView;

import com.michong.js.config.JsEnum;
import com.michong.js.entity.JsIntentEntity;
import com.michong.js.kernel.ProcessIntentThread;
import com.michong.js.kernel.ProcessCallbackThread;
import com.michong.js.kernel.JsProcessor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

final public class JsBridge {

    public static JsBridge subscribe(WebView webView, String name) {
        JsBridge jsBridge = new JsBridge();
        if (webView != null) {
            webView.addJavascriptInterface(jsBridge.obtainBridge(), name);
        }
        return jsBridge;
    }

    public static JsBridge build() {
        return new JsBridge();
    }

    private ProcessIntentThread mProcessIntentThread;
    private ProcessCallbackThread mProcessCallbackThread;
    private JsProcessor mProcessor;
    private Bridge mBridge;
    private LinkedBlockingQueue<String> mIntentQueue;
    private LinkedBlockingQueue<JsIntentEntity> mCallbackQueue;
    private Pattern mPatten = Pattern.compile(JsEnum.Exception.reloadUrlHeader, Pattern.CASE_INSENSITIVE);

    private JsBridge() {
        mProcessor = new JsProcessor();
    }

    public Bridge obtainBridge() {
        if (mBridge == null) {
            synchronized (this) {
                if (mBridge == null) {
                    mIntentQueue = new LinkedBlockingQueue<>();
                    mBridge = new Bridge(mIntentQueue);
                    mProcessIntentThread = new ProcessIntentThread(mProcessor, mIntentQueue);
                    mProcessIntentThread.start();
                }
            }
        }
        return mBridge;
    }

    public String filterJsExceptionUrl(String url) {
        String filterUrl = url;
        Matcher matcher = mPatten.matcher(url);
        if (matcher.find()) {
            filterUrl = matcher.replaceFirst("");
            byte[] decodeUrl = Base64.decode(filterUrl, Base64.DEFAULT);
            filterUrl = new String(decodeUrl);
        }
        return filterUrl;
    }

    public void release() {
        mProcessor.reset();
        if (mProcessIntentThread != null) {
            mProcessIntentThread.interrupt();
            mProcessIntentThread = null;
            mIntentQueue.clear();
            mIntentQueue = null;
        }
        if (mProcessCallbackThread != null) {
            mProcessCallbackThread.interrupt();
            mProcessCallbackThread = null;
            mCallbackQueue.clear();
            mCallbackQueue = null;
        }
    }

    public void buildCallback(JsIntentEntity intent) {
        if (mProcessCallbackThread == null) {
            synchronized (this) {
                if (mProcessCallbackThread == null) {
                    mCallbackQueue = new LinkedBlockingQueue<>();
                    mProcessCallbackThread = new ProcessCallbackThread(mProcessor, mCallbackQueue);
                    mProcessCallbackThread.start();
                }
            }
        }
        mCallbackQueue.offer(intent);
    }

    public void buildBackPressedCallback() {
        mProcessor.buildBackPressedJsCallback();
    }

    public void setIntentSubscriber(JsIntentSubscriber subscriber) {
        mProcessor.setIntentSubscriber(subscriber);
    }

    public void removeIntentSubscriber() {
        mProcessor.removeIntentSubscriber();
    }

    public void setInterceptor(JsIntentInterceptor interceptor) {
        mProcessor.setIntentInterceptor(interceptor);
    }

    public void removeInterceptor() {
        mProcessor.removeIntentInterceptor();
    }

    public void setCallbackSubscriber(JsCallbackSubscriber subscriber) {
        mProcessor.setCallbackSubscriber(subscriber);
    }

    public void removeCallbackSubscriber() {
        mProcessor.removeCallbackSubscriber();
    }

}
