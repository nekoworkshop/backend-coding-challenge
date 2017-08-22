package com.coveo.backendtest.framework;
/**
 * The ContextListener class handles the initialization of the service via the contextInitialized method, which will be
 * called right before the service container is ready to serve requests.
 */

import com.coveo.backendtest.GeoDataDAO_TSV;
import com.coveo.backendtest.GeoDataRecordObj;
import com.coveo.backendtest.utils.LanguageTag;
import com.coveo.backendtest.utils.StringSearchAggregator;
import com.coveo.backendtest.utils.Trie;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();

        try {
            //Create, set and initialize DAO instance
            ServiceLocator.setDAOInstance(new GeoDataDAO_TSV(new InputStreamReader(this.
                    getClass().getResourceAsStream("/cities_canada-usa.tsv"), "UTF-8")));

            //Initialize Trie instances, one for French and English and another for all alternative names.
            Trie trieFR_EN = new Trie();
            Trie trieAlternative = new Trie();
            trieFR_EN.setLanguageTag(LanguageTag.FREandENG);
            trieAlternative.setLanguageTag(LanguageTag.others);

            //Populate the tries with the geo-data.
            List<GeoDataRecordObj> geoData = ServiceLocator.getDAOInstance().getAllGeoData();
            for(GeoDataRecordObj g:geoData){
                trieFR_EN.insert(g,g.getName().toLowerCase());
                trieFR_EN.insert(g,g.getAsciiname().toLowerCase());

                if (!g.getAlternatenames().isEmpty()) {
                    for (String alternateName : g.getAlternatenames()) {
                        trieAlternative.insert(g, alternateName.toLowerCase());
                    }
                }
            }

            //Instantiate the StringSearchAggregator
            ServiceLocator.setStringSearchAggregatorInstance(new StringSearchAggregator());

            //Add search algorithms into the search aggregator. For now we only have Trie.
            ServiceLocator.getStringSearchAggregatorInstance().addStringMatchAlgorithm(trieFR_EN);
            ServiceLocator.getStringSearchAggregatorInstance().addStringMatchAlgorithm(trieAlternative);

        } catch (UnsupportedEncodingException e) {
            //TODO:UnsupportedEncodingException
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

}

