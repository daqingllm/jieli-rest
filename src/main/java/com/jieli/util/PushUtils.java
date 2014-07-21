package com.jieli.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import com.jieli.common.entity.PushMessageResult;
import org.apache.commons.lang.StringUtils;

/**
 * Created by 95 on 2014/7/19.
 */
public class PushUtils {
    //public static final Logger LOG = LoggerFactory.getLogger(PushUtils.class);

    private static final String appKey ="8b0672e88ad20bab12da5dfa";
    private static final String masterSecret = "81181342c7b0f1e19c8c3ea2";

    /*
    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    */

    public static PushMessageResult pushMessageToAssociation(String message,String associationId) {
        if (StringUtils.isEmpty(message) || StringUtils.isEmpty(associationId)){
            return PushMessageResult.SKIP;
        }

        JPushClient jPushClient = new JPushClient(masterSecret, appKey, 3);
        PushPayload payload = buildPushObject(message,associationId);

        try {
            PushResult result = jPushClient.sendPush(payload);
            return PushMessageResult.SUCCESS;
            //LOG.info("Push Success , msg_id : " + result.msg_id + " , sendno : " + result.sendno);
        } catch (APIConnectionException e) {
            return PushMessageResult.NETWORK;
            //LOG.error("Connection error. Should retry later. ", e);
        } catch (APIRequestException e) {
            return PushMessageResult.OTHER_ERROR;
            //LOG.error("Error response from JPush server. Should review and fix it. ", e);
            //LOG.info("HTTP Status: " + e.getStatus());
            //LOG.info("Error Code: " + e.getErrorCode());
            //LOG.info("Error Message: " + e.getErrorMessage());
        }
    }

    public static PushPayload buildPushObject(String alertMessage,String associationId){
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(associationId))
                .setNotification(Notification.android(alertMessage, alertMessage, null))
                .build();
    }

}
