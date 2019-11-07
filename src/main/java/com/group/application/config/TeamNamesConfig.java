package com.group.application.config;

import com.group.application.teamnames.*;
import com.group.application.utils.BettingPlaceNames;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
public class TeamNamesConfig {

    private TeamNamesFactory teamNamesFactory = new TeamNamesFactory();


    @Bean
    TeamNames888Sport getTeamNames888Sport(){
        return (TeamNames888Sport) teamNamesFactory.getTeamNames(BettingPlaceNames.Sport888.toString());
    }
    @Bean
    TeamNamesBet365 getTeamNamesBet365(){
        return (TeamNamesBet365) teamNamesFactory.getTeamNames(BettingPlaceNames.Bet365.toString());
    }
    @Bean
    TeamNamesMozzart getTeamNamesMozzart(){
        return (TeamNamesMozzart)  teamNamesFactory.getTeamNames(BettingPlaceNames.Mozzart.toString());
    }
    @Bean
    TeamNamesMeridian getTeamNamesMeridian(){
        return (TeamNamesMeridian) teamNamesFactory.getTeamNames(BettingPlaceNames.Meridian.toString());
    }
    @Bean TeamNamesPinnBet getTeamNamesPinnbet(){
        return (TeamNamesPinnBet) teamNamesFactory.getTeamNames(BettingPlaceNames.Pinnbet.toString());
    }
}
