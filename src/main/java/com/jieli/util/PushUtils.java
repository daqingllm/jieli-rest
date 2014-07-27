package com.jieli.util;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

import com.baidu.push.DefaultPushClient;
import com.baidu.push.PushClient;
import com.baidu.push.auth.PushCredentials;
import com.baidu.push.model.*;
import com.baidu.push.reqeust.DeleteTagRequest;
import com.baidu.push.reqeust.FetchTagRequest;
import com.baidu.push.reqeust.PushMessageRequest;
import com.baidu.push.reqeust.SetTagRequest;
import com.baidu.push.response.PushResponse;
import com.baidu.push.util.PushServiceException;
import com.jieli.admin.CommonUtil;
import com.jieli.common.entity.PushMessageResult;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created by 95 on 2014/7/19.
 */
public class PushUtils {
    //public static final Logger LOG = LoggerFactory.getLogger(PushUtils.class);

    private static final String appKey_QQX_IOS ="8b0672e88ad20bab12da5dfa";
    private static final String masterSecret_QQX_IOS = "81181342c7b0f1e19c8c3ea2";

    private static final String appKey_STD_IOS = "6c45dde6609ce59b54880500";
    private static final String masterSecret_STD_IOS = "a2d417a7c25d14bb024fe7ec";

    public static final String appKey_Android = "fIhrD0s2NdStFD6LoehGRp36";
    public static final String masterSecret_Android = "55So91iaISqztcRflBb2slQG4NEx6MsK";

    /*
    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    */
    public static PushMessageResult pushMessageToAssociation(String message, String associationId){
        if (StringUtils.isEmpty(message) || StringUtils.isEmpty(associationId)){
            return PushMessageResult.SKIP;
        }

        try {
            PushMessageResult pushMessageResultIOS = pushMessageToAssociation_IOS(message, associationId);
            PushMessageResult pushMessageResultAndroid = pushMessageToAssociation_Android(message, associationId);

            PushMessageResult result = pushMessageResultIOS;
            if (result == PushMessageResult.SUCCESS){
                result = pushMessageResultAndroid;
            } else if (pushMessageResultAndroid == PushMessageResult.NO_GROUP){
                result = PushMessageResult.NO_GROUP;
            }
            return result;
        } catch (PushServiceException e){
            int code = e.getPushErrorCode();
            if (code == 30611){
                return PushMessageResult.NO_GROUP;
            } else {
                return PushMessageResult.OTHER_ERROR;
            }
        }
    }

    public static PushMessageResult pushMessageToAssociation_IOS(String message, String associationId) {
        JPushClient jPushClient = null;

        // 根据协会Id初始化client
        if (CommonUtil.QQX_AssociationId.equals(associationId)) {
            jPushClient = new JPushClient(masterSecret_QQX_IOS, appKey_QQX_IOS, 3);
        } else {
            jPushClient = new JPushClient(masterSecret_STD_IOS, appKey_STD_IOS, 3);
        }

        if (jPushClient == null) return PushMessageResult.OTHER_ERROR;

        PushPayload payload = buildPushObject(message,associationId);

        try {
            PushResult result = jPushClient.sendPush(payload);
            return PushMessageResult.SUCCESS;
        } catch (APIConnectionException e) {
            return PushMessageResult.NETWORK;
        } catch (APIRequestException e) {
            return PushMessageResult.OTHER_ERROR;
        }
    }

    public static PushMessageResult pushMessageToAssociation_Android(String message, String associationId){
        PushClient client = new DefaultPushClient(new PushCredentials(appKey_Android, masterSecret_Android));

        // 由于Android端不区分通用版和青企协版所以只用一个就行
        if (client == null) return PushMessageResult.OTHER_ERROR;

        PushMessageRequest request = new PushMessageRequest();

        // 必须使用notify 不能使用message
        request.setMessageType(MessageType.notify);
        String msg = String.format("{'title':'%s','description':'%s','notification_builder_id':0,'notification_basic_style':3,'open_type':2}", "协会助手",
                message);

        request.setMessages(msg);
        request.setTag(associationId);

        request.setPushType(PushType.tag_user);
        request.setMessageKeys("msgkeys");

        PushResponse<Integer> response = client.pushMessage(request);

        if (response.getResult() > 0) return PushMessageResult.SUCCESS;
        else return PushMessageResult.OTHER_ERROR;
    }

    private static void pushMessageToAssociation_Android_ALL(){
        PushClient client = new DefaultPushClient(new PushCredentials(appKey_Android, masterSecret_Android));
        PushMessageRequest request = new PushMessageRequest();
        request.setMessageType(MessageType.notify);
        String msg = String.format("{'title':'%s','description':'%s','notification_builder_id':1,'notification_basic_style':1,'open_type':2,'custom_content':{'test':'test'}}", "协会助手",
                "发给所有用户的信息!");

        request.setMessages(msg);
        request.setPushType(PushType.all_user);
        request.setMessageKeys("msgkeys");

        PushResponse<Integer> response = client.pushMessage(request);

        System.out.println(response);
    }

    public static void AddTagAndroid(String tag){
        PushClient client = new DefaultPushClient(new PushCredentials(appKey_Android,
                masterSecret_Android));
        SetTagRequest request = new SetTagRequest(tag);
        PushResponse<Empty> response = client.setTag(request);

        System.out.println(response);
    }

    private static void PrintAllTags(){
        PushClient client = new DefaultPushClient(new PushCredentials(appKey_Android,
                masterSecret_Android));

        FetchTagRequest request = new FetchTagRequest();

        PushResponse<FetchTagBean> response = client.fetchTag(request);
        List<Tag> tags = response.getResult().getTags();
        for (Tag eachTag : tags){
            System.out.println(eachTag.getName());
        }

    }

    private static boolean HaveTag(String tag) {
        if (StringUtils.isEmpty(tag)) return false;

        PushClient client = new DefaultPushClient(new PushCredentials(appKey_Android,
                masterSecret_Android));

        FetchTagRequest request = new FetchTagRequest();

        PushResponse<FetchTagBean> response = client.fetchTag(request);
        List<Tag> tags = response.getResult().getTags();
        for (Tag eachTag : tags){
            if (tag.equals(eachTag.getName())){
                return true;
            }
        }
        return false;
    }

    public static void deleteTag(String tag) {
        PushClient client = new DefaultPushClient(new PushCredentials(appKey_Android,
                masterSecret_Android));

        DeleteTagRequest request = new DeleteTagRequest(tag);

        try {
            PushResponse<Empty> response = client.deleteTag(request);
            System.out.println(response);
        } catch (PushServiceException pse) {
            System.out.println(pse);
        }
    }

    private static PushPayload buildPushObjectTest(String message){
        return PushPayload.alertAll(message);
    }

    public static PushPayload buildPushObject(String alertMessage,String associationId){
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(associationId))
                .setNotification(Notification.ios(alertMessage,null))
                .build();
    }

}
