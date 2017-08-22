package com.coveo.backendtest.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class HaversineTest {
    @Test
    public void distance() throws Exception {
        assertEquals(2886,Haversine.distance(36.12,-86.67,33.94,-118.40),10);
    }

    @Test
    public void samePointDistance(){
        assertEquals(0,Haversine.distance(43.70011,-79.4163,43.70011,-79.4163),1);
    }

}