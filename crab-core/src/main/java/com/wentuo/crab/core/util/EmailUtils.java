package com.wentuo.crab.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Exrickx
 */
@Component
@Slf4j
public class EmailUtils {

//
//    @Autowired
//    private JavaMailSender mailSender;
//
//
//    /**
//     * 发送模版邮件
//     */
//    @Async
//    public void sendMail(String theme, String text) {
//        log.info(text);
//        //用于封装邮件信息的实例
//        SimpleMailMessage smm = new SimpleMailMessage();
//        //由谁来发送邮件
//        smm.setFrom("yuwenbo@iwentuo.com");
//        //邮件主题
//        smm.setSubject(StaticConfigUtil.getEnv() + theme);
//        //邮件内容
////        smm.setText(Constant.isProd() + "======账单效验有误,请及时核对");
//        smm.setText(text);
//        //接受邮件
//        String[] to = {"yuwenbo@51ganjie.com", "yanxiaodan@51ganjie.com"};
//        smm.setTo(to);
//        try {
//            mailSender.send(smm);
//            log.info("======================邮箱发送成功================================");
//        } catch (Exception e) {
//            e.printStackTrace();
//            log.info("======================邮箱发送失败======================");
//        }
//    }


}
