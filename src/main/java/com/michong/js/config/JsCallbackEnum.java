package com.michong.js.config;

/**
 * <b> </b><br/>
 *
 * @author LeiXun
 *         Created on 2017/7/6.
 */

public enum JsCallbackEnum {
    Succeed(0, ""), Failed(1, "失败"), Canceled(2, "取消"),Refused(3,"权限拒绝");

    private int errno;
    private String errMsg;

    JsCallbackEnum(int errno, String errMsg) {
        this.errMsg = errMsg;
        this.errno = errno;
    }

    public int getErrno() {
        return errno;
    }

    public String getErrMsg() {
        return errMsg;
    }
}
