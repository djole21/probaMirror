package com.group.application;

import com.group.application.match.Match;
import com.group.application.oddload.*;
import com.group.application.pomselenium.Bet365Page;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Lazy
@Component
public class RunOrLoad {

    private String country;
    private String league;
    private String urlMeridian;
    private String urlMozzart;
    private String url888Sport;
    private String urlBet365;

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setUrlMeridian(String urlMeridian) {
        this.urlMeridian = urlMeridian;
    }

    public void setUrlMozzart(String urlMozzart) {
        this.urlMozzart = urlMozzart;
    }

    public void setUrl888Sport(String url888Sport) {
        this.url888Sport = url888Sport;
    }

    public void setUrlBet365(String urlBet365) {
        this.urlBet365 = urlBet365;
    }


    public void run(ApplicationContext applicationContext, HttpServletResponse res) throws IOException, CloneNotSupportedException {

        List<Match> utakmiceMozzart;
        List<Match> utakmicePinnBet;
        List<Match> utakmiceMeridian;
        List<Match> utakmice888Sport;
        List<Match> utakmiceBet365;

        ILoad load;

        load = applicationContext.getBean(LoadMozzart.class);
        utakmiceMozzart = load.load(urlMozzart);

        load = applicationContext.getBean(LoadPinnBet.class);
        utakmicePinnBet = load.load("https://www.pinnbet.com/");

        load = applicationContext.getBean(LoadMeridian.class);
        utakmiceMeridian = load.load(urlMeridian);

        load = applicationContext.getBean(Load888Sport.class);
        utakmice888Sport = load.load(url888Sport);

        load = applicationContext.getBean(LoadBet365.class);
        utakmiceBet365 = load.load(urlBet365);

        applicationContext.getBean(WebDriver.class).close();

/*
        MatchPrinting.printMatches(utakmiceBet365, BettingPlaceNames.Bet365.toString());
        MatchPrinting.printMatches(utakmiceMozzart, BettingPlaceNames.Mozzart.toString());
        MatchPrinting.printMatches(utakmicePinnBet, BettingPlaceNames.Pinnbet.toString());
        MatchPrinting.printMatches(utakmiceMeridian, BettingPlaceNames.Meridian.toString());
        MatchPrinting.printMatches(utakmice888Sport, BettingPlaceNames.Sport888.toString());

        try {
            MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmiceMozzart, BettingPlaceNames.Mozzart.toString(), res);
            MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmiceMeridian, BettingPlaceNames.Meridian.toString(), res);
            MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmice888Sport, BettingPlaceNames.Sport888.toString(), res);
            MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmiceBet365, BettingPlaceNames.Bet365.toString(), res);
        } catch (CloneNotSupportedException e) {
            throw new CloneNotSupportedException();
        }*/
    }

    private File createFile(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyy");
        Date today = Calendar.getInstance().getTime();
        String date = dateFormat.format(today);

        String fileName = StringUtils.deleteWhitespace(country) + "-" +
                StringUtils.deleteWhitespace(league) + "-" + date + ".txt";

        return new File("/home/djordje/Desktop/matches/" + fileName);
    }

    public void loadNames(WebDriver driver) throws IOException {
        TempC.loadNamesBet365(new Bet365Page(driver), urlBet365);
        TempC.loadNamesPinn("https://www.pinnbet.com/", driver, country, league);
        TempC.loadNamesMozz(urlMozzart, driver);
        TempC.loadNames(urlMeridian, driver);
        TempC.loadNames888(url888Sport, driver);
    }

}
