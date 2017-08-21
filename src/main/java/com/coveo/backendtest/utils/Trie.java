package com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataDAO;
import com.coveo.backendtest.GeoDataRecordObj;

import java.util.*;

public class Trie extends StringMatchAlgo{

    private TrieNode root;
    private GeoDataDAO dao;

    public Trie(GeoDataDAO dao) {
        this.dao = dao;
        this.populate();
    }

    public Trie(){
        this.root = new TrieNode();
    }

    /**
     * Public method for on-demand rebuild request.
     */
    public void rebuild(){
        this.populate();
    }

    /**
     * private method for populate or rebuilding the Trie.
     */
    private void populate(){
        this.root = new TrieNode();
        List<GeoDataRecordObj> l = dao.getAllGeoData();
        for(GeoDataRecordObj o: l){
            this.insert(o);
        }
    }


    /**
     * Private method for adding a city with all it's possible names to the trie.
     * For the purpose of searching, all strings will be converted to lowercase.
     *
     * @param city
     */
    public void insert(GeoDataRecordObj city){
        this.insert(city, city.getName().toLowerCase());
        this.insert(city, city.getAsciiname().toLowerCase());
        if (!city.getAlternatenames().isEmpty()) {
            for (String alternateName : city.getAlternatenames()) {
                this.insert(city, alternateName.toLowerCase());
            }
        }
    }


    /**
     * Private method for adding a single name with its associated city record into the trie.
     * This is where we build the trie.
     * @param city
     * @param name
     */
    private void insert(GeoDataRecordObj city, String name) {
        HashMap<Character, TrieNode> children = root.getChildren();

        assert(name.length()>0);

        for(int i=0; i<name.length(); i++){
            char c = name.charAt(i);

            //build the tree as we go through each char
            TrieNode t;

            //Does the node for char c already exist?
            if(children.containsKey(c)){
                //if so, update the pointer.
                t = children.get(c);
            }else{
                //If not, create a new node and insert it into the children hashmap.
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.getChildren();

            //If we have reached the last character of the city name, store it's GeoDataRecordObj reference in it.
            if(i==name.length()-1)
                t.addExactMatch(city);
        }
    }

    /**
     * Public interface for searching cities in the trie.
     * @param str The query. It could be either a complete city name or a partially typed one.
     * @return The resule is stored in a two dimensional list. The first row is the exact match while the other row
     *          is partial match (auto-completion)
     */
    public List<StringMatchResultObj> searchCity (String str){
        //convert the search string to lower case.
        str = str.toLowerCase();

        //Prepare the list to be returned.
        List<StringMatchResultObj> result = new ArrayList<>();

        /**
         * The searchNode method will return a node that exact matches the query string.
         * This means that all its children are super strings of the query.
         *
         * Example: searchNode("Mon") will return the node "n" which has "m" and "o" as it's grandfather and father node.
         * The node that contains the record of Montreal,which should be an "l" ,could be reached from this
         * node and then t-r-e-a-l.
         *
         * The method returning null means that there is no string in the tree that has the query as prefix.
         */
        TrieNode node = this.searchNode(str);
        if (node == null){
            return Collections.emptyList();
        }else{
            //Add exact match result.
            for (GeoDataRecordObj city: node.getExactMatch())
                result.add(new StringMatchResultObj(MatchTypes.EXACT_MATCH, city));

            //Now we need to find all the city that has the query string as its prefix.
            //We use a set to rule out the duplications.
            Set<GeoDataRecordObj> superStringMatch = new HashSet<>();

            //Recursively traverse the sub tree to find all cities within.
            for(TrieNode child: node.getChildren().values())
                superStringMatch.addAll(getCityBySuperString(child));

            //Add prefix match result from the temporary set.
            for(GeoDataRecordObj city: superStringMatch)
                result.add(new StringMatchResultObj(MatchTypes.PREFIX_MATCH, city));
            return result;
        }



    }

    /**
     * Try to reach a node where the query string can be constructed by this node and its parents.
     * @param str
     * @return
     */
    private TrieNode searchNode(String str){
        Map<Character, TrieNode> children = root.getChildren();
        TrieNode t = null;

        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);

            //Check if there is possible route to the next node.
            if(children.containsKey(c)){
                //If so, update the current node pointer and children pointer
                t = children.get(c);
                children = t.getChildren();
            }else{
                //A dead end means that the query string cannot be constructed by this trie.
                return null;
            }
        }

        return t;
    }

    /**
     * This method returns all GeoDataRecordObj from the subtrees starting from parameter node.
     * It is used to get all possible cities by matching their names as super string of the current node.
     * @param node
     * @return The result is returned in the form of a set to rule out duplications. It's the caller's responsibility
     * to convert it to a List if needed.
     */
    private Set<GeoDataRecordObj> getCityBySuperString (TrieNode node){
        Set<GeoDataRecordObj> result = new HashSet<>(node.getExactMatch());

        for(TrieNode child: node.getChildren().values())
            result.addAll(getCityBySuperString(child));
        return result;
    }


    public GeoDataDAO getDao() {
        return dao;
    }

    public void setDao(GeoDataDAO dao) {
        this.dao = dao;
    }
}
