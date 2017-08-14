package com.coveo.backendtest;

/**
 * This class is passed to the CitySuggestionFinder, providing all filtering/weighting criteria and
 * necessary additional information (e.g searcher's location.)
 */
public class SearchParam {

    private int keepCityLargerThan = 0;
    private int keepCitySmallerThan = Integer.MAX_VALUE;
    private double keepCityFartherThan = 0.0;
    private double keepCityCloserThan = Double.MAX_VALUE;
    private boolean useDistanceBonus = true;
    private boolean usePopulationBonus = true;
    // Additional information.
    private double userLat = 0.0;
    private double userLong = 0.0;

    public int getKeepCityLargerThan() {
        return keepCityLargerThan;
    }

    public void setKeepCityLargerThan(int keepCityLargerThan) {
        this.keepCityLargerThan = keepCityLargerThan;
    }

    public int getKeepCitySmallerThan() {
        return keepCitySmallerThan;
    }

    public void setKeepCitySmallerThan(int keepCitySmallerThan) {
        this.keepCitySmallerThan = keepCitySmallerThan;
    }

    public double getKeepCityFartherThan() {
        return keepCityFartherThan;
    }

    public void setKeepCityFartherThan(double keepCityFartherThan) {
        this.keepCityFartherThan = keepCityFartherThan;
    }

    public double getKeepCityCloserThan() {
        return keepCityCloserThan;
    }

    public void setKeepCityCloserThan(double keepCityCloserThan) {
        this.keepCityCloserThan = keepCityCloserThan;
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
