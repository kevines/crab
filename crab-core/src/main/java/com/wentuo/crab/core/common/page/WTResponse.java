package com.wentuo.crab.core.common.page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.wentuo.crab.core.common.exception.BizExceptionEnum;
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
 * 分页结果的封装(for Layui Table)
 *
 * @author fengshuonan
 * @Date 2019年1月25日22:07:36
 */
public class WTResponse<T> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(WTResponse.class);

    private static final long serialVersionUID = 2757435524710287976L;

    public static final Integer SUCCESS = 200;
    public static final Integer FAIL = 500;
    public static final Integer NULLERROR = 400;
    protected static WTResponse SUCCESS_TIP = new WTResponse("请求成功");


    private Integer code = 200;
    private String message = "操作成功";
    private T data;
    private Integer count = 0;
    // 扩展信息
    private Object ext;

    public Integer getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = Math.toIntExact(count);
    }

    public WTResponse() {
    }

    public boolean isSuccess() {
        return this.getCode().equals(SUCCESS);
    }

    public WTResponse(Integer code, Integer count, T data) {
        this.code = code;
        this.data = data;
        this.count = count;
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

    public WTResponse(Integer code, String message, T data, Object ext) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.ext = ext;
    }

    public WTResponse(Integer code, String message, T data, Object ext, Integer count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
        this.ext = ext;
    }

    public WTResponse(T data) {
        this.code = SUCCESS;
        this.data = data;
        this.message = "成功";
    }

    public static WTResponse success() {
        return new WTResponse();
    }

    public static WTResponse success(Object data) {
        return new WTResponse(data);
    }

    public static WTResponse success(String message, Object data) {
        return new WTResponse(WTResponse.SUCCESS, message, data);
    }

    public static WTResponse success(String message, Object data, Integer count) {
        return new WTResponse(WTResponse.SUCCESS, message, data, null, count);
    }

    public static WTResponse error(String message) {
        return new WTResponse(BizExceptionEnum.ERROR.getCode(), message);
    }

    public static WTResponse error(String message, Object data) {
        return new WTResponse(BizExceptionEnum.ERROR.getCode(), message, data);
    }


    public static WTResponse nullError(String message) {
        return new WTResponse(BizExceptionEnum.PARAM_ERROR.getCode(), message);
    }

    public static WTResponse nullError(String message, Object data) {
        return new WTResponse(BizExceptionEnum.PARAM_ERROR.getCode(), message, data);
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

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getExt() {
        return ext;
    }

    public void setExt(Object ext) {
        this.ext = ext;
    }

}
