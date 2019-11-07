package com.group.application.utils;

import java.sql.*;
import java.util.HashMap;

public class Db {

    private static Db instance;

    public static Db getInstance() {
        if(instance == null)
            instance = new Db();
        return instance;
    }

    public Db()  {

    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost/odds?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "djole", "mitrovic01");
    }

    private int getLeagueIdByName(String leagueName) throws SQLException {
        Connection conn = null;
        int leagueId = 0;
        PreparedStatement statement = null;
        ResultSet rs = null;

        conn = getConnection();
        try {
            conn = getConnection();
            statement = conn.prepareStatement("select id from league where name like ?");

            statement.setString(1, leagueName);
            rs = statement.executeQuery();
            while(rs.next()){
                leagueId = rs.getInt("id");
            }
        }catch (SQLException e){
            throw new SQLException();
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
        return leagueId;
    }

    private int getBettingPlaceIdByName(String bettingPlaceName) throws SQLException {
        Connection conn = null;
        ResultSet rs = null;
        int bettingPlaceId = 0;
        PreparedStatement statement = null;

        try {
            conn = getConnection();
            statement = conn.prepareStatement("select id from bettingPlace where name like ?");
            statement.setString(1, bettingPlaceName);

            rs = statement.executeQuery();
            while(rs.next()){
                bettingPlaceId = rs.getInt("id");
            }
        }catch (SQLException e) {
            throw new SQLException();
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
        return bettingPlaceId;
    }

    public HashMap<String, String> loadTeamsForLeague(String leagueName, String bettingPlaceName) throws SQLException {
        Connection conn = null;

        HashMap<String, String> timovi = new HashMap<String, String>();
        ResultSet rs = null;

        int bettingPlaceId = 0;
        int leagueId = 0;
        PreparedStatement statement = null;

        try {
            conn = getConnection();

            leagueId = getLeagueIdByName(leagueName);
            bettingPlaceId = getBettingPlaceIdByName(bettingPlaceName);

            if(bettingPlaceId != 0 && leagueId != 0){
                statement = conn.prepareStatement("SELECT team.name as osnNaziv, teamNameBettingPlace.name as kladNaziv " +
                        "FROM team join teamNameBettingPlace on team.id = teamNameBettingPlace.idTeam " +
                        "where team.IdLeague = ? and IdKlad = ?");
                statement.setInt(1, leagueId);
                statement.setInt(2, bettingPlaceId);

                rs = statement.executeQuery();

                while(rs.next()){
                    timovi.put(rs.getString("kladNaziv"),rs.getString("osnNaziv"));
                }
            }
        }catch (SQLException e) {
            throw new SQLException();
        }finally {
            if(rs != null) {
                rs.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(conn != null) {
                conn.close();
            }
        }
        return timovi;
    }


}
