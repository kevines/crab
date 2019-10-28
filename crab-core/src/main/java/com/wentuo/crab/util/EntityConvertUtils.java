package com.wentuo.crab.util;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 实体类转化
 *
 * @author lv
 * @date 2019/3/19 13:22
 */
public class EntityConvertUtils {


    //属性值过滤
    private final static ValueFilter valueFilter = new ValueFilter() {
        @Override
        public Object process(Object source, String name, Object value) {
            return value;
        }
    };

    /**
     * 将实体转换为A to B
     *
     * @param A
     * @param clazz
     * @return
     */
    public static <T> T convertAToB(Object A, Class<T> clazz) {
        if (A == null || clazz == null) {
            return null;
        }
        String json = JSON.toJSONString(A, valueFilter);
        return JSON.parseObject(json, clazz);
    }


    /**
     * 将AList->BList
     *
     * @param AList
     * @param clazz
     * @return
     */
    public static <T> List<T> convertAListToBList(List<?> AList, Class<T> clazz) {
        if (AList == null || AList.size() <= 0) {
            return new ArrayList<T>();
        }
        List<T> list = new ArrayList<T>();
        for (Object A : AList) {
            list.add(convertAToB(A, clazz));
        }
        return list;
    }

    /**
     * 将DTO转换为实体集合
     *
     * @return
     */
    public static <T> List<Map<String, Object>> beanToMap(List<T> objects) {
        List<Map<String, Object>> maps = new ArrayList<>();
        objects.forEach(o -> {
            Map<String, Object> map = BeanUtil.beanToMap(o);
            maps.add(map);
        });
        return maps;
    }

    /**
     * 将DTO转换为实体集合
     *
     * @return
     */
    public static <T> Page<Map<String, Object>> beanToMap(Page<T> objects) {
        Page<Map<String, Object>> maps = new Page<>();
        List<Map<String, Object>> list = new ArrayList<>();
        objects.getRecords().forEach(o -> {
            Map<String, Object> map = BeanUtil.beanToMap(o);
            list.add(map);
        });
        maps.setRecords(list);
        return maps;
    }

    public static <T> T setNullValue(T source) {
        try {
            Field[] fields = source.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getGenericType().toString().equals(
                        "class java.lang.String")) {
                    field.setAccessible(true);
                    Object obj = field.get(source);
                    if (obj != null && obj.equals("")) {

                        field.set(source, null);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return source;
    }

    /**
     * List -> String
     *
     * @param list
     * @return
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (String string : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * mapToList
     *
     * @param map  要传入得map      Map<String, String>
     * @param key1 map中得第一个
     * @param key2 map中得第二个
     * @return
     */
    public static List mapToList(Map<String, String> map, String key1, String key2) {
        List list = new ArrayList();
        for (String key : map.keySet()) {
            Map<String, String> map1 = new HashMap<>(2);
            map1.put(key1, key);
            map1.put(key2, map.get(key).toString());
            list.add(map1);
        }
        return list;
    }

    /**
     * mapToList
     *
     * @param map  要传入得map      Map<String, String>
     * @param key1 map中得第一个
     * @param key2 map中得第二个
     * @return
     */
    public static List stringMapToList(Map<String, String> map, String key1, String key2) {
        List list = new ArrayList();
        for (String key : map.keySet()) {
            Map<String, String> map1 = new HashMap<>(2);
            map1.put(key1, key);
            map1.put(key2, map.get(key));
            list.add(map1);
        }
        return list;
    }

    /**
     * 实体对象转成Map
     *
     * @param obj 实体对象
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 复制map对象
     *
     * @param paramsMap 被拷贝对象
     * @param resultMap 拷贝后的对象
     * @explain 将paramsMap中的键值对全部拷贝到resultMap中；
     * paramsMap中的内容不会影响到resultMap（深拷贝）
     */
    public static void mapCopy(Map paramsMap, Map resultMap) {
        if (resultMap == null) {
            resultMap = new HashMap();
        }
        if (paramsMap == null) {
            return;
        }
        Iterator it = paramsMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            resultMap.put(key, paramsMap.get(key) != null ? paramsMap.get(key) : "");

        }
    }

}
