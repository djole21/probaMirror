package com.group.application.utils;

public enum BettingPlaceNames {
    Mozzart("mozzart"),
    Meridian("meridian"),
    Bet365("bet365"),
    Pinnbet("pinnbet"),
    Sport888("888sport");

    private final String name;

    BettingPlaceNames(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
