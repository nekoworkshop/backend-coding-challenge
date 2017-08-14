package com.coveo.backendtest.utils;

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
        List<StringMatchResultObj> result = t.searchCity("Montreal");

        /**
         * Assert that:
         * 1. We only have one result.
         * 2. The only result is an exact match.
         * 3. The matched city is Montreal.
         */
        assertEquals(1,result.size());
        assertEquals(MatchTypes.EXACT_MATCH,result.get(0).getMatchType());
        assertEquals(cMontreal,result.get(0).getCityRecord());
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
        List<StringMatchResultObj> result = t.searchCity(cityName);

        /**
         * Assert that:
         * The exact match returned has the non-ascii name as it's alternative name.
         */
        assertTrue( result.get(0).getCityRecord().getAlternatenames().contains(cityName));
    }

    @Test
    public void superStringSearchTest() throws Exception{
        Trie t = new Trie();
        t.insert(cMontreal);
        t.insert(cMontgomery);
        t.insert(cQuebec);

        List<StringMatchResultObj> result = t.searchCity("Mont");

        /**
         * Assert that:
         * 1. The super string match should return two result (Montreal and Montgomery).
         * 2. The strict string match should return zero result.
         * 3. Montreal and Montgomery exist in the super string match reuslt.
         */
        assertEquals(2,result.size());
        assertTrue((cMontreal==result.get(0).getCityRecord())|| (cMontreal == result.get(1).getCityRecord()));
        assertTrue((cMontgomery==result.get(0).getCityRecord())|| (cMontgomery == result.get(1).getCityRecord()));
    }

    @Test
    public void DuplicateInsertTest() throws Exception{
        Trie t = new Trie();
        t.insert(cMontreal);
        t.insert(cMontreal);
        t.insert(cMontreal);
        t.insert(cMontreal);

        List<StringMatchResultObj> result = t.searchCity("Montreal");

        /**
         * Assert that:
         * 1. We only have one result.
         * 2. The only result is an exact match.
         * 3. The matched city is Montreal.
         */
        assertEquals(1,result.size());
        assertEquals(MatchTypes.EXACT_MATCH,result.get(0).getMatchType());
        assertEquals(cMontreal,result.get(0).getCityRecord());
    }

    @Test
    public void noMatchTest() throws Exception{
        Trie t = new Trie();
        t.insert(cMontreal);
        t.insert(cMontgomery);
        t.insert(cQuebec);


        List<StringMatchResultObj> result = t.searchCity("CityOutOfNoWhere");

        /**
         * Assert that:
         * The return size should be zero.
         */
        assertEquals(0,result.size());
    }
}