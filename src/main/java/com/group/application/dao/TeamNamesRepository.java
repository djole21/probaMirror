package com.group.application.dao;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;

@Repository
public class TeamNamesRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private int getLeagueIdByName(String name){
        return jdbcTemplate.queryForObject(
                "select id from league where name like ?",
                new Object[]{name}, Integer.class
        );
    }

    private int getBettingPlaceIdByName(String name){
        return jdbcTemplate.queryForObject(
                "select id from bettingPlace where name like ?",
                new Object[]{name},
                Integer.class
        );
    }

    public HashMap<String, String> loadTeamsForLeague(String leagueName, String bettingPlace){
        int leagueId = getLeagueIdByName(leagueName);
        int bettingPlaceId = getBettingPlaceIdByName(bettingPlace);
        return jdbcTemplate.query(
                "SELECT team.name as osnNaziv, teamNameBettingPlace.name as kladNaziv "  +
                        "FROM team join teamNameBettingPlace on team.id = teamNameBettingPlace.idTeam "  +
                        "where team.IdLeague = ? and IdKlad = ?",
                new Object[]{leagueId, bettingPlaceId},
                (ResultSet rs) ->{
                    HashMap<String, String> map = new HashMap<>();
                    while ((rs.next())){
                        map.put(rs.getString("kladNaziv"),rs.getString("osnNaziv"));
                    }
                    return map;
                }



        );
    }
}
