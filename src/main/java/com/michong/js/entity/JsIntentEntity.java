package com.michong.js.entity;

import com.michong.js.config.JsCallbackEnum;
import com.michong.js.config.JsEnum;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * <b></b>
 * Created on 2017/6/15.
 *
 * @author LeiXun
 */

public class JsIntentEntity {

    /**
     * 获取本JsIntent回调时的参数数量
     *
     * @param entity JsIntent
     * @return 该种类JsIntent回调时的参数数量
     */
    public static int getResultParamCount(JsIntentEntity entity) {
        switch (entity.type) {
            case GetToken:
                return 1;
            case UploadImage:
                return 1;
            case ThirdLogin://例外：回调直接给回三方的数据，不需要自己约定的key
                return 0;
            case QueryNetworkStatus:
                return 1;
            case PickContact:
                return 2;
            case ScanQRCode:
                return 1;
            case SendSMS:
            case CallPhone:
            case ThirdShare:
            case SetTitle:
            case SaveToken:
            case CanBack:
            case InterceptBack:
            case HyperLink:
            default:
                return 0;
        }
    }

    /**
     * 获取本JsIntent回调参数字段名
     *
     * @param entity JsIntent
     * @param index  索引，结合{@link #getResultParamCount(JsIntentEntity)}取值
     * @return 回调参数字段名
     */
    public static String getParamName(JsIntentEntity entity, int index) {
        String result = "";
        switch (entity.type) {
            case UploadImage://裁剪图片
                if (index == 0)//【回调参数0】为{@link #JsEnum.UpLoadImage.callbackParamName}
                    result = JsEnum.UpLoadImage.callbackParamName;
                break;
            case GetToken://获取Token
                if (index == 0)//【回调参数0】为{@link #JsEnum.GetToken.callbackParamName}
                    result = JsEnum.GetToken.callbackParamName;
                break;
            case QueryNetworkStatus:
                if (index == 0)
                    result = JsEnum.QueryNetworkStatus.callbackParamName;
                break;
            case PickContact:
                if (index == 0) {
                    result = JsEnum.PickContact.callbackParamName1;
                } else if (index == 1) {
                    result = JsEnum.PickContact.callbackParamName2;
                }
            case ScanQRCode:
                if (index == 0)
                    result = JsEnum.ScanQRCode.callbackParamName;
            default:
                result = "";
                break;
        }
        return result;
    }

    private String callback;//JavaScript回调方法名
    private JSONObject data;//数据
    private JsEnum.IntentType type = JsEnum.IntentType.Unknown;

    private JSONObject resultParam;
    private int resultErrno = 0;
    private String resultErrorMessage = "";

    public JsIntentEntity(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        initSelf(jsonObject);
    }

    public JsIntentEntity(JSONObject jsonObject) {
        initSelf(jsonObject);
    }

    public JsEnum.IntentType getType() {
        return type;
    }

    public String getCallback() {
        return callback;
    }

    public JSONObject getData() {
        return data;
    }

    public JSONObject getResultParam() {
        return resultParam;
    }

    public int getResultErrno() {
        return resultErrno;
    }

    public String getResultErrorMessage() {
        return resultErrorMessage;
    }

    public void setSucceedResult(String... param) {
        this.resultParam = new JSONObject();
        if (param != null) {
            try {
                for (int i = 0; i < param.length; i++) {
                    this.resultParam.put(getParamName(this, i), param[i]);
                }
            } catch (JSONException e) {
            }
        }
        this.resultErrno = JsCallbackEnum.Succeed.getErrno();
        this.resultErrorMessage = JsCallbackEnum.Succeed.getErrMsg();
    }

    public void setSucceedResult(JSONObject params) {
        this.resultParam = params;
        this.resultErrno = JsCallbackEnum.Succeed.getErrno();
        this.resultErrorMessage = JsCallbackEnum.Succeed.getErrMsg();
    }

    public void setFailedResult(int errno, String errMsg) {
        this.resultErrno = errno;
        this.resultErrorMessage = errMsg;
    }

    public void setFailedResult(JsCallbackEnum jEnum) {
        this.resultErrno = jEnum.getErrno();
        this.resultErrorMessage = jEnum.getErrMsg();
    }

    public void initSelf(JSONObject jsonObject) {
        if (jsonObject != null) {
            type = JsEnum.IntentType.resolve(jsonObject.optString("api"));
            callback = jsonObject.optString("callback");
            data = jsonObject.optJSONObject("data");
        }
        if (callback == null)
            callback = "";
        if (data == null)
            data = new JSONObject();
    }
}
