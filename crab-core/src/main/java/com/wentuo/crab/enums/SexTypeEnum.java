package com.wentuo.crab.enums;

/**
 * 性别枚举
 * Created by wangbencheng on 2019/10/26 19:59.
 */
public enum SexTypeEnum {

    MAN("男", "M"),

    WOMAN("女", "W"),

    UNKNOW("未知", "N");

    private String name;
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    SexTypeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    /**
     * 根据code获取name
     *
     * @param code
     *            : 键值key
     * @return String
     */
    public static String getNameByCode(String code) {
        SexTypeEnum[] enums = SexTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(code)) {
                return enums[i].getName();
            }
        }
        return "";
    }

    public static String getCodeByName(String name) {
        SexTypeEnum[] enums = SexTypeEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getName().equals(name)) {
                return enums[i].getCode();
            }
        }
        return "";
    }
}
