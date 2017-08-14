package com.coveo.backendtest;

import org.glassfish.jersey.spi.Contract;

import java.util.ArrayList;

/**
 * DAO interface for reading the city data from a database or the tsv file.
 */

public interface GeoDataDAO {
    ArrayList<GeoDataRecordObj> getAllGeoData();
}
