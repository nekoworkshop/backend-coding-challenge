package com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataRecordObj;

public class StringMatchResultObj {

    private MatchTypes matchType;
    private GeoDataRecordObj cityRecord;
    private boolean alternativeNameMatch;

    public StringMatchResultObj(MatchTypes matchType, GeoDataRecordObj cityRecord) {
        this.matchType = matchType;
        this.cityRecord = cityRecord;
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

    public boolean isAlternativeNameMatch() {
        return alternativeNameMatch;
    }

    public void setAlternativeNameMatch(boolean alternativeNameMatch) {
        this.alternativeNameMatch = alternativeNameMatch;
    }
}
