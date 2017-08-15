package com.coveo.backendtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CitySuggestionObjTest {

    GeoDataRecordObj cMontreal,cMontgomery;

    @Before
    public void setUp() throws Exception {
        cMontreal = new GeoDataRecordObj();
        cMontreal.setName("Montréal");
        cMontreal.setAsciiname("Montreal");
        ArrayList<String> alterNames = new ArrayList<>();
        alterNames.add("Μόντρεαλ");
        alterNames.add("モントリオール");
        alterNames.add("滿地可");
        cMontreal.setAlternatenames(alterNames);
        cMontreal.setCountryCode("CA");
        cMontreal.setAdmin1Code("10");

        cMontgomery = new GeoDataRecordObj();
        cMontgomery.setName("Montgomery");
        cMontgomery.setAsciiname("Montgomery");
        alterNames = new ArrayList<>();
        alterNames.add("Μοντγκόμερι");
        alterNames.add("Монтґомері");
        alterNames.add("Մոնթգոմերի");
        cMontgomery.setAlternatenames(alterNames);
        cMontgomery.setCountryCode("US");
        cMontgomery.setAdmin1Code("AL");
    }

    @After
    public void tearDown() throws Exception {
        cMontreal=null;
        cMontgomery=null;
    }

    @Test
    public void comparisionTest(){
        CitySuggestionObj small = new CitySuggestionObj("a","a","a",0.1);
        CitySuggestionObj large = new CitySuggestionObj("b","b","b",0.3);
        assertTrue(small.compareTo(large) < 0);
        assertTrue(large.compareTo(small) > 0);
        assertTrue(small.compareTo(small) == 0);
    }

    @Test
    public void countryCodeAndAdmin1CodeMapTest(){
        CitySuggestionObj montreal = new CitySuggestionObj(cMontreal,0.0);
        CitySuggestionObj montgomery = new CitySuggestionObj(cMontgomery,0.0);
        assertEquals("Montréal, QC, Canada", montreal.getName());
        assertEquals("Montgomery, AL, USA", montgomery.getName());

    }
}
