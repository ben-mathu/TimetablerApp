package com.example.timetablerapp.util;

import java.util.Locale;

/**
 * 11/11/19 -bernard
 */
public class CompareStrings {
    public static boolean compare(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.isEmpty() || s2.isEmpty()) return false;

        s1 = s1.toLowerCase(Locale.getDefault());
        s2 = s2.toLowerCase(Locale.getDefault());

        return s1.contains(s2);
    }
}
