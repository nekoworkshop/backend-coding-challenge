package com.coveo.backendtest;

import java.util.ArrayList;

/**
 *
 * Data object representing a record in the geo database.
 * This object will be instantiated during the loading of the geo data.
 *
 * Note that the alternative names are stored in an array.
 *
 * @see GeoDataDAO_TSV;
 */

public class GeoDataRecordObj {
    private int id = -1;
    private String name = "Undefined name";
    private String asciiname = "Undefined asciiname";
    private ArrayList<String> alternatenames = null;
    private double latitude = -1;
    private double longitude = -1;
    private String featureClass = "Undefined feature class";
    private String featureCode = "Undefined feature code";
    private String countryCode = "Undefined country code";
    private String cc2 = "Undefined cc2";
    private String admin1Code = "Undefined admin1 code";
    private String admin2Code = "Undefined admin2 code";
    private String admin3Code = "Undefined admin3 code";
    private String admin4Code = "Undefined admin4 code";
    private int population = -1;
    private int elevation = -1;
    private int dem = -1;
    private String timezone = "Undefined timezone";
    private String modificationDate = "Undefined modification date";

    public GeoDataRecordObj(int id, String name, String asciiname, ArrayList<String>  alternatenames, double latitude, double longitude, String featureClass, String featureCode, String countryCode, String cc2, String admin1Code, String admin2Code, String admin3Code, String admin4Code, int population, int elevation, int dem, String timezone, String modificationDate) {
        this.id = id;
        this.name = name;
        this.asciiname = asciiname;
        this.alternatenames = alternatenames;
        this.latitude = latitude;
        this.longitude = longitude;
        this.featureClass = featureClass;
        this.featureCode = featureCode;
        this.countryCode = countryCode;
        this.cc2 = cc2;
        this.admin1Code = admin1Code;
        this.admin2Code = admin2Code;
        this.admin3Code = admin3Code;
        this.admin4Code = admin4Code;
        this.population = population;
        this.elevation = elevation;
        this.dem = dem;
        this.timezone = timezone;
        this.modificationDate = modificationDate;
    }

    public GeoDataRecordObj(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsciiname() {
        return asciiname;
    }

    public void setAsciiname(String asciiname) {
        this.asciiname = asciiname;
    }

    public ArrayList<String>  getAlternatenames() {
        return alternatenames;
    }

    public void setAlternatenames(ArrayList<String>  alternatenames) {
        this.alternatenames = alternatenames;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getFeatureClass() {
        return featureClass;
    }

    public void setFeatureClass(String featureClass) {
        this.featureClass = featureClass;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCc2() {
        return cc2;
    }

    public void setCc2(String cc2) {
        this.cc2 = cc2;
    }

    public String getAdmin1Code() {
        return admin1Code;
    }

    public void setAdmin1Code(String admin1Code) {
        this.admin1Code = admin1Code;
    }

    public String getAdmin2Code() {
        return admin2Code;
    }

    public void setAdmin2Code(String admin2Code) {
        this.admin2Code = admin2Code;
    }

    public String getAdmin3Code() {
        return admin3Code;
    }

    public void setAdmin3Code(String admin3Code) {
        this.admin3Code = admin3Code;
    }

    public String getAdmin4Code() {
        return admin4Code;
    }

    public void setAdmin4Code(String admin4Code) {
        this.admin4Code = admin4Code;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getDem() {
        return dem;
    }

    public void setDem(int dem) {
        this.dem = dem;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(String modificationDate) {
        this.modificationDate = modificationDate;
    }
}
