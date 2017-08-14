package com.coveo.backendtest.com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataRecordObj;
import com.coveo.backendtest.SearchParam;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class WeightFunctionsTest {

    private SearchParam sParam;
    private GeoDataRecordObj city;
    @Before
    public void setUp() throws Exception {
       sParam = new SearchParam();
       city = new GeoDataRecordObj();
    }

    @After
    public void tearDown() throws Exception {
        sParam = null;
        city = null;
    }

    @Test
    public void distanceWeight_fullBonusTest() throws Exception {
       //Montreal
       sParam.setUserLat(45.5017);
       sParam.setUserLong(73.5673);
       //Laval
       city.setLatitude(45.6066);
       city.setLongitude(73.7124);
       assertEquals(1.0,WeightFunctions.distanceWeight(sParam,city),0.01);
    }

    @Test
    public void distanceWeight_linearBonusTest() throws Exception {
        //Montreal
        sParam.setUserLat(45.5017);
        sParam.setUserLong(73.5673);
        //Gaspe
        city.setLatitude(48.8316);
        city.setLongitude(64.4869);
        assertEquals(0.076666,WeightFunctions.distanceWeight(sParam,city),0.01);
    }

    @Test
    public void distanceWeight_zeroBonusTest() throws Exception {
        //Montreal
        sParam.setUserLat(45.5017);
        sParam.setUserLong(73.5673);
        //Dalian,China
        city.setLatitude(38.9140);
        city.setLongitude(-121.6147);
        assertEquals(0.0,WeightFunctions.distanceWeight(sParam,city),0.01);
    }

    @Test
    public void populationWeight_fullBonusTest() throws Exception {
        //Montreal
        city.setPopulation(4100000);
        assertEquals(1.0,WeightFunctions.populationWeight(city),0.01);
    }

    @Test
    public void populationWeight_linearBonusTest() throws Exception {
        //Sherbrooke
        city.setPopulation(162638);
        assertEquals(0.406,WeightFunctions.populationWeight(city),0.01);
    }
}