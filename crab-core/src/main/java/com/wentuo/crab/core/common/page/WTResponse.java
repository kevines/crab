package com.wentuo.crab.core.common.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;

/**
 * 功能描述：接口数据响应返回结果封装
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName WTResponse
 * @since 2019/10/28 11:28
 */
public class WTResponse<T> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(WTResponse.class);

    /**
     * 返回请求的状态码
     */
    public static final Integer SUCCESS = 200;
    public static final Integer ERROR = 500;
    public static final Integer NULL = 400;
    public static final Integer NOPERMISSION = 403;
    public static final Integer EXPIRSE = 302;
    public static final Integer NULLRESULT = 204;   //空结果返回状态码

    private Integer code = 200;
    private String message = "操作成功";
    private T data;

    public WTResponse() {}
    public WTResponse(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public WTResponse(Integer errCode, String errorMessage) {
        this.code = errCode;
        this.message = errorMessage;
    }

    public WTResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public WTResponse(T data) {
        this.code = SUCCESS;
        this.data = data;
        this.message = "操作成功";
    }

    public WTResponse(String message) {
        this.code = SUCCESS;
        this.message = message;
    }

    public static WTResponse success() {
        return new WTResponse();
    }

    /**
     * 返回成功的状态码
     * @param data
     * @return
     */
    public static WTResponse success(Object data) {
        return new WTResponse(data);
    }

    /**
     * 返回成功的状态码
     * @param message
     * @return
     */
    public static WTResponse success(String message) {
        return new WTResponse(message);
    }

    /**
     * 返回成功的状态码
     * @param message
     * @param data
     * @return
     */
    public static WTResponse success(String message, Object data) {
        return new WTResponse(WTResponse.SUCCESS, message, data);
    }

    /**
     * 服务报错5xx
     * @param message
     * @return
     */
    public static WTResponse error(String message) {
        return new WTResponse(BizExceptionEnum.ERROR.getCode(), message);
    }

    /**
     * 服务报错5xx
     * @param message
     * @param data
     * @return
     */
    public static WTResponse error(String message, Object data) {
        return new WTResponse(BizExceptionEnum.ERROR.getCode(), message, data);
    }

    /**
     * 空指针造成的查询到的空记录返回码
     * @param message
     * @return
     */
    public static WTResponse nullError(String message) {
        return new WTResponse(WTResponse.NULL, message);
    }

    /**
     * 空指针造成的查询到的空记录返回码及数据
     * @param message
     * @param data
     * @return
     */
    public static WTResponse nullError(String message, Object data) {
        return new WTResponse(WTResponse.NULL, message, data);
    }

    /**
     * 空记录返回状态码及返回文字提示内容
     * @param message
     * @return
     */
    public static WTResponse nullResult(String message) {
        return new WTResponse(WTResponse.NULLRESULT, message);
    }

    /**
     * 空记录返回状态码及返回文字提示内容和数据
     * @param message
     * @param data
     * @return
     */
    public static WTResponse nullResult(String message, Object data) {
        return new WTResponse(WTResponse.NULLRESULT, message, data);
    }

    public static void writeResponse(Object T) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return;
        }
        requestAttributes.getResponse().setContentType("text/plain;charset=UTF-8");
        try {

            WTResponse response = WTResponse.success(T);
            PrintWriter out = requestAttributes.getResponse().getWriter();
            out.print(JSON.toJSONString(response, fastjsonConfig().getSerializeConfig(), fastjsonConfig().getSerializerFeatures()));
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            logger.error("writeStringResponse error:" + ex);
        }
    }

    public static FastJsonConfig fastjsonConfig() {
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(new SerializerFeature[]{SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect});
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        fastJsonConfig.setCharset(Charset.forName("utf-8"));
        initOtherValueFilters(fastJsonConfig);
        return fastJsonConfig;
    }


    protected static void initOtherValueFilters(FastJsonConfig fastJsonConfig) {
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

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
