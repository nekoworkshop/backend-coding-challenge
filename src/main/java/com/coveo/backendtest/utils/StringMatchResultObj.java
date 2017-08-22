package com.coveo.backendtest.utils;
/**
 * The class that represents the result returned from a search algorithm.
 * Besides, the GeoDataRecordObj, additional information like language and type of string match are also encapsulated into
 * this class.
 */

import com.coveo.backendtest.GeoDataRecordObj;

import java.util.Objects;

public class StringMatchResultObj {

    private MatchTypes matchType;
    private LanguageTag languageTag;
    private GeoDataRecordObj cityRecord;

    public StringMatchResultObj(MatchTypes matchType, LanguageTag languageTag, GeoDataRecordObj cityRecord) {
        this.matchType = matchType;
        this.languageTag = languageTag;
        this.cityRecord = cityRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringMatchResultObj that = (StringMatchResultObj) o;
        return getMatchType() == that.getMatchType() &&
                getLanguageTag() == that.getLanguageTag() &&
                Objects.equals(getCityRecord(), that.getCityRecord());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatchType(), getLanguageTag(), getCityRecord());
    }

    public MatchTypes getMatchType() {
        return matchType;
    }

    public void setMatchType(MatchTypes matchType) {
        this.matchType = matchType;
    }

    public GeoDataRecordObj getCityRecord() {
        return cityRecord;
    }

    public void setCityRecord(GeoDataRecordObj cityRecord) {
        this.cityRecord = cityRecord;
    }

    public LanguageTag getLanguageTag() {
        return languageTag;
    }

    public void setLanguageTag(LanguageTag languageTag) {
        this.languageTag = languageTag;
    }


}
