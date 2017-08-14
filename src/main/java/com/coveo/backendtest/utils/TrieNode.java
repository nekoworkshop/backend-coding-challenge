package com.coveo.backendtest.utils;

import com.coveo.backendtest.GeoDataRecordObj;

import java.util.ArrayList;
import java.util.HashMap;

public class TrieNode {
    private char c;
    private HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
    private ArrayList<GeoDataRecordObj> exactMatch = new ArrayList<>();
    private boolean isLeaf;

    public TrieNode() {}

    public TrieNode(char c){
        this.c = c;
    }

    /**
     * The method used for add a city's record to a node.
     * This method makes sure that the exactMatch doesn't contains duplicates.
     * @param g
     */
    public void addExactMatch (GeoDataRecordObj g){
        boolean foundDup = false;
        for(GeoDataRecordObj city: this.exactMatch)
            if(city.getId() == g.getId())
                foundDup = true;

        if(!foundDup)
            this.exactMatch.add(g);
    }

    /**
     * Getter and setters.
     */
    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public HashMap<Character, TrieNode> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Character, TrieNode> children) {
        this.children = children;
    }

    public ArrayList<GeoDataRecordObj> getExactMatch() {
        return exactMatch;
    }

    public void setExactMatch(ArrayList<GeoDataRecordObj> exactMatch) {
        this.exactMatch = exactMatch;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }
}
