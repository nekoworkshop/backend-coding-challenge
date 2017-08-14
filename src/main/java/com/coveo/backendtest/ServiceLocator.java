package com.coveo.backendtest;

import com.coveo.backendtest.com.coveo.backendtest.utils.Trie;

public class ServiceLocator {

    private static GeoDataDAO dao;
    private static Trie trie;

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
}
