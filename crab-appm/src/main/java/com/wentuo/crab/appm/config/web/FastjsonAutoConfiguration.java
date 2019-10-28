//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.wentuo.crab.appm.config.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration("defaultFastjsonConfig")
@ConditionalOnClass({JSON.class})
@ConditionalOnMissingBean({FastJsonHttpMessageConverter.class})
@ConditionalOnWebApplication
public class FastjsonAutoConfiguration {
    public FastjsonAutoConfiguration() {
    }

    @Bean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setFastJsonConfig(this.fastjsonConfig());
        converter.setSupportedMediaTypes(this.getSupportedMediaType());
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        return converter;
    }

    public FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect});
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        this.initOtherValueFilters(fastJsonConfig);
        return fastJsonConfig;
    }

    public List<MediaType> getSupportedMediaType() {
        ArrayList<MediaType> mediaTypes = new ArrayList();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        mediaTypes.add(MediaType.valueOf("application/vnd.spring-boot.actuator.v2+json"));
        return mediaTypes;
    }

    protected void initOtherValueFilters(FastJsonConfig fastJsonConfig) {
        ValueFilter nullValueFilter = (object, name, value) -> {
            return value;
        };
        ValueFilter longValueFilter = (object, name, value) -> {
            if (value instanceof Long) {
                return String.valueOf(value);
            } else {
                return value instanceof BigInteger ? String.valueOf(value) : value;
            }
        };
        fastJsonConfig.setSerializeFilters(new SerializeFilter[]{nullValueFilter, longValueFilter});
    }
}
