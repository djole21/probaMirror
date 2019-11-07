package com.group.application.teamnames;

import com.group.application.utils.BettingPlaceNames;
import com.group.application.utils.Db;

import java.sql.SQLException;
import java.util.HashMap;

public class TeamNamesPinnBet implements ITeamNames {
    private HashMap<String, String> teams;
    @Override
    public void loadTeams(Db db, String league) throws SQLException {
        try {
            setTeams(db.loadTeamsForLeague(league, BettingPlaceNames.Pinnbet.toString()));
        }catch (SQLException e){
            System.out.println("greska pri ucitavanju lige: ("+league+") u pinnbet kladionici.");
            System.out.println(e.getMessage());
            throw new SQLException();
        }
    }

    @Override
    public HashMap<String, String> getTeams() {
        return this.teams;
    }

    @Override
    public void setTeams(HashMap<String, String> teams) {
        this.teams = teams;
    }


}
