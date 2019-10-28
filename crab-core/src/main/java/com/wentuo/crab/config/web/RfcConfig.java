package com.wentuo.crab.config.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述：修改tomcat中不识别的特殊字符
 *
 * @author wangbencheng
 * @version 1.0
 * @className RfcConfig
 * @since 2019/9/26 11:49
 */
@Configuration
public class RfcConfig {
    @Bean
    public Integer setRfc()
    {
        // 指定jre系统属性，允许特殊符号， 如{} 做入参，其他符号按需添加。见 tomcat的HttpParser源码。
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "|{}");
        return 0;
    }
}
