
package com.wentuo.crab.appm.config.web;

import com.alibaba.fastjson.JSONObject;
import com.wentuo.crab.util.HttpContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class ParamLogger {

    @Pointcut("execution(* com.wentuo.crab.appm.api..*(..))")
    public void controller() {
    }


    @Before("controller()")
    public void controller(JoinPoint point) {

    }

    @Around("controller()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String uuid = HttpContext.getUserId();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        log.info("\n\t请求用户: {}\n\t请求IP: {}\n\t请求路径: {}\n\t方法描述: {}\n\t请求参数: {}",
                uuid, HttpContext.getIp(), request.getRequestURL(),
                getMethodInfo(point), JSONObject.toJSONString(request.getParameterMap()));

        long startTime = System.currentTimeMillis();
        Object[] args = point.getArgs();
        Object retVal = point.proceed(args);
        long endTime = System.currentTimeMillis();

        log.info("\n\t执行时间: {} ms \n\t返回值: {}", endTime - startTime, JSONObject.toJSONString(retVal));
        return retVal;
    }

    private String getMethodInfo(JoinPoint point) {
        ConcurrentHashMap<String, Object> info = new ConcurrentHashMap<>(3);

        info.put("class", point.getTarget().getClass().getSimpleName());

        String[] parameterNames = ((MethodSignature) point.getSignature()).getParameterNames();
        ConcurrentHashMap<String, String> args = null;

        if (Objects.nonNull(parameterNames)) {
            args = new ConcurrentHashMap<>(parameterNames.length);
            for (int i = 0; i < parameterNames.length; i++) {
                String value = point.getArgs()[i] != null ? point.getArgs()[i].toString() : "null";
                args.put(parameterNames[i], value);
            }
            info.put("args", args);
        }

        return JSONObject.toJSONString(info);
    }

}
