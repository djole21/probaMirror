package com.group.application.utils;

public enum OddNames {
    FullTime ("KONACAN ISHOD"),
    TotalGoals ("UKUPNO GOLOVA"),
    BothTeamsToScore ("OBA TIMA DAJU GOL");

    private final String name;

    OddNames(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }
}
