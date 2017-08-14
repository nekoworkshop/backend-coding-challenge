package com.coveo.backendtest;

/**
 *
 * This class handles the searching,filtering and weighting of the suggestions, based on the various input from the user.
 * The DAO is responsible for returning all the matching GeoDataRecordObj based on the starting portion of the cities name, while the
 * filtering and weighting is implemented in this class.
 *
 */

import com.coveo.backendtest.com.coveo.backendtest.utils.Trie;

import java.util.ArrayList;

public class CitySuggestionFinder {

    private Trie trie;

    public CitySuggestionFinder(Trie t){
        this.trie = t;
    }

    //TODO: actual search logic.
    public CitySuggestionCollection lookup(int weightingScheme){
        CitySuggestionCollection result = new CitySuggestionCollection();

        return result;
    }
}
