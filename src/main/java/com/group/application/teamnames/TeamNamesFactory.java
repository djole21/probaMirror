package com.group.application.teamnames;

import com.group.application.utils.BettingPlaceNames;

public class TeamNamesFactory {

    public ITeamNames getTeamNames(String bettingPlace){
        if(bettingPlace.equalsIgnoreCase(BettingPlaceNames.Mozzart.toString())){
            return new TeamNamesMozzart();
        }else if(bettingPlace.equalsIgnoreCase(BettingPlaceNames.Meridian.toString())){
            return new TeamNamesMeridian();
        }else if(bettingPlace.equalsIgnoreCase(BettingPlaceNames.Bet365.toString())){
            return new TeamNamesBet365();
        }else if(bettingPlace.equalsIgnoreCase(BettingPlaceNames.Sport888.toString())){
            return new TeamNames888Sport();
        }else if(bettingPlace.equalsIgnoreCase(BettingPlaceNames.Pinnbet.toString())){
            return new TeamNamesPinnBet();
        }

        return null;

    }
}
