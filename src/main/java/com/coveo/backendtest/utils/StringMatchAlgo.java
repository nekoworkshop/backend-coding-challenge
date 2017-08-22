package com.coveo.backendtest.utils;
/**
 * Base class for all string search algorithms.
 *
 * All instance of search algorithms need to have a method return a List of cities based on a string.
 * Also all search algorithms needs to be tagged with a language.
 */

import java.util.List;

public abstract class StringMatchAlgo {
    public abstract List<StringMatchResultObj> searchCity(String searchString);

    private LanguageTag languageTag;

    public StringMatchAlgo() {
    }

    public StringMatchAlgo(LanguageTag lTag){
        this.languageTag = lTag;
    }

    public LanguageTag getLanguageTag() {
        return languageTag;
    }

    public void setLanguageTag(LanguageTag languageTag) {
        this.languageTag = languageTag;
    }
}
