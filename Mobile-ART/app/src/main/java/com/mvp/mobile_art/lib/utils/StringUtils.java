package com.mvp.mobile_art.lib.utils;

/**
 * Created by michael on 4/25/17.
 */

public class StringUtils {

    private static final String TAG = "StringUtils";

    public static boolean isNullOrEmpty(String str){
        return (str == null || str.isEmpty());
    }
}
