package com.wentuo.crab.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * Created by lv on 2019/3/20 17:50.
 */
public enum ActionEnum {
    INSERT("增加",1),
    DELETE("删除",2),
    SELECT("查询",3),
    UPDATE("修改",4),
    EXCEPTION("异常",5);



    private String name;
    @EnumValue
    private Integer code;

    ActionEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 根据key获取value
     *
     * @param key : 键值key
     * @return Integer
     */
    public static Integer getValueByKey(String key) {
        ActionEnum[] enums = ActionEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(key)) {
                return enums[i].getCode();
            }
        }
        return 0;
    }


    public static String getKeyByValue(Integer value) {
        ActionEnum[] enums = ActionEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].getCode().equals(value)) {
                return enums[i].getName();
            }
        }
        return "";
    }

    public static ActionEnum getKey(String key){
        ActionEnum[] enums = ActionEnum.values();
        for (int i = 0; i < enums.length; i++) {
            if (enums[i].toString().equals(key)) {
                return enums[i];
            }
        }
        return null;
    }
}
