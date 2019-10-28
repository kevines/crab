package com.wentuo.crab.core.common.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 避免重复提交
 *
 * @author hhz
 * @since
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AvoidRepeatCommit {

    /**
     * 指定时间内不可重复提交,单位秒
     *
     * @return
     */
    long timeout() default 5;

}