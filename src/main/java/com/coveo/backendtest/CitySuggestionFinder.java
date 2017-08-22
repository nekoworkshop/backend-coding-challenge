package com.coveo.backendtest;

/**
 *
 * This class coordinates the searching,filtering and weighting of the suggestions, based on the search parameter provided
 * by the user.
 *
 * Note the dependency of Trie is fulfilled by the caller.
 *
 * @see com.coveo.backendtest.utils.Trie, com.coveo.backendtest.SearchParam ,com.coveo.backendtest.utils.WeightFunctions
 */

import com.coveo.backendtest.utils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CitySuggestionFinder {

    private StringSearchAggregator searchAggregator;

    public CitySuggestionFinder(StringSearchAggregator t){
        this.searchAggregator = t;
    }

    public CitySuggestionCollection lookup(SearchParam sParam){

        //Prepare the data structure for storing the results.
        Set<CitySuggestionObj> results = new HashSet<>();

        //Retrieve candidates from the trie.
        assert (this.searchAggregator != null);
        Set<StringMatchResultObj> matchedCities = searchAggregator.searchCity(sParam);

        //Now we iterate through all candidates and insert the valid ones into the result set.
        double maxScore = 0.0;
        double score = 0.0;
        for(StringMatchResultObj smResult: matchedCities){

            //Check if the city needs to be filtered out.
            if (sParam.isSearchResultValid(smResult)){

                //If the city is a valid candidate, calculate its weight
                score = WeightFunctions.calculateAllWeight(smResult,sParam);

                //and make a new CitySuggestionObj to represent the city.
                results.add(new CitySuggestionObj(smResult.getCityRecord(),score));

                //update the largest score we have seen so far for the sake of normalization of scores.
                if(score > maxScore)
                    maxScore = score;
            }
        }

        //Normalize the score.
        for(CitySuggestionObj c:results){
            c.setScore(c.getScore()/maxScore);
        }

        //return the result as an CitySuggestionCollection
        return new CitySuggestionCollection(results);
    }
}
