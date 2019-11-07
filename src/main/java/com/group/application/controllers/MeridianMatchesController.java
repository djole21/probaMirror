package com.group.application.controllers;

import com.group.application.config.OddLoadConfig;
import com.group.application.dao.TeamNamesRepository;
import com.group.application.match.Match;
import com.group.application.oddload.ILoad;
import com.group.application.oddload.LoadMeridian;
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
public class MeridianMatchesController {

    private OddLoadConfig oddLoadConfig;
    private LoadMeridian loadMeridian;
    private LoadPinnBet loadPinnBet;
    private Db db;
    private WebDriver driver;
    private TeamNamesRepository teamNamesRepository;

    @Autowired
    public MeridianMatchesController(OddLoadConfig oddLoadConfig, LoadMeridian loadMeridian,
                                     LoadPinnBet loadPinnBet, WebDriver driver, TeamNamesRepository repo) {

        this.oddLoadConfig = oddLoadConfig;
        this.loadMeridian = loadMeridian;
        this.loadPinnBet = loadPinnBet;
        this.db = Db.getInstance();
        this.driver = driver;
        this.teamNamesRepository = repo;
    }

    @RequestMapping(value = "/meridian", method = RequestMethod.GET)
    public String start(){
        return "meridian";
    }


    @RequestMapping(value = "/startMeridian", method = RequestMethod.GET)
    public ModelAndView printMatches(@RequestParam("country") String country,
                                     @RequestParam("league") String league,
                                     @RequestParam("url") String meridian
    ) throws IOException, CloneNotSupportedException, SQLException {
        ModelAndView map = new ModelAndView("printMatches");
        //setParameters(country, league);
        setParametersJDBCTemplate(country, league);
        List<String> list = compare(meridian);

        map.addObject("list", list);
        map.addObject("country", country);
        map.addObject("league", league);
        return map;
    }

    private void setParameters(String country, String league) throws SQLException {
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        loadMeridian.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Meridian.toString()));
        loadPinnBet.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }

    private void setParametersJDBCTemplate(String country, String league){
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        loadMeridian.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Meridian.toString()));
        loadPinnBet.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }


    private List<String> compare(String meridian) throws IOException, CloneNotSupportedException {
        List<Match> utakmiceMeridian;
        List<Match> utakmicePinnBet;

        utakmiceMeridian = loadMeridian.load(meridian);
        utakmicePinnBet = loadPinnBet.load("https://www.pinnbet.com/");

        return new ArrayList<String>(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet,
                utakmiceMeridian, BettingPlaceNames.Meridian.toString()));
    }

}
