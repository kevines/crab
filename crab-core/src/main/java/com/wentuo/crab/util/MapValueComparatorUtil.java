package com.wentuo.crab.util;

import java.util.*;

/**
 * 通过value的map排序
 *
 * @author lv
 * @date 2019/4/22 9:14
 */
public class MapValueComparatorUtil implements Comparator<Map.Entry<String, String>> {

    /**
     * 使用 Map按value进行排序
     *
     * @param oriMap
     * @return
     */
    public static Map<String, String> sortMapByValue(Map<String, String> oriMap) {
        if (oriMap == null || oriMap.isEmpty()) {
            return null;
        }
        Map<String, String> sortedMap = new LinkedHashMap<>();
        List<Map.Entry<String, String>> entryList = new ArrayList<>(
                oriMap.entrySet());
        Collections.sort(entryList, new MapValueComparatorUtil());

        Iterator<Map.Entry<String, String>> iter = entryList.iterator();
        Map.Entry<String, String> tmpEntry = null;
        while (iter.hasNext()) {
            tmpEntry = iter.next();
            sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
        }
        return sortedMap;
    }

    @Override
    public int compare(Map.Entry<String, String> me1, Map.Entry<String, String> me2) {
        return me1.getValue().compareTo(me2.getValue());
    }

}
