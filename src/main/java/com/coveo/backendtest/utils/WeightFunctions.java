package com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataRecordObj;
import com.coveo.backendtest.SearchParam;

/**
 * This class provides a static method for calculating a city's score.
 *
 * The distance and population weighting can be turned off or on based on user's preference, as indicated in the SearchParam class.
 *
 * @see SearchParam
 *
 */
public class WeightFunctions {

    //TODO: Expose weighting parameters (maximal distance and population for flat bonus, etc)as variable.

    /**
     * Public interface for getting a score for a city based on how it was matched during the search and the user's search
     * preference.
     *
     * @param c
     * @param s
     * @return
     */
    public static double calculateAllWeight (StringMatchResultObj c, SearchParam s){
        double weight = 0.0;
        if(s.isUseDistanceBonus())
            weight+= distanceWeight(s,c.getCityRecord());
        if(s.isUsePopulationBonus())
            weight+= populationWeight(c.getCityRecord());
        weight+= matchTypeWeight(c,s);
        return weight;
    }

    //TODO: change visibility of the methods for calculating individual weights to private, and rewrite tests.

    /**
     * Weight factor for distance: We consider any city within 300 kms as "highly relative", this is roughly the distance
     * between Montreal and Quebec City. Cities within this range will be givin a flat score bonus of 1.
     * Cities ranged from 300 kms to 800 kms is considered "relative". The bonus will decrease linearly until 0 at 800kms.
     *
     * @param sParam
     * @param city
     * @return
     */
    public static double distanceWeight (SearchParam sParam, GeoDataRecordObj city){

        double dist = Haversine.distance(sParam.getUserLat(),sParam.getUserLong(),
                                            city.getLatitude(),city.getLongitude());
        if (dist < 300.0)
            return 1;
        if(dist <800.0)
            return (800.0-dist)/500.0;
        return 0;
    }

    /**
     * Weight factor for population: We consider cities with a population larger than 400 000 as a major city. They will be
     * given a flat bonus of 1.
     * The score bonus will decrease linearly until 0 at 0 population.
     *
     * @param city
     * @return
     */
    public static double populationWeight (GeoDataRecordObj city){
        if(city.getPopulation() > 400000.0)
            return 1;
        return city.getPopulation()/400000.0;
    }

    /**
     *
     * Weight factor for match type: An exact match will receive a small bonus. We can differentiate the result further by
     * assigning different bonus to different type of matches like prefix match and fuzzy match.
     *
     * @param c
     * @param s
     * @return
     */
    private static double matchTypeWeight (StringMatchResultObj c, SearchParam s){
        if(c.getMatchType() == MatchTypes.EXACT_MATCH)
            return 0.2;
        return 0.0;
    }
}
