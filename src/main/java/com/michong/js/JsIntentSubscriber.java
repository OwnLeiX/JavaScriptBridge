package com.michong.js;

import com.michong.js.entity.JsIntentEntity;

/**
 * <b></b>
 * Created on 2017/6/23.
 *
 * @author LeiXun
 */

public interface JsIntentSubscriber {

    void onSetTitleIntent(JsIntentEntity intent, String title);

    void onSaveTokenIntent(JsIntentEntity intent, String token);

    void onGetTokenIntent(JsIntentEntity intent);

    void onUploadImageIntent(JsIntentEntity intent, int width, int height, long maxSize);

    void onThirdLoginIntent(JsIntentEntity intent, String thirdType);

    void onCanBackIntent(JsIntentEntity intent, boolean canBack);

    void onInterceptBackButtonIntent(JsIntentEntity intent, boolean intercept);

    void onThirdShareIntent(JsIntentEntity intent, String shareId,String type);

    void onHyperLinkIntent(JsIntentEntity intent, String target, String decodedUrl, boolean jsBridgeEnabled);

    void onFinishIntent(JsIntentEntity intent);

    void onNetworkQueryIntent(JsIntentEntity intent);

    void onPickContactIntent(JsIntentEntity intent);

    void onCallPhoneIntent(JsIntentEntity intent,String number);

    void onSendSMSIntent(JsIntentEntity intent,String number,String content);

    void onScanQRCodeIntent(JsIntentEntity intent);

    void onGetPushDataIntent(JsIntentEntity intent);

    void onParseError(int errorCode,String msg);
}
