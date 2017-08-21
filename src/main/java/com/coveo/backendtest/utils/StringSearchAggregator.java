package com.coveo.backendtest.utils;

import java.util.ArrayList;
import java.util.List;

public class StringSearchAggregator {

    private List<StringMatchAlgo> searchAlgorithms;

    public StringSearchAggregator() {
        this.searchAlgorithms = new ArrayList<>();
    }

    public void addStringMatchAlgorithm(StringMatchAlgo algoToAdd){
        searchAlgorithms.add(algoToAdd);
    }

    public List<StringMatchResultObj> searchCity(String searchString){

        List<StringMatchResultObj> result = new ArrayList<>();

        for(StringMatchAlgo algo: this.searchAlgorithms){
            result.addAll(algo.searchCity(searchString));
        }

        return result;
    }
}
