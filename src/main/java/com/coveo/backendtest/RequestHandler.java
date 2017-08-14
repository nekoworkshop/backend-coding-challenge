package com.coveo.backendtest;

import com.coveo.backendtest.com.coveo.backendtest.utils.Trie;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.inject.Inject;
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

    private Trie trie;
    private CitySuggestionFinder finder;

    public RequestHandler(Trie t, CitySuggestionFinder f){
        this.trie = t;
        this.finder = f;
    }

    /**
     * The no-argument constructor is called by the container.
     */
    public RequestHandler(){
        this.trie = ServiceLocator.getTrieInstance();
        this.finder = new CitySuggestionFinder(trie);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String Respond(@QueryParam("name") String name, @Context UriInfo uriInfo, String content) throws JsonProcessingException{

        //TODO: be able to obtain the search result in the form of CitySuggestionObj.

        MultivaluedMap<String, String> queryParams = uriInfo.getQueryParameters();
        String queryParam = queryParams.getFirst("q");
        String latitudeParam = queryParams.getFirst("latitude");
        String longitudeParam = queryParams.getFirst("longitude");



        CitySuggestionCollection responseBody = new CitySuggestionCollection();

        return responseBody.generateJSON();
    }
}
