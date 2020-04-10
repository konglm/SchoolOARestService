/*----------------------------------------------------------------
 *  Copyright (C) 2017山东金视野教育科技股份有限公司
 * 版权所有。 
 *
 * 文件名：
 * 文件功能描述：
 *
 * 
 * 创建标识：
 *
 * 修改标识：
 * 修改描述：
 *----------------------------------------------------------------*/

package com.goldeneyes.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.AppMessage;
import com.gexin.rp.sdk.base.impl.ListMessage;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.uitls.AppConditions;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.gexin.rp.sdk.template.NotificationTemplate;
import com.gexin.rp.sdk.template.TransmissionTemplate;
import com.gexin.rp.sdk.template.style.Style0;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.base.payload.APNPayload;
/**
 * @author Administrator
 *
 */
public class PushUtil {
	
	//定义常量, appId、appKey、masterSecret 
    private static String appId = "rzMQZq71G2ATOk0drueaE6";
    private static String appSecret = "BSl2t3PZCj9t4NuBmBMks1";
    private static String appKey = "PM3wC8cqTa9LwF3jbNuAg8";
    private static String masterSecret = "xaAGJoD64L8kOiRXIZeWX3";
    //private static String host = "http://sdk.open.api.igexin.com/apiex.htm";
    private static String host = "https://api.getui.com/apiex.htm";
    
    public static void main(String[] args) {
//    	List<Integer> ids = new ArrayList<Integer>();
//    	ids.add(150);
//    	pushMsg(ids,"测试","测试");
    	pushMsg(150l,"测试","测试", 5);
    }
	
    /**
     * 推送消息
     * @param title
     * @param content
     */
//	public static void pushMsg(List<Integer> ids, String title, String content) {     
    public static void pushMsg(Long cid, String title, String content, int noReadCnt) {
        // 配置返回每个别名及其对应cid的用户状态，可选
        //System.setProperty("gexin_pushList_needAliasDetails", "true");
        IGtPush push = new IGtPush(host, appKey, masterSecret);
        // 通知透传模板
        TransmissionTemplate template = templateApp(title, content, noReadCnt);
//        ListMessage message = new ListMessage();
        SingleMessage message = new SingleMessage();
        message.setData(template);
        // 设置消息离线，并设置离线时间
        message.setOffline(true);
        // 离线有效时间，单位为毫秒，可选
        message.setOfflineExpireTime(24 * 1000 * 3600);
        // 配置推送目标
//        List targets = new ArrayList();
//        for(int cid: ids){
        	Target target = new Target();
        	target.setAppId(appId);
            target.setAlias(String.valueOf(cid));
//            targets.add(target);
//		}
        // taskId用于在推送时去查找对应的message
//        String taskId = push.getContentId(message);
//        System.out.println(taskId);
//        IPushResult ret = push.pushMessageToList(taskId, targets);
        IPushResult ret = push.pushMessageToSingle(message, target);
        final Log logger = LogFactory.getLog(PushUtil.class);
        logger.info(cid);
        logger.info(ret.getResponse().toString());
        System.out.println(ret.getResponse().toString());
	}
	/**
	 * 推送消息模板
	 * @param title
	 * @param content
	 * @return
	 */
	public static TransmissionTemplate templateApp(String title, String content, int noReadCnt) {
		TransmissionTemplate template = new TransmissionTemplate();
        // 设置APPID与APPKEY
        template.setAppId(appId);
        template.setAppkey(appKey);
        // 透传消息设置，1为强制启动应用，客户端接收到消息后就会立即启动应用；2为等待应用启动
        template.setTransmissionType(2);
        template.setTransmissionContent(content);
        // 设置定时展示时间
        // template.setDuration("2015-01-16 11:40:00", "2015-01-16 12:24:00");
        
        APNPayload payload = new APNPayload();
        //在已有数字基础上加1显示，设置为-1时，在已有数字上减1显示，设置为数字时，显示指定数字
        payload.setAutoBadge(String.valueOf(noReadCnt));
        payload.setContentAvailable(1);
        payload.setSound("default");
        payload.setCategory("");

        //简单模式APNPayload.SimpleMsg
        payload.setAlertMsg(new APNPayload.SimpleAlertMsg(content));

        template.setAPNInfo(payload);

//        Style0 style = new Style0();
//        // 设置通知栏标题与内容
//        style.setTitle(title);
//        style.setText(content);
//        // 配置通知栏图标
//        style.setLogo("push.png");
//        // 配置通知栏网络图标
//        style.setLogoUrl("");
//        // 设置通知是否响铃，震动，或者可清除
//        style.setRing(true);
//        style.setVibrate(true);
//        style.setClearable(true);
//        template.setStyle(style);


        return template;
    }
}
