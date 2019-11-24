package com.example.timetablerapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 24/11/19
 *
 * @author bernard
 */
public class SerializeName {
    /**
     * serialize the string, ie extract first name, middle name and last name
     * from the string.
     *
     * @param fullName string with full name of user
     * @return a list containing the first name middle name and last name
     */
    public static List<String> serializeName(String fullName) {
        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("^(\\w+\\s)+$");
        Matcher matcher = pattern.matcher(fullName);

        while (matcher.find()) {
            names.add(fullName.substring(matcher.start(), matcher.end() - 1));
        }
        return names;
    }
}
