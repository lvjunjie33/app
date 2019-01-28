package snod.com.cn.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import snod.com.cn.controller.InitController;
import snod.com.cn.entity.JpushConfig;

@Service
public class JpushService {
	private final static Logger logger = LoggerFactory.getLogger(InitController.class);
	@Resource
    JpushConfig jpushConfig;// 注入配置信息
	
	 /**
     * 发送自定义推送，由APP端拦截信息后再决定是否创建通知(目前APP用此种方式)
     * 
     * @param title
     *            App通知栏标题
     * @param content
     *            App通知栏内容（为了单行显示全，尽量保持在22个汉字以下）
     * @param extrasMap
     *            额外推送信息（不会显示在通知栏，传递数据用）
     * @param alias
     *            别名数组，设定哪些用户手机能接收信息（为空则所有用户都推送）
     * @return PushResult
     */
    public PushResult sendCustomPush(String title, String content, Map<String, String> extrasMap, String... alias) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        clientConfig.setTimeToLive(Long.valueOf(jpushConfig.getLiveTime()));
        // 使用NativeHttpClient网络客户端，连接网络的方式，不提供回调函数
        JPushClient jpushClient = new JPushClient(jpushConfig.getMasterSecret(), jpushConfig.getAppkey(), null,
                clientConfig);
        // 设置为消息推送方式为仅推送消息，不创建通知栏提醒
        PushPayload payload = buildCustomPushPayload(title, content, extrasMap, alias);
        PushResult result = null;
        try {
            result = jpushClient.sendPush(payload);
            logger.info("极光推送结果 - " + result+",接收推送的别名列表:" + String.join(",", alias));
        } catch (APIConnectionException e) {
            logger.error("极光推送连接错误，请稍后重试 ", e);
            logger.error("Sendno: " + payload.getSendno());
        } catch (APIRequestException e) {
            logger.error("极光服务器响应出错，请修复！ ", e);
            logger.info("HTTP Status: " + e.getStatus());
            logger.info("Error Code: " + e.getErrorCode());
            logger.info("Error Message: " + e.getErrorMessage());
            logger.info("Msg ID: " + e.getMsgId());
            logger.info("以下存在不能识别的别名: " + String.join(",", alias));
            logger.error("Sendno: " + payload.getSendno());
        }
        return result;
    }
    
    /**
     * 构建Android和IOS的自定义消息的推送通知对象
     * 
     * @return PushPayload
     */
    private PushPayload buildCustomPushPayload(String title, String content, Map<String, String> extrasMap,
            String... alias) {
        // 批量删除数组中空元素
        String[] newAlias = removeArrayEmptyElement(alias);
        return PushPayload.newBuilder().setPlatform(Platform.android_ios())
                .setAudience((null == newAlias || newAlias.length == 0) ? Audience.all() : Audience.alias(alias))
                .setMessage(Message.newBuilder().setTitle(title).setMsgContent(content).addExtras(extrasMap).build())
                .build();
    }

    /**
     * 删除别名中的空元素（需删除如：null,""," "）
     * 
     * @param strArray
     * @return String[]
     */
    private String[] removeArrayEmptyElement(String... strArray) {
        if (null == strArray || strArray.length == 0) {
            return null;
        }
        List<String> tempList = Arrays.asList(strArray);
        List<String> strList = new ArrayList<String>();
        Iterator<String> iterator = tempList.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            // 消除空格后再做比较
            if (null != str && !"".equals(str.trim())) {
                strList.add(str);
            }
        }
        // 若仅输入"",则会将数组长度置为0
        String[] newStrArray = strList.toArray(new String[strList.size()]);
        return newStrArray;
    }

}
