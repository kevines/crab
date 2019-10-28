/**
 * BSHO Engine
 */
package com.wentuo.crab.util;

import java.io.Serializable;

/**
 * 业务异常Exception
 *
 * @author masai
 * @version $Id: BizException.java, v 0.1 2016年11月16日 下午2:59:20 masai Exp $
 */
public class BizException extends RuntimeException  implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = -8471201995271839633L;

    /** 错误码 */
    private Integer            code;
    /** 错误信息 */
    private String            message;

    /**
     * 构造方法
     *
     * @param code 错误码
     * @param message 错误信息
     */
    public BizException(Integer code, String message) {
        super();
        this.code = code;
        this.message = message;
    }



    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
