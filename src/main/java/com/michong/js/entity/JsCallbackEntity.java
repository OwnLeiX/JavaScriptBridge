package com.michong.js.entity;

import android.webkit.ValueCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <b></b>
 * Created on 2017/6/20.
 *
 * @author LeiXun
 */

public class JsCallbackEntity {

    public static JsCallbackEntity build(JsIntentEntity entity) {
        JsCallbackEntity instance = new JsCallbackEntity();
        instance.methodName = entity.getCallback();
        instance.errno = entity.getResultErrno();
        instance.errMsg = entity.getResultErrorMessage();
        instance.params = entity.getResultParam();
        return buildCallbackContent(instance);
    }

    public static JsCallbackEntity buildBackPressedCallback() {
        JsCallbackEntity instance = new JsCallbackEntity();
        instance.methodName = "goBack";
        instance.errno = 0;
        instance.errMsg = "";
        instance.params = new JSONObject();
        return buildCallbackContent(instance);
    }

    /**
     * 根据JsCallback拼接{@link android.webkit.WebView#loadUrl(String)} 或 {@link android.webkit.WebView#evaluateJavascript(String, ValueCallback)} 所用String
     */
    private static JsCallbackEntity buildCallbackContent(JsCallbackEntity instance) {
        if (instance != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("errno", instance.errno);
                jsonObject.put("error", instance.errMsg);
                jsonObject.put("data", instance.params == null ? new JSONObject() : instance.params);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String params = jsonObject.toString();
            params = params.replaceAll("\\\\\\\"", "\\\\\\\\\"");
            instance.callbackContent = String.format("javascript:%1$s('%2$s');", instance.methodName, params);
        }
        return instance;
    }

    private String methodName;
    private JSONObject params;
    private String errMsg;
    private int errno;

    private String callbackContent;//拼接好的callback

    private JsCallbackEntity() {
        params = new JSONObject();
    }

    public String getMethodName() {
        return methodName;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public int getErrno() {
        return errno;
    }

    public String getCallbackContent() {
        return callbackContent;
    }
}
