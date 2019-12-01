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
public class PerformWordChecks {
    /**
     * serialize the string, ie extract first name, middle name and last name
     * from the string.
     *
     * @param fullName string with full name of user
     * @return a list containing the first name middle name and last name
     */
    public static List<String> serializeName(String fullName) {
        if (fullName == null) throw new NullPointerException();
        if (fullName.isEmpty()) throw new IllegalArgumentException();

        List<String> names = new ArrayList<>();
        Pattern pattern = Pattern.compile("([a-zA-Z]+){2,4}");
        Matcher matcher = pattern.matcher(fullName);

        // check that full name is at least 2 words
        if (Pattern.compile("([a-zA-Z]+){0,2}").matcher(fullName).matches()) return names;

        // check that full name does not include numbers
        if (Pattern.compile("([0-9]+)+").matcher(fullName).find()) return names;

        while (matcher.find()) {
            names.add(matcher.group());
        }
        return names;
    }

    public static boolean checkEmailValidity(String email) {
        if (email == null) throw new NullPointerException();
        else return email.matches("^.*@(\\w+(\\.\\w+)+)");
    }
}
