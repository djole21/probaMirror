package com.group.application.controllers;

import com.group.application.config.OddLoadConfig;
import com.group.application.dao.TeamNamesRepository;
import com.group.application.match.Match;
import com.group.application.oddload.ILoad;
import com.group.application.oddload.LoadBet365;
import com.group.application.oddload.LoadPinnBet;
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
public class Bet365MatchesController {

    private OddLoadConfig oddLoadConfig;
    private LoadBet365 loadBet365;
    private LoadPinnBet loadPinnBet;
    private Db db;
    private WebDriver driver;
    private TeamNamesRepository teamNamesRepository;

    @Autowired
    public Bet365MatchesController(OddLoadConfig oddLoadConfig, LoadBet365 loadBet365,
                                   LoadPinnBet loadPinnBet, WebDriver driver, TeamNamesRepository repo) {
        this.oddLoadConfig = oddLoadConfig;
        this.loadBet365 = loadBet365;
        this.loadPinnBet = loadPinnBet;
        this.db = Db.getInstance();
        this.driver = driver;
        this.teamNamesRepository = repo;
    }

    @RequestMapping(value = "/bet365", method = RequestMethod.GET)
    public String start(){
        return "bet365";
    }

    @RequestMapping(value = "/startBet365", method = RequestMethod.POST)
    public ModelAndView printMatches(@RequestParam("country") String country,
                                     @RequestParam("league") String league,
                                     @RequestParam("url") String bet365)
            throws SQLException, IOException, CloneNotSupportedException {
        ModelAndView map = new ModelAndView("printMatches");
        //setParams(country, league);
        setParamsJDBCTemplate(country, league);
        List<String> list = compare(bet365);

        map.addObject("list", list);
        map.addObject("country", country);
        map.addObject("league", league);
        return map;
    }

    private void setParams(String country, String league) throws SQLException {
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        loadBet365.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Bet365.toString()));
        loadPinnBet.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }
    private void setParamsJDBCTemplate(String country, String league){
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        loadBet365.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Bet365.toString()));
        loadPinnBet.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }

    private List<String> compare(String bet365) throws IOException, CloneNotSupportedException {
        List<Match> utakmiceBet365;
        List<Match> utakmicePinnBet;
        utakmiceBet365 = loadBet365.load(bet365);
        utakmicePinnBet = loadPinnBet.load("https://www.pinnbet.com/");

        return new ArrayList<String>(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet,
                utakmiceBet365, BettingPlaceNames.Bet365.toString()));
    }
}
