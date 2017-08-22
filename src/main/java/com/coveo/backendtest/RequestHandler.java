package com.coveo.backendtest;
/**
 * This class is responsible for receiving the query from the user as well as returning the
 * final result back to the user.
 *
 * Dependency: CitySuggestionFinder, the class handles the searching,filtering and weighting.
 *              Trie, dependency of CitySuggestionFinder, data structure used in searching.
 */

import com.coveo.backendtest.framework.ServiceLocator;
import com.coveo.backendtest.utils.LanguageTag;
import com.coveo.backendtest.utils.StringSearchAggregator;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

@Path("suggestions")
public class RequestHandler {

    private StringSearchAggregator searchAggregator;
    private CitySuggestionFinder finder;
    private int ignoreQueryShorterThan;

    /**
     * This constructor is used for testing.
     * @param searchAggregator
     * @param f
     */
    public RequestHandler(StringSearchAggregator searchAggregator, CitySuggestionFinder f, int ignoreQueryShorterThan){
        this.searchAggregator = searchAggregator;
        this.finder = f;
        this.ignoreQueryShorterThan = ignoreQueryShorterThan;
    }

    /**
     * The no-argument constructor is called by the container. In production environment the instance of dependency will
     * be aquired from the service locator.
     */
    public RequestHandler(){
        this.searchAggregator = ServiceLocator.getStringSearchAggregatorInstance();
        this.finder = new CitySuggestionFinder(ServiceLocator.getStringSearchAggregatorInstance());
        this.ignoreQueryShorterThan = 0;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Respond(
            @DefaultValue("") @QueryParam("q") String queryParam,
            @DefaultValue("") @QueryParam("latitude") String latitudeParam,
            @DefaultValue("") @QueryParam("longitude") String longitudeParam,
            @DefaultValue("FRE_ENG") @QueryParam("language") String language,
            @DefaultValue("-1") @QueryParam("filterCityLargerThan") int filterCityLargerThan,
            @DefaultValue("-1") @QueryParam("filterCitySmallerThan") int filterCitySmallerThan,
            @DefaultValue("-1") @QueryParam("filterCityFartherThan") double filterCityFartherThan,
            @DefaultValue("-1") @QueryParam("filterCityCloserThan") double filterCityCloserThan,
            @DefaultValue("false") @QueryParam("filterPrefixMatch") String filterPrefixMatch,
            @DefaultValue("true") @QueryParam("useDistanceBonus") String useDistanceBonus,
            @DefaultValue("true") @QueryParam("usePopulationBonus") String usePopulationBonus,
            @Context UriInfo uriInfo, String content) throws JsonProcessingException{

        //Ignore query string shorter than a threshold. Default is 0, meaning we don't ignore anything.
        if(queryParam.length() < ignoreQueryShorterThan)
            return new CitySuggestionCollection().generateJSON();

        //Now construct the search parameters.
        SearchParam sParam = new SearchParam();

        //The string user typed.
        sParam.setSearchString(queryParam);

        //Check if the user provided valid position info.
        if(latitudeParam.isEmpty() || longitudeParam.isEmpty()) {
            //If not, don't use the distance weighting bonus.
            sParam.setUseDistanceBonus(false);
        }else{
            //If user position is available, put the info into the search param.
           sParam.setUserLong(Double.parseDouble(longitudeParam));
           sParam.setUserLat(Double.parseDouble(latitudeParam));
           sParam.setUseDistanceBonus(true);
        }

        //Set preferred language in search.
        switch (language){
            case "FRE_ENG":
                sParam.setPreferedLanguage(LanguageTag.FREandENG);
                break;
            case"OTHER":
                sParam.setPreferedLanguage(LanguageTag.others);
                break;
            default:
                sParam.setPreferedLanguage(LanguageTag.FREandENG);
        }

        if(filterCityLargerThan != -1)
            sParam.setFilterCityLargerThan(filterCityLargerThan);
        if(filterCitySmallerThan != -1)
            sParam.setFilterCitySmallerThan(filterCitySmallerThan);
        if(filterCityFartherThan != -1)
            sParam.setFilterCityFartherThan(filterCityFartherThan);
        if(filterCityCloserThan != -1)
            sParam.setFilterCityCloserThan(filterCityCloserThan);

        sParam.setFilterPrefixMatch(Boolean.parseBoolean(filterPrefixMatch));

        sParam.setUseDistanceBonus(Boolean.parseBoolean(useDistanceBonus));

        sParam.setUsePopulationBonus(Boolean.parseBoolean(usePopulationBonus));


        CitySuggestionCollection responseBody = finder.lookup(sParam);

        return responseBody.generateJSON();
    }
}
