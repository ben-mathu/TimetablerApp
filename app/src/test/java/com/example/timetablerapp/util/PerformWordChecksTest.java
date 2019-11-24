package com.example.timetablerapp.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

import java.util.ArrayList;
import java.util.List;

/**
 * 24/11/19
 *
 * @author bernard
 */
public class PerformWordChecksTest {

    @Test
    public void serializeName_WhenStringIsNull_ThrowException() {
        Assert.assertThrows(NullPointerException.class, () -> PerformWordChecks.serializeName(null));
    }

    @Test
    public void serializeName_WhenStringIsEmpty_ThrowException() {
        Assert.assertThrows(IllegalArgumentException.class, () -> PerformWordChecks.serializeName(""));
    }

    @Test
    public void serializeName_WhenFullNameIsCorrect_ReturnsListOfNames() {
        // Expected List
        List<String> list = new ArrayList<>();
        list.add("Benard");list.add("Kamau");list.add("Mathu");

        // Actual list
        List<String> strList = PerformWordChecks.serializeName("Benard Kamau Mathu");

        Assert.assertArrayEquals(list.toArray(), strList.toArray());
    }

    @Test
    public void serializeName_WhenFullNameHasNumbers_ReturnEmptyList() {
        // Expected List
        List<String> list = new ArrayList<>();

        // Actual list
        List<String> strList = PerformWordChecks.serializeName("Benard Kamau Mathu 90");

        Assert.assertArrayEquals(list.toArray(), strList.toArray());
    }

    @Test
    public void serializeName_WhenFullNameIsLessThan2Words_ReturnEmptyList() {
        // Expected List
        List<String> list = new ArrayList<>();

        // Actual list
        List<String> strList = PerformWordChecks.serializeName("Benard");

        Assert.assertArrayEquals(list.toArray(), strList.toArray());
    }

    @Test
    public void checkEmailValidity_EmailIsNull_ThrowNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> PerformWordChecks.checkEmailValidity(null));
    }

    @Test
    public void checkEmailValidity_EmailIsEmpty_ReturnFalse() {
        Assert.assertFalse(PerformWordChecks.checkEmailValidity(""));
    }

    @Test
    public void checkEmailValidity_EmailHasNoAtSign_ReturnFalse() {
        Assert.assertFalse(PerformWordChecks.checkEmailValidity("john_doegmail.com"));
    }

    @Test
    public void checkEmailValidity_EmailHasNoDomainName_ReturnFalse() {
        Assert.assertFalse(PerformWordChecks.checkEmailValidity("john_doe@"));
    }

    @Test
    public void checkEmailValidity_EmailHasNoDomainName_OrAtSign_ReturnFalse() {
        Assert.assertFalse(PerformWordChecks.checkEmailValidity("john_doe"));
    }

    @Test
    public void checkEmailValidity_EmailIsValid_ReturnTrue() {
        Assert.assertTrue(PerformWordChecks.checkEmailValidity("john_doe@example.co.ke"));
    }
}