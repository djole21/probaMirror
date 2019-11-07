package com.group.application.teamnames;

import com.group.application.utils.Db;

import java.sql.SQLException;
import java.util.HashMap;

public interface ITeamNames {
    HashMap<String, String> teams = null;

    public void loadTeams(Db db, String league) throws SQLException;

    public HashMap<String, String> getTeams();
    public void setTeams(HashMap<String, String> teams);

}
