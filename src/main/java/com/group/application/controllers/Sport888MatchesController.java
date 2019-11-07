package com.group.application.controllers;

import com.group.application.config.OddLoadConfig;
import com.group.application.dao.TeamNamesRepository;
import com.group.application.match.Match;
import com.group.application.oddload.ILoad;
import com.group.application.oddload.Load888Sport;
import com.group.application.oddload.LoadPinnBet;
import com.group.application.utils.BettingPlaceNames;
import com.group.application.utils.Db;
import com.group.application.utils.MatchPrinting;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class Sport888MatchesController {

    private OddLoadConfig oddLoadConfig;
    private LoadPinnBet loadPinnBet;
    private Load888Sport load888Sport;
    private WebDriver driver;
    private Db db;
    private TeamNamesRepository teamNamesRepository;

    @Autowired
    public Sport888MatchesController(OddLoadConfig oddLoadConfig, LoadPinnBet loadPinnBet,
                                     Load888Sport load888Sport, WebDriver driver, TeamNamesRepository repo) {
        this.oddLoadConfig = oddLoadConfig;
        this.loadPinnBet = loadPinnBet;
        this.load888Sport = load888Sport;
        this.driver = driver;
        this.db = Db.getInstance();
        this.teamNamesRepository = repo;
    }

    @RequestMapping(value = "/sport888", method = RequestMethod.GET)
    public String start(){
        return "sport888";
    }

    @RequestMapping(value = "/start888Sport", method = RequestMethod.POST)
    public ModelAndView printMatches(@RequestParam("country") String country,
                                     @RequestParam("league") String league,
                                     @RequestParam("url") String sport888)
            throws SQLException, IOException, CloneNotSupportedException {
        ModelAndView map = new ModelAndView("printMatches");
        //setParameters(country, league);
        setParametersJDBCTemplate(country, league);
        List<String> list = compare(sport888);

        map.addObject("list", list);
        map.addObject("country", country);
        map.addObject("league", league);
        return map;
    }

    private void setParameters(String country, String league) throws SQLException {
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        load888Sport.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Sport888.toString()));
        loadPinnBet.setTimovi(db.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }

    private void setParametersJDBCTemplate(String country, String league){
        oddLoadConfig.setCountry(country);
        oddLoadConfig.setLeague(league);
        load888Sport.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Sport888.toString()));
        loadPinnBet.setTimovi(teamNamesRepository.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        loadPinnBet.setCountry(country);
        loadPinnBet.setLeague(league);
    }

    private List<String> compare(String sport888) throws IOException, CloneNotSupportedException {
        List<Match> utakmice888Sport;
        List<Match> utakmicePinnBet;

        utakmice888Sport = load888Sport.load(sport888);
        utakmicePinnBet = loadPinnBet.load("https://www.pinnbet.com/");

        return new ArrayList<String>(MatchPrinting.compareMatchesAndPrintOdds(utakmicePinnBet,
                utakmice888Sport, BettingPlaceNames.Sport888.toString()));
    }

}
