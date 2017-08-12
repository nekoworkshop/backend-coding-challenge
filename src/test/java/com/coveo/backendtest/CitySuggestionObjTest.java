package com.coveo.backendtest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class CitySuggestionObjTest {
    @Test
    public void comparisionTest(){
        CitySuggestionObj small = new CitySuggestionObj("a","a","a",0.1);
        CitySuggestionObj large = new CitySuggestionObj("b","b","b",0.3);
        assertTrue(small.compareTo(large) < 0);
        assertTrue(large.compareTo(small) > 0);
        assertTrue(small.compareTo(small) == 0);
    }
}
