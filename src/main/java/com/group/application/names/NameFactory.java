package com.group.application.names;


import com.group.application.utils.BettingPlaceNames;

public class NameFactory {
    public static INameFactory getFactory(String name){
        if(name.equalsIgnoreCase(BettingPlaceNames.Mozzart.toString())) {
            return new MozzartNameFactory();
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Meridian.toString())) {
            return new MeridianNameFactory();
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Pinnbet.toString())) {
            return new PinnbetNameFactory();
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Sport888.toString())) {
            return new Sport888NameFactory();
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Bet365.toString())) {
            return new Bet365NameFactory();
        }else {
            return null;
        }
    }
}
