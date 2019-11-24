package com.example.timetablerapp.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * 24/11/19
 *
 * @author bernard
 */
public class CompareStringsTest {

    @Test
    public void compare_WordContainLetter_True() {
        assertTrue(CompareStrings.compare("BenardMathu", "m"));
    }

    @Test
    public void compare_WordDoesNotContainLetter_False() {
        assertFalse(CompareStrings.compare("mathu", "B"));
    }

    @Test
    public void compare_OneStringIsNull_False() {
        assertFalse(CompareStrings.compare(null, "benardmathu"));
        assertFalse(CompareStrings.compare(null, null));
        assertFalse(CompareStrings.compare("benardMathu", null));
    }

    @Test
    public void compare_EmptyStrings_True() {
        assertFalse(CompareStrings.compare("", "n"));
        assertFalse(CompareStrings.compare("", ""));
        assertFalse(CompareStrings.compare("benardMathu", ""));
    }
}