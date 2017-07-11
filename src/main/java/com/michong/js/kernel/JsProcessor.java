package com.michong.js.kernel;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Base64;

import com.michong.js.JsIntentInterceptor;
import com.michong.js.JsIntentSubscriber;
import com.michong.js.JsCallbackSubscriber;
import com.michong.js.config.JsEnum;
import com.michong.js.config.JsError;
import com.michong.js.entity.JsCallbackEntity;
import com.michong.js.entity.JsIntentEntity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>Js处理者</b>
 * Created on 2017/6/22.
 *
 * @author LeiXun
 */

final public class JsProcessor {
    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
    private JsIntentSubscriber mSubscriber;
    private JsIntentInterceptor mInterceptor;
    private JsCallbackSubscriber mCallbackSubscriber;

    /**
     * 检查该JsIntent是否能构建回调（主要检查intent是否有设置成功或失败结果）
     *
     * @param intent JsIntent
     */
    public boolean checkCanBuildCallback(JsIntentEntity intent) {
        return (intent.getResultParam() != null && intent.getResultErrno() == 0)  //设置了成功结果的JsIntentEntity
                || (intent.getResultErrno() != 0);//设置了失败结果的JsIntentEntity
    }

    /**
     * 根据JsIntent构建JsCallback
     *
     * @param intent 已经调用过{@link JsIntentEntity#setSucceedResult(String...)} 或 {@link JsIntentEntity#setFailedResult(int, String)} 的JsIntent
     * @return 根据JsIntent构建的JsCallback
     */
    public void buildJsCallback(JsIntentEntity intent) {
        if (mCallbackSubscriber != null) {
            final JsCallbackSubscriber subscriber = mCallbackSubscriber;
            final JsCallbackEntity callback = JsCallbackEntity.build(intent);
            mMainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    subscriber.onBuildJsCallbackSucceed(callback, callback.getCallbackContent());
                }
            });
        }
    }

    /**
     * 创建[返回键被按下]的Js回调
     *
     * @return [返回键被按下]的Js回调
     */
    public void buildBackPressedJsCallback() {
        if (mCallbackSubscriber != null) {
            JsCallbackEntity backPressed = JsCallbackEntity.buildBackPressedCallback();
            mCallbackSubscriber.onBuildJsCallbackSucceed(backPressed, backPressed.getCallbackContent());
        }
    }

    /**
     * 根据json格式的String 解析JsIntent
     *
     * @param jsonString jsonString格式的JsIntent
     */
    public JsIntentEntity parseJsMessage(String jsonString) {
        JsIntentEntity returnValue = null;
        try {
            returnValue = new JsIntentEntity(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    /**
     * 自动解析JsIntent数据，通过callback的各种不同回调来实现
     *
     * @param intent Js意图
     */
    public void processJsIntent(final JsIntentEntity intent) {
        if (intent == null || mSubscriber == null || interceptIntent(intent))
            return;
        final JsIntentSubscriber subscriber = mSubscriber;
        JSONObject data = intent.getData();
        if (data != null) {
            try {
                switch (intent.getType()) {
                    case SetTitle:
                        final String title = data.getString(JsEnum.SetTitle.dataKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onSetTitleIntent(intent, title);
                            }
                        });
                        break;
                    case SaveToken:
                        final String token = data.getString(JsEnum.SaveToken.dataKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onSaveTokenIntent(intent, token);
                            }
                        });
                        break;
                    case GetToken:
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onGetTokenIntent(intent);
                            }
                        });
                        break;
                    case UploadImage:
                        final int width = data.getInt(JsEnum.UpLoadImage.maxWidthKey);
                        final int height = data.getInt(JsEnum.UpLoadImage.maxHeightKey);
                        final long maxSize = data.getLong(JsEnum.UpLoadImage.maxSizeKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onUploadImageIntent(intent, width, height, maxSize);
                            }
                        });
                        break;
                    case ThirdLogin:
                        final String type = data.getString(JsEnum.ThirdLogin.dataKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onThirdLoginIntent(intent, type);
                            }
                        });
                        break;
                    case CanBack:
                        final boolean can = data.getInt(JsEnum.CanBack.dataKey) == JsEnum.CanBack.trueInt;
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onCanBackIntent(intent, can);
                            }
                        });
                        break;
                    case InterceptBack:
                        final boolean intercept = data.getInt(JsEnum.InterceptBack.dataKey) == JsEnum.InterceptBack.trueInt;
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onInterceptBackButtonIntent(intent, intercept);
                            }
                        });
                        break;
                    case ThirdShare:
                        final String shareId = data.getString(JsEnum.ThirdShare.shareIdKey);
                        final String shareType = data.getString(JsEnum.ThirdShare.typeKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onThirdShareIntent(intent, shareId, shareType);
                            }
                        });
                        break;
                    case HyperLink:
                        String link = data.getString(JsEnum.HyperLink.linkKey);
                        if (TextUtils.isEmpty(link))
                            throw new JSONException("data is null");
                        Pattern pattern = Pattern.compile(String.format(Locale.ENGLISH, "^%1$s://%2$s/", JsEnum.HyperLink.hyperLinkScheme, JsEnum.HyperLink.hyperLinkHost),
                                Pattern.CASE_INSENSITIVE);
                        Matcher matcher = pattern.matcher(link);
                        if (matcher.find()) {
                            String linkData = matcher.replaceFirst("");
                            byte[] decodeBytes = Base64.decode(linkData, Base64.DEFAULT);
                            String decodedLinkData = new String(decodeBytes);
                            JSONObject linkDataJsonObject = new JSONObject(decodedLinkData);
                            final String target = linkDataJsonObject.getString(JsEnum.HyperLink.targetKey);
                            final String url = linkDataJsonObject.getString(JsEnum.HyperLink.urlKey);
                            final boolean jsBridgeEnabled = linkDataJsonObject.optInt(JsEnum.HyperLink.jsBridgeEnabledKey, 0) == 1;
                            mMainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    subscriber.onHyperLinkIntent(intent, target, url, jsBridgeEnabled);
                                }
                            });
                        } else {
                            mMainThreadHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    subscriber.onParseError(JsError.ERROR_CODE_PARSE_UNKNOWN_HYPER_LINK, JsError.ERROR_MSG_PARSE_UNKNOWN_HYPER_LINK);
                                }
                            });
                        }
                        break;
                    case Finish:
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onFinishIntent(intent);
                            }
                        });
                        break;
                    case QueryNetworkStatus:
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onNetworkQueryIntent(intent);
                            }
                        });
                        break;
                    case PickContact:
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onPickContactIntent(intent);
                            }
                        });
                        break;
                    case CallPhone:
                        final String number1 = data.getString(JsEnum.SendSMS.numberKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onCallPhoneIntent(intent, number1);
                            }
                        });
                        break;
                    case SendSMS:
                        final String number2 = data.getString(JsEnum.SendSMS.numberKey);
                        final String content = data.getString(JsEnum.SendSMS.contentKey);
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onSendSMSIntent(intent, number2, content);
                            }
                        });
                        break;
                    case ScanQRCode:
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onScanQRCodeIntent(intent);
                            }
                        });
                        break;
                    default:
                        mMainThreadHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                subscriber.onParseError(JsError.ERROR_CODE_PARSE_UNKNOWN_TYPE, JsError.ERROR_MSG_PARSE_UNKNOWN_TYPE);
                            }
                        });
                        break;
                }
            } catch (JSONException e) {
                mMainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        subscriber.onParseError(JsError.ERROR_CODE_PARSE_NO_FIELD, JsError.ERROR_MSG_PARSE_NO_FIELD);
                    }
                });
            }
        } else {
            mMainThreadHandler.post(new Runnable() {
                @Override
                public void run() {
                    subscriber.onParseError(JsError.ERROR_CODE_PARSE_NO_DATA, JsError.ERROR_MSG_PARSE_NO_DATA);
                }
            });
        }
    }

    public void setIntentSubscriber(JsIntentSubscriber subscriber) {
        this.mSubscriber = subscriber;
    }

    public void removeIntentSubscriber() {
        this.mSubscriber = null;
    }

    public void setCallbackSubscriber(JsCallbackSubscriber subscriber) {
        this.mCallbackSubscriber = subscriber;
    }

    public void removeCallbackSubscriber() {
        this.mCallbackSubscriber = null;
    }

    public void setIntentInterceptor(JsIntentInterceptor mInterceptor) {
        this.mInterceptor = mInterceptor;
    }

    public void removeIntentInterceptor() {
        this.mInterceptor = null;
    }

    public void reset() {
        mInterceptor = null;
        mSubscriber = null;
        mCallbackSubscriber = null;
        mMainThreadHandler.removeCallbacksAndMessages(null);
    }

    private boolean interceptIntent(JsIntentEntity intent) {
        boolean intercept = false;
        if (mInterceptor != null)
            intercept = mInterceptor.onInterceptJsIntent(intent);
        return intercept;
    }
}
