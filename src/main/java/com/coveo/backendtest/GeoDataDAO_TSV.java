package com.coveo.backendtest;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.apache.commons.lang3.StringUtils;
import org.jvnet.hk2.annotations.Service;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DAO for tsv files, responsible for reading and parsing the geo database records.
 *
 * Use getInstance to obtain the reference and use getAllGeoDate() retrive an array of parsed GeoDataRecordObjs.
 */
public class GeoDataDAO_TSV implements GeoDataDAO {

    private ArrayList<GeoDataRecordObj> allGeoData = null;
    private InputStreamReader inputStreamReader;

    public GeoDataDAO_TSV (InputStreamReader inputStreamReader){
        this.inputStreamReader = inputStreamReader;
    }

    @Override
    public ArrayList<GeoDataRecordObj> getAllGeoData() {
        if(allGeoData == null) {
            this.reloadData();
        }
        return allGeoData;
    }

    //TODO: Error handling for not supplying inputStreamReader
    //Used for manually reload the database. Useful if the database is being constantly updated.
    public synchronized void reloadData(){
        TsvParserSettings settings = new TsvParserSettings();
        settings.setLineSeparatorDetectionEnabled(true);
        RowListProcessor rowProcessor = new RowListProcessor();
        settings.setRowProcessor(rowProcessor);
        settings.setHeaderExtractionEnabled(true);
        TsvParser parser = new TsvParser(settings);
        parser.parse(this.inputStreamReader);
        List<String[]>rows = rowProcessor.getRows();


        allGeoData = new ArrayList<>();
        GeoDataRecordObj o;
        for (String[] row: rows){
            o = new GeoDataRecordObj();
            o.setId(Integer.parseInt(row[0]));
            o.setName(row[1]);
            o.setAsciiname(row[2]);
            o.setAlternatenames(StringUtils.isBlank(row[3])?null:new ArrayList(Arrays.asList(row[3].split(","))));
            o.setLatitude(Double.parseDouble(row[4]));
            o.setLongitude(Double.parseDouble(row[5]));
            o.setFeatureClass(row[6]);
            o.setFeatureCode(row[7]);
            o.setCountryCode(row[8]);
            o.setCc2(row[9]);
            o.setAdmin1Code(row[10]);
            o.setAdmin2Code(row[11]);
            o.setAdmin3Code(row[12]);
            o.setAdmin4Code(row[13]);
            o.setPopulation(StringUtils.isBlank(row[14])?-1:Integer.parseInt(row[14]));
            o.setElevation(StringUtils.isBlank(row[15])?-1:Integer.parseInt(row[15]));
            o.setDem(StringUtils.isBlank(row[16]) ? 0 : Integer.parseInt(row[16]));
            o.setTimezone(row[17]);
            o.setModificationDate(row[18]);

            allGeoData.add(o);
        }

    }

    public InputStreamReader getInputStreamReader() {
        return inputStreamReader;
    }

    public void setInputStreamReader(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader;
    }
}
