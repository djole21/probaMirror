package com.group.application.match;

import com.group.application.odds.Odd;

import java.util.HashMap;

public class Match {

    private String domacin;
    private String gost;
    private HashMap<String, Odd> kvote;

    public Match(String domacin, String gost) {
        this.kvote = new HashMap<String, Odd>();
        this.domacin = domacin;
        this.gost = gost;
    }

    public Odd get(String key){
        return kvote.get(key);
    }

    public void add(String key, Odd o){
        kvote.put(key, o);
    }

    @Override
    public String toString() {
        return domacin + " vs " + gost + "\n" + kvote.toString();
    }

    public String getDomacin() {
        return domacin;
    }

    public String getGost() {
        return gost;
    }

    public void setKvote(HashMap<String, Odd> kvote) {
        this.kvote = kvote;
    }
}
