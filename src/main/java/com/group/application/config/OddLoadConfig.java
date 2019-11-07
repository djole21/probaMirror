package com.group.application.config;

import com.group.application.config.PageObjectConfig;
import com.group.application.config.TeamNamesConfig;
import com.group.application.oddload.*;
import com.group.application.pomselenium.*;
import com.group.application.teamnames.*;
import com.group.application.utils.BettingPlaceNames;
import com.group.application.utils.Db;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

import java.sql.SQLException;

@Lazy
@Configuration
@Import({TeamNamesConfig.class, PageObjectConfig.class})
public class OddLoadConfig {

    private OddLoadFactory oddLoadFactory;
    private MozzartPage mozzartPage;
    private Sport888Page sport888Page;
    private MeridianPage meridianPage;
    private Bet365Page bet365Page;
    private PinnBetPage pinnBetPage;
    private TeamNames888Sport teamNames888Sport;
    private TeamNamesMozzart teamNamesMozzart;
    private TeamNamesMeridian teamNamesMeridian;
    private TeamNamesPinnBet teamNamesPinnBet;
    private TeamNamesBet365 teamNamesBet365;
    private String league;
    private String country;

    @Autowired
    public OddLoadConfig(MozzartPage mozzartPage,TeamNamesBet365 teamNamesBet365,
                         Sport888Page sport888Page, MeridianPage meridianPage,
                         Bet365Page bet365Page, PinnBetPage pinnBetPage,
                         TeamNames888Sport teamNames888Sport, TeamNamesMozzart teamNamesMozzart,
                         TeamNamesMeridian teamNamesMeridian, TeamNamesPinnBet teamNamesPinnBet) {
        this.oddLoadFactory = new OddLoadFactory();
        this.mozzartPage = mozzartPage;
        this.sport888Page = sport888Page;
        this.meridianPage = meridianPage;
        this.bet365Page = bet365Page;
        this.pinnBetPage = pinnBetPage;
        this.teamNames888Sport = teamNames888Sport;
        this.teamNamesMozzart = teamNamesMozzart;
        this.teamNamesMeridian = teamNamesMeridian;
        this.teamNamesPinnBet = teamNamesPinnBet;
        this.teamNamesBet365 = teamNamesBet365;
    }


    public void setLeague(String league) {
        this.league = league;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Bean
    public Load888Sport getLoad888sport() {
        return (Load888Sport)oddLoadFactory.getLoading(BettingPlaceNames.Sport888.toString(),
                sport888Page, teamNames888Sport,  country, league);
    }
    @Bean
    public LoadMozzart getLoadMozzart() {
        return (LoadMozzart)oddLoadFactory.getLoading(BettingPlaceNames.Mozzart.toString(),
                mozzartPage, teamNamesMozzart,  country, league);
    }
    @Bean
    public LoadMeridian getLoadMeridian() {
        return (LoadMeridian) oddLoadFactory.getLoading(BettingPlaceNames.Meridian.toString(),
                meridianPage, teamNamesMeridian,  country, league);
    }
    @Bean
    public LoadPinnBet getLoadPinnbet() {
        return (LoadPinnBet) oddLoadFactory.getLoading(BettingPlaceNames.Pinnbet.toString(),
                pinnBetPage, teamNamesPinnBet,  country, league);
    }
    @Bean
    public LoadBet365 getLoadBet365() {
        return (LoadBet365) oddLoadFactory.getLoading(BettingPlaceNames.Bet365.toString(),
                bet365Page, teamNamesBet365,  country, league);
    }

}
