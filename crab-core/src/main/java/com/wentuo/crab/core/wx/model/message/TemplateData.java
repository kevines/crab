package com.wentuo.crab.core.wx.model.message;

import lombok.Data;

/**
 * 功能描述：封装的请求模版参数
 *
 * @author wangbencheng
 * @version 1.0.0
 * @claaName TemplateDate
 * @since 2019/10/6 13:07
 */
@Data
public class TemplateData {

    /**
     * 内容
     */
    private String value;

    /**
     * 颜色
     */
    private String color;

    /**
     * 构造方法 值
     * @param value
     */
    public TemplateData(String value) {
        this.value = value;
    }

    /**
     * 构造方法 值、颜色
     * @param value
     * @param color
     */
    public TemplateData(String value, String color) {
        this.value = value;
        this.color = color;
    }

    /**
     * 构造方法
     */
    public TemplateData() {

    }

}
