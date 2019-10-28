package com.wentuo.crab.util;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author lv
 * @date 2019/4/11 13:55
 */
@Component
public class DingtalkRootUtil {
    //钉钉用车内部群通知的机器人的IP：https://oapi.dingtalk.com/robot/send?access_token=86e91687f7d3819a6acc904979e4186a98ad6478bc0ea47594a4de13fe6c5674
    //测试用IP：https://oapi.dingtalk.com/robot/send?access_token=7bf02dfbb2addf3cd6d454ae35117f9dacc72039b77850d1005bef82d1b76ef4
    //研发群的IP：https://oapi.dingtalk.com/robot/send?access_token=e6819374af4d48e77ba64025115b150f5c034623a5fd1e53732dba27b272e510
    //

    /**
     * 钉钉机器人发送消息通用
     *
     * @param area      作用区域判断    0不判 1测试 2研发群 3用车群 4异常提醒群
     * @param content   发送内容
     * @param atMobiles 提示手机号 中间用，分割
     */
    public Boolean send(String area, String content, String atMobiles, Boolean noticeAll) {

        //判断发送区域
        String WEBHOOK_TOKEN = "";
        if (area.equals("1")) {
            WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=7bf02dfbb2addf3cd6d454ae35117f9dacc72039b77850d1005bef82d1b76ef4";
        } else if (area.equals("2")) {
            if(Constant.isProd()){
                WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=c1b3568bb05f17d42dfa3f9882c31f7c9c239ed308f2be2cec827a28c8de477b";
            }else {
                WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=f0a1d43ccf639e221b120ed22e2fa6b86f69d8d2f98b3fa95b527a6dbc0067ba";
            }
        } else if (area.equals("3")) {
            WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=86e91687f7d3819a6acc904979e4186a98ad6478bc0ea47594a4de13fe6c5674";
        } else if (area.equals("4")) {
            WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=943c8123a1a65b0d6a5c8df047bc4d6ccd96b170643a205c71b9c41747baadc3";
        } else {
            WEBHOOK_TOKEN = "https://oapi.dingtalk.com/robot/send?access_token=943c8123a1a65b0d6a5c8df047bc4d6ccd96b170643a205c71b9c41747baadc3";
        }
        HttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(WEBHOOK_TOKEN);
        httppost.addHeader("Content-Type", "application/json; charset=utf-8");
        content = StringUtils.abbreviate(content,1300);
        String textMsg = "{" +
                "     \"msgtype\": \"text\"," +
                "     \"text\": {" +
                "         \"content\": \"" + content + "\"" +
                "     }," +
                "     \"at\": {" +
                "         \"atMobiles\": [" + atMobiles + "], " +
                "         \"isAtAll\": " + noticeAll + "" +
                "     }" +
                " }";
        StringEntity se = new StringEntity(textMsg, "utf-8");
        httppost.setEntity(se);
        try {
            HttpResponse response = httpclient.execute(httppost);
//            String result = EntityUtils.toString(response.getEntity(), "utf-8")
//            System.out.println(result)
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
