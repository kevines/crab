package com.wentuo.crab.util;

import com.wentuo.crab.core.common.constant.MyProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author lv
 * @date 2019/4/11 13:58
 */
@Configuration
public class StaticConfigUtil {

    @Autowired
    public static MyProperties myProperties;

    @Value("${spring.env}")
    private String name;

    @Value("${server.port}")
    private  String port;



    @Bean
    public int init(){
        myProperties = new MyProperties();
        myProperties.setEnv(this.name);
        Constant.PORT=port;
        return 0;
    }

    public static String getEnv(){
        return myProperties.getEnv();
    }



}
