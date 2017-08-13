package com.coveo.backendtest.com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataRecordObj;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrieTest {

    GeoDataRecordObj cMontreal,cMontgomery,cQuebec;

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

        cMontgomery = new GeoDataRecordObj();
        cMontgomery.setName("Montgomery");
        cMontgomery.setAsciiname("Montgomery");
        alterNames = new ArrayList<>();
        alterNames.add("Μοντγκόμερι");
        alterNames.add("Монтґомері");
        alterNames.add("Մոնթգոմերի");
        cMontgomery.setAlternatenames(alterNames);

        cQuebec = new GeoDataRecordObj();
        cQuebec.setName("Québec");
        cQuebec.setAsciiname("Quebec");
        alterNames = new ArrayList<>();
        alterNames.add("Ville de Québec");
        alterNames.add("魁北克城");
        alterNames.add("ケベック・シティー");
        cQuebec.setAlternatenames(alterNames);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void singleMatchSearchTest() throws Exception{
        Trie t = new Trie();
        t.insert(cMontreal);
        List<List<GeoDataRecordObj>> result = t.searchCity("Montréal");

        /**
         * Assert that:
         * 1. The super string match should be return zero result.
         * 2. The strict string match should return one result.
         * 3. The sole result we get is montreal.
         */
        assertEquals(0,result.get(1).size());
        assertEquals(1,result.get(0).size());
        assertTrue(result.get(0).contains(cMontreal));
    }

    @Test
    public void unicodeExactMatchSearchTest() throws Exception{
       Trie t = new Trie();
       t.insert(cMontreal);
       t.insert(cMontgomery);
       t.insert(cQuebec);

       unicodeExactMatchSearch(t,"滿地可");
       unicodeExactMatchSearch(t,"Монтґомері");
       unicodeExactMatchSearch(t,"ケベック・シティー");
    }

    private void unicodeExactMatchSearch(Trie t, String cityName) throws Exception{
        List<List<GeoDataRecordObj>> result = t.searchCity(cityName);

        /**
         * Assert that:
         * The exact match returned has the non-ascii name as it's alternative name.
         */
        assertTrue( result.get(0).get(0).getAlternatenames().contains(cityName));
    }

    @Test
    public void superStringSearchTest() throws Exception{
        Trie t = new Trie();
        t.insert(cMontreal);
        t.insert(cMontgomery);
        t.insert(cQuebec);

        List<List<GeoDataRecordObj>> result = t.searchCity("mon");

        /**
         * Assert that:
         * 1. The super string match should return two result (Montreal and Montgomery).
         * 2. The strict string match should return zero result.
         * 3. Montreal and Montgomery exist in the super string match reuslt.
         */
        assertEquals(2,result.get(1).size());
        assertEquals(0,result.get(0).size());
        assertTrue(result.get(1).contains(cMontreal));
        assertTrue(result.get(1).contains(cMontgomery));
    }

    @Test
    public void DuplicateInsertTest() throws Exception{
        Trie t = new Trie();
        t.insert(cMontreal);
        t.insert(cMontreal);
        t.insert(cMontreal);
        t.insert(cMontreal);

        List<List<GeoDataRecordObj>> result = t.searchCity("montreal");

        /**
         * Assert that:
         * 1. The super string match should be return zero result.
         * 2. The strict string match should return one result.
         * 3. The sole result we get is montreal.
         */
        assertEquals(0,result.get(1).size());
        assertEquals(1,result.get(0).size());
        assertTrue(result.get(0).contains(cMontreal));
    }

}