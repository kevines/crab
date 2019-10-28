package com.wentuo.crab.util;

import java.util.Random;

/**
 * @ClassName CodeUtil
 * @Description 各种号码生成工具
 * @Author Wangbencheng
 * @Date 2019/7/5 19:21
 * @Version 1.0
 **/
public class CodeUtil {

    /**
     * 自定义进制(0,1没有加入,容易与o,l混淆)
     */
    private static final char[] r = new char[]{'q', 'w', 'e', '8', 'a', 's', '2', 'd', 'z',
            'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm', 'j', 'u', 'f', 'r', '4', 'v', 'y',
            'l', 't', 'n', '6', 'b', 'g', 'h'};
    private static final char b = 'o';   //(不能与自定义进制有重复)
    private static final int binLen = r.length;   //进制长度

    /**
     * 更加id 生产6为随机码
     * @param id 用户id
     * @param s 表示生成随机码的位数
     * @return 随机码
     */
    public static String toSerialCode(long id, int s) {
        char[] buf = new char[32];
        int charPos = 32;
        while ((id / binLen) > 0) {
            int intid = (int) (id % binLen);
            buf[--charPos] = r[intid];
            id /= binLen;
        }
        String str = new String(buf, charPos, (32 - charPos));
        //不够长度的自动随机补全
        if (str.length() < s) {
            StringBuilder sb = new StringBuilder();
            sb.append(b);
            Random random = new Random();
            for (int i = 1; i < s - str.length(); i++) {
                sb.append(r[random.nextInt(binLen)]);
            }
            str += sb.toString();
        }
        return str;
    }


}
