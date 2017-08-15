package com.coveo.backendtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CitySuggestionCollectionTest {

    private Random rand = new Random(); //This will automatically init a random seed based on System.nanotime.

    @Test
    public void emptyJSONTest() throws JsonProcessingException {
        String expected = "{\"suggestions\":[]}";

        CitySuggestionCollection j = new CitySuggestionCollection();

        assertEquals(expected,j.generateJSON());
    }

    @Test
    public void singleElementJSONTest() throws JsonProcessingException {
        String expected = "{\"suggestions\":[{\"name\":\"London, ON, Canada\",\"latitude\":\"42.98339\",\"longitude\":\"-81.23304\",\"score\":0.9}]}";

        CitySuggestionObj londonON = new CitySuggestionObj("London, ON, Canada","42.98339","-81.23304",0.9);
        CitySuggestionCollection j = new CitySuggestionCollection();

        j.addSuggestion(londonON);

        assertEquals(expected,j.generateJSON());
    }

    @Test
    public void multipleElementJSONTest() throws JsonProcessingException {
        String expected = "{\"suggestions\":[{\"name\":\"London, ON, Canada\",\"latitude\":\"42.98339\",\"longitude\":\"-81.23304\",\"score\":0.9},{\"name\":\"London, OH, USA\",\"latitude\":\"39.88645\",\"longitude\":\"-83.44825\",\"score\":0.5}]}";

        CitySuggestionObj londonON = new CitySuggestionObj("London, ON, Canada","42.98339","-81.23304",0.9);
        CitySuggestionObj londonOH = new CitySuggestionObj("London, OH, USA","39.88645","-83.44825",0.5);
        CitySuggestionCollection j = new CitySuggestionCollection();

        j.addSuggestion(londonON);
        j.addSuggestion(londonOH);

        assertEquals(expected,j.generateJSON());
    }

    @Test
    public void CollectionSortingTest() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String outputString;
        CitySuggestionObj s;
        CitySuggestionCollection origCollection = new CitySuggestionCollection();

        for(int i=0;i<10;i++){
            s = new CitySuggestionObj(Integer.toString(i),"1","1",rand.nextDouble());
            origCollection.addSuggestion(s);
        }

        outputString = origCollection.generateJSON();

        CitySuggestionCollection parsedCollection = mapper.readValue(outputString, CitySuggestionCollection.class);

        Boolean descendingOrder = true;
        for(int i=0;i<9;i++){
            if (parsedCollection.getCitySuggestionObjs().get(i).getScore() < parsedCollection.getCitySuggestionObjs().get(i+1).getScore()) {
                descendingOrder = false;
                break;
            }
        }


        assertTrue(descendingOrder);
    }
}
