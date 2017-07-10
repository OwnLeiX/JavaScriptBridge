package com.michong.js;

import com.michong.js.entity.JsCallbackEntity;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

public interface JsCallbackSubscriber {
    void onBuildJsCallbackSucceed(JsCallbackEntity callback,String url);
}
