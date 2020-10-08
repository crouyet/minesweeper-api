package com.minesweeper.api.config;


import com.google.common.collect.Maps;

import java.util.Map;

public class RSD {

    public static final String USERNAME = "user";
    public static final String SESSION_TOKEN = "token";

    private static ThreadLocal<Map<String, String>> userInfo = ThreadLocal.withInitial(Maps::newHashMap);

    public static void save(String key, String value) {
        userInfo.get().put(key, value);
    }

    public static Map<String, String> get() {
        return userInfo.get();
    }

    public static String get(String key) {
        return userInfo.get().get(key);
    }

    public static void set(Map<String, String> value) {
        userInfo.set(value);
    }

    public static void clear() {
        userInfo.remove();
    }
}
