package com.michong.js.config;

/**
 * <b></b>
 * Created on 2017/6/23.
 *
 * @author LeiXun
 */

public interface JsError {
    int ERROR_CODE_PARSE_UNKNOWN_TYPE = 100;
    String ERROR_MSG_PARSE_UNKNOWN_TYPE = "未知的JsIntent类型";

    int ERROR_CODE_PARSE_NO_DATA = 101;
    String ERROR_MSG_PARSE_NO_DATA = "JsIntent内(JsonObject)data字段为null";


    int ERROR_CODE_PARSE_NO_FIELD = 102;
    String ERROR_MSG_PARSE_NO_FIELD = "JsIntent中data内不含该类型应携带的数据";

    int ERROR_CODE_PARSE_UNKNOWN_HYPER_LINK = 102;
    String ERROR_MSG_PARSE_UNKNOWN_HYPER_LINK = "未知的超链格式";
}
