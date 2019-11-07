package com.group.application.controllers;


import com.group.application.config.OddLoadConfig;
import com.group.application.dao.TeamNamesRepository;
import com.group.application.match.Match;
import com.group.application.oddload.*;
import com.group.application.utils.BettingPlaceNames;
import com.group.application.utils.Db;
import com.group.application.utils.MatchPrinting;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AllMatchesController {

    private Db db;
    private OddLoadConfig oddLoadConfig;
    private Load888Sport load888Sport;
    private LoadBet365 loadBet365;
    private LoadMeridian loadMeridian;
    private LoadMozzart loadMozzart;
    private LoadPinnBet loadPinnBet;
    private WebDriver driver;
    private TeamNamesRepository teamNamesRepository;

    @Autowired
    public AllMatchesController(OddLoadConfig oddLoadConfig, Load888Sport load888Sport,
                                LoadBet365 loadBet365, LoadMeridian loadMeridian,
                                LoadMozzart loadMozzart, LoadPinnBet loadPinnBet, WebDriver driver,
                                TeamNamesRepository repo) {
        this.db = Db.getInstance();
        this.oddLoadConfig = oddLoadConfig;
        this.load888Sport = load888Sport;
        this.loadBet365 = loadBet365;
        this.loadMeridian = loadMeridian;
        this.loadMozzart = loadMozzart;
        this.loadPinnBet = loadPinnBet;
        this.driver = driver;
        this.teamNamesRepository = repo;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public String start(){
        return "all";
    }


    @RequestMapping(value = "/startAll", method = RequestMethod.POST)
    public ModelAndView printMatches(@RequestParam("country") String country,
                                     @RequestParam("league") String league,
                                     @RequestParam("meridian") String meridian,
                                     @RequestParam("mozzart") String mozzart,
                                     @RequestParam("sport888") String sport888,
                                     @RequestParam("bet365") String bet365)
            throws IOException, CloneNotSupportedException, SQLException {

        setParameters(country, league);
        ModelAndView map = new ModelAndView("printMatches");
        //setParameters(country, league);
        setParametersJDBCTemplate(country, league);
        List<String> list = compare(meridian, sport888, bet365, mozzart);

        map.addObject("list", list);
        map.addObject("country", country);
        map.addObject("league", league);
        return map;
    }

    private void setParametersJDBCTemplate(String country, String league){
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);

        loadMeridian.setTimovi(teamNamesRepository.loadTeamsForLeague(
                league, BettingPlaceNames.Meridian.toString()));
        loadPinnBet.setTimovi(teamNamesRepository.loadTeamsForLeague(
                league, BettingPlaceNames.Pinnbet.toString()));
        loadMozzart.setTimovi(teamNamesRepository.loadTeamsForLeague(
                league, BettingPlaceNames.Mozzart.toString()));
        loadBet365.setTimovi(teamNamesRepository.loadTeamsForLeague(
                league, BettingPlaceNames.Bet365.toString()));
        load888Sport.setTimovi(teamNamesRepository.loadTeamsForLeague(
                league, BettingPlaceNames.Sport888.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }

    private void setParameters(String country, String league) throws SQLException {
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);

        loadMeridian.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Meridian.toString()));
        loadPinnBet.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadMozzart.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Mozzart.toString()));
        loadBet365.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Bet365.toString()));
        load888Sport.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Sport888.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }


    private List<String> compare(String meridian, String sport888, String bet365, String mozzart
    ) throws IOException, CloneNotSupportedException {
        List<Match> utakmiceMeridian;
        List<Match> utakmicePinnBet;
        List<Match> utakmiceBet365;
        List<Match> utakmice888Sport;
        List<Match> utakmiceMozzart;
        List<String> all = new ArrayList<>();

        utakmiceMeridian = loadMeridian.load(meridian);
        utakmicePinnBet = loadPinnBet.load("https://www.pinnbet.com/");
        utakmiceMozzart = loadMozzart.load(mozzart);
        utakmiceBet365 = loadBet365.load(bet365);
        utakmice888Sport = load888Sport.load(sport888);

        all.addAll(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmiceMeridian,
                BettingPlaceNames.Meridian.toString()));
        all.addAll(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmiceMozzart,
                BettingPlaceNames.Mozzart.toString()));
        all.addAll(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmiceBet365,
                BettingPlaceNames.Bet365.toString()));
        all.addAll(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet, utakmice888Sport,
                BettingPlaceNames.Sport888.toString()));

        return all;
    }
}
