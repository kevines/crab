package com.wentuo.crab.enums;

/**
 * 微信登陆类型枚举
 * @author wangbencheng
 * @since 2019/10/26
 */
public enum WechatLoginTypeEnum {
    MINI("mini", 1),

    APP("app", 2),

    H5("h5", 3);

    private String type;
    private Integer value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    WechatLoginTypeEnum(String type, Integer value){
        this.type = type;
        this.value = value;
    }

    /**
     *
     * @param value 1
     * @return
     */
    public static String getTypeByValue(Integer value) {
        WechatLoginTypeEnum[] enums = WechatLoginTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getValue().equals(value)) {
                return enums[i].getType();
            }
        }
        return "";
    }

    /**
     *
     * @param type mini
     * @return
     */
    public static Integer getValueByType(String type) {
        WechatLoginTypeEnum[] enums = WechatLoginTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getType().equals(type)) {
                return enums[i].getValue();
            }
        }
        return null;
    }

}
