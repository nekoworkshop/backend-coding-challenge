package com.coveo.backendtest;

import java.util.ArrayList;
import java.util.Collections;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

/**
 *
 * Object that holds the search result for a query, responsible for the sorting and JSON-ify of the final result.
 *
 * It will contain one or more ( or none ) of CitySuggestionObj.
 *
 * @see CitySuggestionObj
 *
 */

public class CitySuggestionCollection {

    @JsonProperty("suggestions")
    private ArrayList<CitySuggestionObj> citySuggestionObjs;

    public CitySuggestionCollection() {
        this.citySuggestionObjs = new ArrayList<>();
    }

    public void addSuggestion (CitySuggestionObj s){
        this.citySuggestionObjs.add(s);
    }

    public String generateJSON() throws JsonProcessingException {
        Collections.sort(this.citySuggestionObjs,Collections.reverseOrder());
        ObjectMapper mapper = new ObjectMapper();
        //mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); //https://stackoverflow.com/questions/8367312/serializing-with-jackson-json-getting-no-serializer-found
        return mapper.writeValueAsString(this);
    }

    public ArrayList<CitySuggestionObj> getCitySuggestionObjs() {
        return citySuggestionObjs;
    }

    public void setCitySuggestionObjs(ArrayList<CitySuggestionObj> citySuggestionObjs) {
        this.citySuggestionObjs = citySuggestionObjs;
    }
}
