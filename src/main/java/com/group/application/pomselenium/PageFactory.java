package com.group.application.pomselenium;

import com.group.application.utils.BettingPlaceNames;
import org.openqa.selenium.WebDriver;

public class PageFactory {

    public IPage getPage(String name, WebDriver driver){
        if(name.equalsIgnoreCase(BettingPlaceNames.Mozzart.toString())){
            return new MozzartPage(driver);
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Meridian.toString())){
            return new MeridianPage(driver);
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Bet365.toString())){
            return new Bet365Page(driver);
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Sport888.toString())){
            return new Sport888Page(driver);
        }else if(name.equalsIgnoreCase(BettingPlaceNames.Pinnbet.toString())){
            return new PinnBetPage(driver);
        }
        return null;
    }
}
