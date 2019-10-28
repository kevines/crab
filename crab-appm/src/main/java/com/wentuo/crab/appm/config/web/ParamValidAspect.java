package com.wentuo.crab.appm.config.web;

import com.wentuo.crab.core.common.page.WTResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

@Slf4j
@Aspect
@Component
public class ParamValidAspect {



    @Around("execution(* com.wentuo.crab.appm.api..*(..)) && args(..,bindingResult)")
    public Object validateParam(ProceedingJoinPoint pjp, BindingResult bindingResult) throws Throwable {
        Object retVal;
        if (bindingResult.hasErrors()) {
            String errorInfo = getErrorsSplitNewLine(bindingResult);
            log.info("validateParam进行参数校验出错, 出错信息如下：{}", errorInfo);
            retVal = WTResponse.error(bindingResult.getFieldError().getDefaultMessage());
        } else {
            //执行目标方法
            retVal = pjp.proceed();
        }
        return retVal;
    }
    /*
	 * 此校验错误信息转化为字符，多个错误信息通过参数[splitChars]进行分隔
	 */
	private String getErrors(BindingResult br, String splitChars) {
		if(splitChars == null) {
			splitChars = "";
		}
		StringBuilder result = new StringBuilder();
		List<ObjectError> errors = br.getAllErrors();
		for (ObjectError vError : errors) {
			result.append(vError.getDefaultMessage());
			result.append(splitChars);
		}
		if(result.length() > 0) {
			result.delete(result.length() - splitChars.length(), result.length());
		}
		return result.toString();
	}

	/*
	 * 此校验错误信息转化为字符，多个错误信息通过<br>进行分隔
	 */
	private String getErrorsSplitNewLine(BindingResult br) {
		return getErrors(br, "<br>");
	}
}
