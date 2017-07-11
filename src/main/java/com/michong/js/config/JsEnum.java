package com.michong.js.config;

/**
 * <b></b>
 * Created on 2017/6/20.
 *
 * @author LeiXun
 */

public interface JsEnum {
    String HEADER = "com.michong.h5.web";
    String SET_TITLE = HEADER + ".setTitle"; //设置标题
    String THIRD_LOGIN = HEADER + ".login"; //三方登录
    String THIRD_SHARE = HEADER + ".share"; //三方分享
    String SAVE_TOKEN = HEADER + ".saveToken"; //保存Token
    String GET_TOKEN = HEADER + ".getToken"; //获取Token
    String UPLOAD_IMAGE = HEADER + ".uploadImage"; //上传图片
    String INTERCEPT_BACK = HEADER + ".interceptBack"; //是否拦截物理返回键
    String CAN_BACK = HEADER + ".canBack"; //是否显示导航栏返回键
    String HYPER_LINK = HEADER + ".hyperLink";
    String FINISH = HEADER + ".finish";
    String QUERY_NETWORK_STATUS = HEADER + ".networkReachability"; //网络状态
    String PICK_CONTACT = HEADER + ".pickContact"; //网络状态
    String CALL_PHONE = HEADER + ".call"; //拨打电话
    String SEND_SMS = HEADER + ".sendSMS"; //发送短信
    String SCAN_QR_CODE = HEADER + ".scanQRCode"; //扫描二维码

    enum IntentType {
        Unknown(""),
        SetTitle(SET_TITLE),
        ThirdLogin(THIRD_LOGIN),
        ThirdShare(THIRD_SHARE),
        SaveToken(SAVE_TOKEN),
        GetToken(GET_TOKEN),
        UploadImage(UPLOAD_IMAGE),
        InterceptBack(INTERCEPT_BACK),
        CanBack(CAN_BACK),
        HyperLink(HYPER_LINK),
        Finish(FINISH),
        QueryNetworkStatus(QUERY_NETWORK_STATUS),
        PickContact(PICK_CONTACT),
        CallPhone(CALL_PHONE),
        SendSMS(SEND_SMS),
        ScanQRCode(SCAN_QR_CODE);

        private String apiName;

        IntentType(String apiName) {
            this.apiName = apiName;
        }

        public String getApiName() {
            return apiName;
        }

        public static IntentType resolve(String apiName) {
            IntentType returnValue = Unknown;
            switch (apiName) {
                case SET_TITLE:
                    returnValue = SetTitle;
                    break;
                case THIRD_LOGIN:
                    returnValue = ThirdLogin;
                    break;
                case THIRD_SHARE:
                    returnValue = ThirdShare;
                    break;
                case SAVE_TOKEN:
                    returnValue = SaveToken;
                    break;
                case GET_TOKEN:
                    returnValue = GetToken;
                    break;
                case UPLOAD_IMAGE:
                    returnValue = UploadImage;
                    break;
                case INTERCEPT_BACK:
                    returnValue = InterceptBack;
                    break;
                case CAN_BACK:
                    returnValue = CanBack;
                    break;
                case HYPER_LINK:
                    returnValue = HyperLink;
                    break;
                case FINISH:
                    returnValue = Finish;
                    break;
                case QUERY_NETWORK_STATUS:
                    returnValue = QueryNetworkStatus;
                    break;
                case PICK_CONTACT:
                    returnValue = PickContact;
                    break;
                case CALL_PHONE:
                    returnValue = CallPhone;
                    break;
                case SEND_SMS:
                    returnValue = SendSMS;
                    break;
                case SCAN_QR_CODE:
                    returnValue = ScanQRCode;
                    break;
            }
            return returnValue;
        }
    }

    interface SetTitle {
        String dataKey = "title";
    }

    interface ThirdLogin {
        String dataKey = "platform";
        String loginType_Qq = "qq";
        String loginType_WeChat = "wechat";
        String loginType_WeiBo = "weibo";
    }

    interface ThirdShare {
        String shareIdKey = "shareId";
        String typeKey = "type";
    }

    interface SaveToken {
        String dataKey = "token";
    }

    interface GetToken {
        String callbackParamName = "token";
    }

    interface UpLoadImage {
        String callbackParamName = "imageUrl";

        String maxWidthKey = "maxWidth";
        String maxHeightKey = "maxHeight";
        String maxSizeKey = "maxSize";
    }

    interface InterceptBack {
        String dataKey = "intercept";

        int trueInt = 1;
    }

    interface CanBack {
        String dataKey = "can";

        int trueInt = 1;
    }

    interface HyperLink {
        String linkKey = "link";

        String targetKey = "target";
        String urlKey = "url";
        String jsBridgeEnabledKey = "jsBridgeEnabled";

        String hyperLinkScheme = "michong";
        String hyperLinkHost = "h5.michong.com";

        String targetType_App = "app";
        String targetType_WebView = "webview";
        String targetType_Explorer = "explorer";
    }

    interface QueryNetworkStatus {
        String callbackParamName = "status";

        enum CallbackParam {
            Unknown("unknown"), None("notReach"), Mobile("viaWWAN"), WiFi("viaWIFI");

            private String value;

            CallbackParam(String value) {
                this.value = value;
            }

            public String getValue() {
                return value;
            }
        }
    }

    interface PickContact {
        String callbackParamName1 = "name";
        String callbackParamName2 = "number";
    }

    interface CallPhone{
        String dataKey = "number";
    }

    interface SendSMS{
        String numberKey = "number";
        String contentKey = "content";
    }

    interface ScanQRCode{
        String callbackParamName = "result";
    }

    interface Exception {
        String reloadUrlHeader = "com.michong.h5.web.refresh:";
    }
}
