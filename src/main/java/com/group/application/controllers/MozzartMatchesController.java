package com.group.application.controllers;

import com.group.application.config.OddLoadConfig;
import com.group.application.dao.TeamNamesRepository;
import com.group.application.match.Match;
import com.group.application.oddload.ILoad;
import com.group.application.oddload.LoadMozzart;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MozzartMatchesController {


    private Db db;
    private OddLoadConfig oddLoadConfig;
    private LoadMozzart loadMozzart;
    private LoadPinnBet loadPinnBet;
    private WebDriver driver;
    private TeamNamesRepository teamNamesRepository;

    @Autowired
    public MozzartMatchesController(OddLoadConfig oddLoadConfig, LoadMozzart loadMozzart,
                                    LoadPinnBet loadPinnBet, WebDriver driver, TeamNamesRepository repo) {
        this.db = Db.getInstance();
        this.oddLoadConfig = oddLoadConfig;
        this.loadMozzart = loadMozzart;
        this.loadPinnBet = loadPinnBet;
        this.driver = driver;
        this.teamNamesRepository = repo;
    }

    @RequestMapping(value = "/mozzart", method = RequestMethod.GET)
    public String start(){
        return "mozzart";
    }


    @RequestMapping(value = "/startMozzart", method = RequestMethod.GET)
    public ModelAndView printMatches(@RequestParam("country") String country,
                                     @RequestParam("league") String league,
                                     @RequestParam("url") String mozzart)
            throws IOException, CloneNotSupportedException, SQLException {
        ModelAndView map = new ModelAndView("printMatches");
        //setParameters(country, league);
        setParametersJDBCTemplate(country, league);
        List<String> list = compare(mozzart);

        map.addObject("list", list);
        map.addObject("country", country);
        map.addObject("league", league);
        return map;
    }

    private void setParameters(String country, String league) throws SQLException {
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        loadMozzart.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Mozzart.toString()));
        loadPinnBet.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }

    private void setParametersJDBCTemplate(String country, String league){
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        loadMozzart.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Mozzart.toString()));
        loadPinnBet.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }


    private List<String> compare(String mozzart) throws IOException, CloneNotSupportedException {
        List<Match> utakmiceMozzart;
        List<Match> utakmicePinnBet;

        utakmicePinnBet = loadPinnBet.load("https://www.pinnbet.com/");
        utakmiceMozzart = loadMozzart.load(mozzart);

        return new ArrayList<String>(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet,
                utakmiceMozzart, BettingPlaceNames.Mozzart.toString()));
    }
}
