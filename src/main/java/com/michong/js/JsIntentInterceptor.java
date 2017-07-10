package com.michong.js;

import com.michong.js.entity.JsIntentEntity;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

public interface JsIntentInterceptor {
    boolean onInterceptJsIntent(JsIntentEntity intent);
}
