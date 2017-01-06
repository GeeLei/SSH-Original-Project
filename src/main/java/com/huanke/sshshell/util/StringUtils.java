package com.huanke.sshshell.util;

public class StringUtils {

    public static String fuzzyString(String origin, int offset, int length,
            char replace) {
        if (origin == null || origin.length() < offset + length) {
            return null;
        }
        StringBuilder sb = new StringBuilder(origin);
        sb.replace(offset, offset + length, repeat(replace + "", length));
        return sb.toString();
    }

    public static String repeat(String seed, int times) {
        if (times <= 0) {
            return "";
        }
        if (seed.length() * times > 1024) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < times; i++) {
                sb.append(seed);
            }
            return sb.toString();
        }
        String re = seed;
        for (int i = 1; i < times; i++) {
            re += seed;
        }
        return re;
    }
}
