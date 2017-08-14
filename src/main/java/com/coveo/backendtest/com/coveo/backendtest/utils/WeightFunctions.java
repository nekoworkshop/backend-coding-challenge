package com.coveo.backendtest.com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataRecordObj;
import com.coveo.backendtest.SearchParam;

/**
 * To begin with, the score of a candidate is calculated by weighting its distance to the user and its population.
 *
 * Weight factor for distance: We consider any city within 500 kms as "highly relative", this is roughly the distance
 * between Montreal and Toronto/Newyork. Cities within this range will be givin a flat score bonus of 1.
 * Cities ranged from 500 kms to 800 kms is considered "relative". The bonus will decrease linearly until 0 at 800kms.
 *
 * Weight factor for population: We consider cities with a population larger than 400 000 as a major city. They will be
 * given a flat bonus of 1.
 * The score bonus will decrease linearly until 0 at 0 population.
 *
 *
 * Another weighting scheme: For situations that distance doesn't matter (e.g. Large business spanning the continent or world,
 * long range travel, etc.), we can take out the distance weight and only consider the population weight.
 *
 */
public class WeightFunctions {

    public static double distanceWeight (SearchParam sParam, GeoDataRecordObj city){
        double dist = Haversine.distance(sParam.getUserLat(),sParam.getUserLong(),
                                            city.getLatitude(),city.getLongitude());
        if (dist < 500.0)
            return 1;
        if(dist <800.0)
            return (800.0-dist)/300.0;
        return 0;
    }

    public static double populationWeight (GeoDataRecordObj city){
        if(city.getPopulation() > 400000.0)
            return 1;
        return city.getPopulation()/400000.0;
    }
}
