package com.coveo.backendtest.framework;


import com.coveo.backendtest.GeoDataDAO_TSV;
import com.coveo.backendtest.framework.ServiceLocator;
import com.coveo.backendtest.utils.Trie;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext servletContext = event.getServletContext();

        try {
            //Initialize DAO instance
            ServiceLocator.setDAOInstance(new GeoDataDAO_TSV(new InputStreamReader(this.
                    getClass().getResourceAsStream("/cities_canada-usa.tsv"), "UTF-8")));
            //Initialize Trie instance. The trie will be populated with the data from the DAO.
            ServiceLocator.setTrieInstance(new Trie(ServiceLocator.getDAOInstance()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // NOOP.
    }

}
