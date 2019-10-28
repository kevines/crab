package com.wentuo.crab.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: 用户角色枚举
 * @author wangbencheng
 * @since 2019/8/16 17:29
 */
public enum RoleEnum {

    BUSINESS("商家", "Business");


    private String name;
    private String code;

    RoleEnum(String name, String code) {
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

    // 查询所有key,value
    public static List<Map<String, String>> queryAll() {
        List<Map<String, String>> list = new ArrayList<>();
        for (RoleEnum t : RoleEnum.values()) {
            Map<String, String> tempMap = new HashMap<>();
            tempMap.put("roleName", t.getName());
            tempMap.put("roleCode", t.getCode());
            list.add(tempMap);
        }
        return list;
    }

    public static List<String> getName(List<String> codes) {
        List<String> strings = new ArrayList<>();
        for (RoleEnum orderStatus : RoleEnum.values()) {
            codes.forEach(code -> {
                if (orderStatus.getCode().equals(code)) {
                    strings.add(orderStatus.getName());
                }
            });
        }
        return strings;
    }

}
