package com.group.application.oddload;

import com.group.application.pomselenium.IPage;
import com.group.application.teamnames.ITeamNames;
import com.group.application.utils.BettingPlaceNames;

public class OddLoadFactory {

    public ILoad getLoading(String name, IPage page, ITeamNames timovi, String country, String league){
        if(name.equalsIgnoreCase(BettingPlaceNames.Mozzart.toString())) {
            return new LoadMozzart(page, timovi);
        }
        else if(name.equalsIgnoreCase(BettingPlaceNames.Meridian.toString())){
            return new LoadMeridian(page, timovi);
        }
        else if(name.equalsIgnoreCase(BettingPlaceNames.Bet365.toString())){
            return new LoadBet365(page, timovi);
        }
        else if(name.equalsIgnoreCase(BettingPlaceNames.Sport888.toString())){
            return new Load888Sport(page, timovi);
        }
        else if(name.equalsIgnoreCase(BettingPlaceNames.Pinnbet.toString())){
            return new LoadPinnBet(page, timovi, country, league);
        }
        return null;
    }
}
