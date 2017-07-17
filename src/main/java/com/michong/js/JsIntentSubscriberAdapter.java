package com.michong.js;

import com.michong.js.entity.JsIntentEntity;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/10.
 */

public class JsIntentSubscriberAdapter implements JsIntentSubscriber {
    @Override
    public void onSetTitleIntent(JsIntentEntity intent, String title) {

    }

    @Override
    public void onSaveTokenIntent(JsIntentEntity intent, String token) {

    }

    @Override
    public void onGetTokenIntent(JsIntentEntity intent) {

    }

    @Override
    public void onUploadImageIntent(JsIntentEntity intent, int width, int height, long maxSize) {

    }

    @Override
    public void onThirdLoginIntent(JsIntentEntity intent, String thirdType) {

    }

    @Override
    public void onCanBackIntent(JsIntentEntity intent, boolean canBack) {

    }

    @Override
    public void onInterceptBackButtonIntent(JsIntentEntity intent, boolean intercept) {

    }

    @Override
    public void onThirdShareIntent(JsIntentEntity intent, String shareId, String type) {

    }

    @Override
    public void onHyperLinkIntent(JsIntentEntity intent, String target, String decodedUrl, boolean jsBridgeEnabled) {

    }

    @Override
    public void onFinishIntent(JsIntentEntity intent) {

    }

    @Override
    public void onNetworkQueryIntent(JsIntentEntity intent) {

    }

    @Override
    public void onPickContactIntent(JsIntentEntity intent) {

    }

    @Override
    public void onCallPhoneIntent(JsIntentEntity intent, String number) {

    }

    @Override
    public void onBindPushAliasIntent(JsIntentEntity intent, String alias) {

    }

    @Override
    public void onSaveDataIntent(JsIntentEntity intent, String key, String data) {

    }

    @Override
    public void onGetDataIntent(JsIntentEntity intent, String key) {

    }

    @Override
    public void onUnbindPushAliasIntent(JsIntentEntity intent, String alias) {

    }

    @Override
    public void onSendSMSIntent(JsIntentEntity intent, String number, String content) {

    }

    @Override
    public void onScanQRCodeIntent(JsIntentEntity intent) {

    }

    @Override
    public void onGetPushDataIntent(JsIntentEntity intent) {

    }

    @Override
    public void onParseError(int errorCode, String msg) {

    }
}
