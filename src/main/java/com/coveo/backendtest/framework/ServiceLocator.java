package com.coveo.backendtest.framework;

import com.coveo.backendtest.GeoDataDAO;
import com.coveo.backendtest.utils.StringSearchAggregator;
import com.coveo.backendtest.utils.Trie;

public class ServiceLocator {

    private static GeoDataDAO dao;
    private static Trie trie;
    private static StringSearchAggregator searchAggregator;

   public static void setDAOInstance (GeoDataDAO daoinst){
       dao = daoinst;
   }

   public static void setTrieInstance (Trie trieInst){
       trie = trieInst;
   }

    public static GeoDataDAO getDAOInstance (){
       return dao;
    }

    public static Trie getTrieInstance (){
        return trie;
    }

    public static void setStringSearchAggregatorInstance (StringSearchAggregator searchAggregatorInst){
       searchAggregator = searchAggregatorInst;
    }

    public static StringSearchAggregator getStringSearchAggregatorInstance(){
        return searchAggregator;
    }
}
