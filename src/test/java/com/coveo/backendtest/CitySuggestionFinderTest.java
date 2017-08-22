package com.coveo.backendtest;

import com.coveo.backendtest.utils.LanguageTag;
import com.coveo.backendtest.utils.StringSearchAggregator;
import com.coveo.backendtest.utils.Trie;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;

import static org.junit.Assert.*;


//TODO: Update to reflect recent change of handling different languages.
public class CitySuggestionFinderTest {

    Trie t;
    StringSearchAggregator searchAggregator;
    CitySuggestionFinder finder;

    @Before
    public void setUp() throws Exception {
        t = new Trie(new GeoDataDAO_TSV(new InputStreamReader(this.getClass().getResourceAsStream("/cities_canada-usa.tsv"))));
        t.setLanguageTag(LanguageTag.FREandENG);
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

    @Test
    public void SingleMatchWithLocationLookupTest() throws Exception {
        SearchParam sParam = new SearchParam();
        CitySuggestionCollection result;

        sParam.setSearchString("toronto");
        sParam.setUserLat(43.70011);
        sParam.setUserLong(-79.4163);
        result = finder.lookup(sParam);
        assertEquals(result.generateJSON(),"{\"suggestions\":[{\"name\":\"Toronto, ON, Canada\",\"latitude\":\"43.70011\",\"longitude\":\"-79.4163\",\"score\":1.0},{\"name\":\"Toronto, OH, USA\",\"latitude\":\"40.46423\",\"longitude\":\"-80.60091\",\"score\":0.4850159361020352}]}");
    }
}