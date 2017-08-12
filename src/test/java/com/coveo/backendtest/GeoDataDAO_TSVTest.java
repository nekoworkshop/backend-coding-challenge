package com.coveo.backendtest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GeoDataDAO_TSVTest {

    private GeoDataDAO_TSV dao;
    @Before
    public void setUp() throws Exception {
        dao = GeoDataDAO_TSV.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    @Test
    public void getAllGeoData() throws Exception {
        dao.setInputStreamReader(new InputStreamReader(this.getClass().getResourceAsStream("/testGeoData.tsv"), "UTF-8"));
        ArrayList<GeoDataRecordObj> d = dao.getAllGeoData();
        assertEquals("Abbotsford",d.get(0).getName());
        assertEquals("Abbotsford",d.get(0).getAsciiname());
        assertEquals("Абботсфорд",d.get(0).getAlternatenames().get(2));
        assertEquals(49.0758,d.get(0).getLatitude(),0.1);
        assertEquals(122.25257,-d.get(0).getLongitude(),0.1);
        assertEquals(151683,d.get(0).getPopulation());

        dao.setInputStreamReader(new InputStreamReader(this.getClass().getResourceAsStream("/testGeoData_modified.tsv")));
        dao.reloadData();

        d = dao.getAllGeoData();
        assertEquals("Airdrie",d.get(0).getName());
        assertEquals("Airdrie",d.get(0).getAsciiname());
        assertEquals("إيردراي",d.get(0).getAlternatenames().get(6));
        assertEquals(51.30011,d.get(0).getLatitude(),0.1);
        assertEquals(-114.03528,d.get(0).getLongitude(),0.1);
        assertEquals(24673,d.get(0).getPopulation());
    }

}