package com.coveo.backendtest.utils;

import com.coveo.backendtest.SearchParam;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StringSearchAggregator {

    private List<StringMatchAlgo> searchAlgorithms;

    public StringSearchAggregator() {
        this.searchAlgorithms = new ArrayList<>();
    }

    public void addStringMatchAlgorithm(StringMatchAlgo algoToAdd){
        searchAlgorithms.add(algoToAdd);
    }

    public Set<StringMatchResultObj> searchCity(SearchParam searchParam){

        Set<StringMatchResultObj> result = new HashSet<>();

        //Iterate through all available search algorithms
        for(StringMatchAlgo algo: this.searchAlgorithms){

            //Only run search on the data structure with a language we are interested in.
            if(algo.getLanguageTag() == searchParam.getPreferedLanguage())
                result.addAll(algo.searchCity(searchParam.getSearchString()));
        }

        return result;
    }
}
