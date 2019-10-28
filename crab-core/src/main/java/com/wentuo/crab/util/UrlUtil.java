package com.wentuo.crab.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author lv
 * @date 2019/4/16 9:56
 */
public class UrlUtil {

    public static String encode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, "utf-8");
    }

    public static String decode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, "utf-8");
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        System.out.println(decode("https://qa.51ganjie.cn//wxAuth.htm"));
//    }
}
