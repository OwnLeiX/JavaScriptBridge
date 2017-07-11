package com.michong.js;

import com.michong.js.config.JsEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

final public class JsResultUtil {

    public static JSONObject buildThridLoginResult(String userInfoJsonString) throws JSONException {
        return new JSONObject(userInfoJsonString);
    }

    public static JSONObject buildThridShareResult() {
        return new JSONObject();
    }

    public static JSONObject buildGetTokenResult(String token) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(JsEnum.GetToken.callbackParamName, token);
        return result;
    }

    public static JSONObject buildUploadImageResult(String url) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(JsEnum.UpLoadImage.callbackParamName, url);
        return result;
    }

    public static JSONObject buildHyperLinkResult() {
        return new JSONObject();
    }

    public static JSONObject buildQueryNetworkStatusResult(JsEnum.QueryNetworkStatus.CallbackParam status) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(JsEnum.QueryNetworkStatus.callbackParamName, status.getValue());
        return result;
    }

    public static JSONObject buildPickContactResult(String name, String... numbers) throws JSONException {
        JSONObject result = new JSONObject();
        JSONArray numbersArray = new JSONArray();
        if (numbers != null) {
            for (int i = 0; i < numbers.length; i++) {
                numbersArray.put(numbers[i]);
            }
        }
        result.put(JsEnum.PickContact.callbackParamName1, name);
        result.put(JsEnum.PickContact.callbackParamName2, numbersArray);
        return result;
    }

    public static JSONObject buildSendSMSResult() {
        return new JSONObject();
    }

    public static JSONObject buildCallPhoneResult() {
        return new JSONObject();
    }

    public static JSONObject buildScanQRCodeResult(String content) throws JSONException {
        JSONObject result = new JSONObject();
        result.put(JsEnum.ScanQRCode.callbackParamName, content);
        return result;
    }
}
