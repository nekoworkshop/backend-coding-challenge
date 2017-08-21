package com.coveo.backendtest;

import com.coveo.backendtest.utils.StringSearchAggregator;
import com.coveo.backendtest.utils.Trie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class CitySuggestionFinderTest {

    Trie t;
    StringSearchAggregator searchAggregator;
    CitySuggestionFinder finder;

    @Before
    public void setUp() throws Exception {
        t = new Trie(new GeoDataDAO_TSV(new InputStreamReader(this.getClass().getResourceAsStream("/cities_canada-usa.tsv"))));
        searchAggregator = new StringSearchAggregator();
        searchAggregator.addStringMatchAlgorithm(t);
        finder = new CitySuggestionFinder(searchAggregator);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void SingleMatchLookupTest() throws Exception {
        SearchParam sParam = new SearchParam();
        CitySuggestionCollection result;

        sParam.setSearchString("モントリオール");

        result = finder.lookup(sParam);
       assertEquals(result.generateJSON(),"{\"suggestions\":[{\"name\":\"Montréal, QC, Canada\",\"latitude\":\"45.50884\",\"longitude\":\"-73.58781\",\"score\":1.0}]}");
    }

    @Test
    public void noMatchLookupTest() throws Exception{
        SearchParam sParam = new SearchParam();
        CitySuggestionCollection result;

        sParam.setSearchString("City out of no where");

        result = finder.lookup(sParam);
        assertEquals(result.generateJSON(),"{\"suggestions\":[]}");
    }

}