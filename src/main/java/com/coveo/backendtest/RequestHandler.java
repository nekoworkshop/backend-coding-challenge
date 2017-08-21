package com.coveo.backendtest;
/**
 * This class is responsible for receiving the query from the user as well as returning the
 * final result back to the user.
 *
 * Dependency: CitySuggestionFinder, the class handles the searching,filtering and weighting.
 *              Trie, dependency of CitySuggestionFinder, data structure used in searching.
 */

import com.coveo.backendtest.framework.ServiceLocator;
import com.coveo.backendtest.utils.StringSearchAggregator;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
    public String Respond(@QueryParam("name") String name, @Context UriInfo uriInfo, String content) throws JsonProcessingException{


        //Reading request parameter
        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String queryParam = queryParams.getFirst("q");
        String latitudeParam = queryParams.getFirst("latitude");
        String longitudeParam = queryParams.getFirst("longitude");

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

        CitySuggestionCollection responseBody = finder.lookup(sParam);

        return responseBody.generateJSON();
    }
}
