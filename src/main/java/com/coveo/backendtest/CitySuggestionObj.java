package com.coveo.backendtest;

/**
 *
 * The CitySuggestionObj class represent a single search result that will be returned to the user.
 *
 * One or more of this object will be put into CitySuggestionCollection,
 * which will handle the generation of the final JSON.
 *
 * @see com.coveo.backendtest.CitySuggestionCollection
 *
 */

import com.coveo.backendtest.utils.StringMatchResultObj;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "name", "latitude", "longitude", "score"})
public class CitySuggestionObj implements Comparable<CitySuggestionObj>{

    private static String[] canadaAdmin1Map = {"AB","BC","MB","NB","NL","Undefined code","NS","ON","PE","QC","SK","YT","NT","NU"};

    private String name = "Undefined name";
    private String latitude = "Undefined latitude";
    private String longitude = "Undefined longitude";
    private double score = -1;

    public CitySuggestionObj (GeoDataRecordObj cityRecord, double score){
        this.name = cityRecord.getName()+", "+admin1ToString(cityRecord.getAdmin1Code(),cityRecord.getCountryCode())+", "+countryCodeToString(cityRecord.getCountryCode());
        this.latitude = Double.toString(cityRecord.getLatitude());
        this.longitude = Double.toString(cityRecord.getLongitude());
        this.score = score;
    }

    public CitySuggestionObj(String name, String latitude, String longitude, double score){
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.score = score;
    }

    public CitySuggestionObj(){
    }

    private static String countryCodeToString (String code){
        if (code.equals("CA"))
            return "Canada";
        return "USA";
    }

    private static String admin1ToString (String admin1Code, String countryCode){
        if (countryCode.equals("CA"))
            return canadaAdmin1Map[Integer.parseInt(admin1Code)-1];
        return admin1Code;
    }


    @Override
    public int compareTo(CitySuggestionObj o) {
        assert (this.getScore() != -1) && (o.getScore() != -1);
        return Double.compare(this.getScore(),o.getScore());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
