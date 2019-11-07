package com.group.application.odds;

import com.group.application.utils.OddNames;

public class GetOddFactory {

    public Odd getOdd(String name){
        if(name == null) {
            return null;
        }
        if(name.equalsIgnoreCase(OddNames.FullTime.toString())){
            return new KonacanIshod();
        } else if(name.equalsIgnoreCase(OddNames.TotalGoals.toString())){
            return  new UkupnoGolova();
        } else if(name.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())){
            return new ObaTimaDajuGol();
        }

        return null;

    }
}
