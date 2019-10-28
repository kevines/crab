package com.wentuo.crab.enums;

/**
 * Created by lv on 2019/3/20 17:50.
 */
public enum EnvironmentEnum {
    PREPUBLISH("正式环境", "prepublish"),

    DEV("dev环境", "dev"),

    TEST("test环境", "test"),

    LOCAL("本地环境", "local");

    private String name;
    private String code;

    EnvironmentEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

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

    /**
     * 根据key获取value
     *
     * @param key : 键值key
     * @return String
     */
    public static String getValueByKey(String key) {
        EnvironmentEnum[] enums = EnvironmentEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(key)) {
                return enums[i].getName();
            }
        }
        return "";
    }
}
