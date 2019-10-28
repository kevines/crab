package com.wentuo.crab.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * 通过key的map排序
 * @author lv
 * @date 2019/4/22 9:07
 */
public class MapKeyComparatorUtil implements Comparator<String> {

    /**
     * 使用 Map按key进行排序
     * @param map
     * @return
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<>(new MapKeyComparatorUtil());
        sortMap.putAll(map);
        return sortMap;
    }

    @Override
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}
