package com.coveo.backendtest;

import com.coveo.backendtest.utils.Haversine;
import com.coveo.backendtest.utils.MatchTypes;
import com.coveo.backendtest.utils.StringMatchResultObj;

/**
 * This class encapsulates user's search preference (filtering/weighting) and
 * necessary additional information (e.g searcher's location.)
 */
public class SearchParam {

    private String searchString = "Undefined";

    //Filtering preference
    private int filterCityLargerThan = Integer.MAX_VALUE;
    private int filterCitySmallerThan = 0;
    private double filterCityFartherThan = Double.MAX_VALUE;
    private double filterCityCloserThan = 0.0;
    private boolean filterFuzzyMatch = false;
    private boolean filterPrefixMatch = false;

    //weighting preference
    private boolean useDistanceBonus = true;
    private boolean usePopulationBonus = true;

    // Additional information.
    private double userLat = 0.0;
    private double userLong = 0.0;

    public SearchParam(String searchString){
        this.searchString = searchString;
    }

    public SearchParam(){}

    /**
     * This method checks whether a city needs to be filtered out or not, based on the user's search preference.
     * To add a new preference, add corresponding field in the SearchParam class and code in the check in this class.
     *
     * Do notice the difference between filtering and weighting. Filtered city will not show up in the result at all.
     * To change how the result is weighted, check WeightFunction class.
     *
     * @see com.coveo.backendtest.utils.WeightFunctions
     *
     * @param searchResult A single result returned from the Trie or other data structure.
     * @return returning true indicates the city passed in needed to be excluded from the final result.
     */
    public boolean isSearchResultValid (StringMatchResultObj searchResult){

        GeoDataRecordObj city = searchResult.getCityRecord();

        //Distance preferences
        if(Haversine.distance(userLat,userLong,city.getLatitude(),city.getLongitude())
                <= filterCityCloserThan)
            return false;

        if(Haversine.distance(userLat,userLong,city.getLatitude(),city.getLongitude())
                >= filterCityFartherThan)
            return false;

        //City size preferences
        if(city.getPopulation()
                <= filterCitySmallerThan)
            return false;

        if(city.getPopulation()
                >= filterCityLargerThan)
            return false;

        //Search method preferences
        if(filterFuzzyMatch && searchResult.getMatchType() == MatchTypes.FUZZY_MATCH)
            return false;

        if(filterPrefixMatch && searchResult.getMatchType() == MatchTypes.PREFIX_MATCH)
            return false;

        //A city who can pass all the checks is retained in the result.
        return true;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public int getFilterCityLargerThan() {
        return filterCityLargerThan;
    }

    public void setFilterCityLargerThan(int filterCityLargerThan) {
        this.filterCityLargerThan = filterCityLargerThan;
    }

    public int getFilterCitySmallerThan() {
        return filterCitySmallerThan;
    }

    public void setFilterCitySmallerThan(int filterCitySmallerThan) {
        this.filterCitySmallerThan = filterCitySmallerThan;
    }

    public double getFilterCityFartherThan() {
        return filterCityFartherThan;
    }

    public void setFilterCityFartherThan(double filterCityFartherThan) {
        this.filterCityFartherThan = filterCityFartherThan;
    }

    public double getFilterCityCloserThan() {
        return filterCityCloserThan;
    }

    public void setFilterCityCloserThan(double filterCityCloserThan) {
        this.filterCityCloserThan = filterCityCloserThan;
    }

    public boolean isUseDistanceBonus() {
        return useDistanceBonus;
    }

    public void setUseDistanceBonus(boolean useDistanceBonus) {
        this.useDistanceBonus = useDistanceBonus;
    }

    public boolean isUsePopulationBonus() {
        return usePopulationBonus;
    }

    public void setUsePopulationBonus(boolean usePopulationBonus) {
        this.usePopulationBonus = usePopulationBonus;
    }

    public double getUserLat() {
        return userLat;
    }

    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }

    public double getUserLong() {
        return userLong;
    }

    public void setUserLong(double userLong) {
        this.userLong = userLong;
    }

}
