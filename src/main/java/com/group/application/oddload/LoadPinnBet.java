package com.group.application.oddload;

import com.group.application.match.Match;
import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.odds.GetOddFactory;
import com.group.application.odds.Odd;
import com.group.application.pomselenium.IPage;
import com.group.application.pomselenium.PinnBetPage;
import com.group.application.teamnames.ITeamNames;
import com.group.application.utils.BettingPlaceNames;
import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadPinnBet implements ILoad, ApplicationContextAware {

    private PinnBetPage pinnBetPage;
    private HashMap<String, String> timovi;
    private GetOddFactory factory;
    private INameFactory nameFactory;
    private String country;
    private String league;
    private ApplicationContext context;

    public LoadPinnBet(){
        factory = new GetOddFactory();
        nameFactory = NameFactory.getFactory(BettingPlaceNames.Pinnbet.toString());
    }

    public LoadPinnBet(IPage pinnBetPage, ITeamNames timovi, String country, String league) {
        this.pinnBetPage = (PinnBetPage)pinnBetPage;
        this.timovi = timovi.getTeams();
        this.factory = new GetOddFactory();
        //this.nameFactory = NameFactory.getFactory(BettingPlaceNames.Pinnbet.toString());
        this.country = country;
        this.league = league;
    }

    public String getCountry() {
        return country;
    }

    public String getLeague() {
        return league;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public void setPinnBetPage(PinnBetPage pinnBetPage) {
        this.pinnBetPage = pinnBetPage;
    }

    public void setTimovi(HashMap<String, String> timovi) {
        this.timovi = timovi;
    }

    public List<Match> load(String url) {
        nameFactory = context.getBean("PinnbetNameFactory", INameFactory.class);
        List<Match> utakmice = new ArrayList<Match>();
        pinnBetPage.goToPage(url);
        loadLeague();
        int numberOfDays = pinnBetPage.getNumberOfDays();
        for(int ii = 1; ii <= numberOfDays; ii++){
            pinnBetPage.waitForPageToLoad(ii);
            utakmice.addAll(iterateThroughMatches(ii));
        }

        return utakmice;
    }

    private void loadLeague(){
        //pinnBetPage.closeAndGoToPage();
        pinnBetPage.waitForMenuToLoad();
        for(int i = 1; i < 5; i++){
            String sport = pinnBetPage.getSportName(i);
            if(sport.equalsIgnoreCase("fudbal")){
                findCountry(i);
            }
        }
    }

    private void findCountry(int i){
        int subMenuSize = pinnBetPage.getSubMenuSize(i);
        for(int j = 1; j <= subMenuSize; j++){
            if(pinnBetPage.isThereCountries(i, j)) {
                String countryName = pinnBetPage.getCountryName(i, j);
                if (countryName.equalsIgnoreCase(country)) {
                    findLeague(i, j);
                }
            }
        }
    }

    private void findLeague(int i, int j){
        int leaguesSize = pinnBetPage.getLeaguesSize(i, j);
        for(int k = 1; k <= leaguesSize; k++){
            String leagueName = pinnBetPage.getLeagueName(i, j, k);
            if(leagueName.equalsIgnoreCase(league)){
                pinnBetPage.clickOnLeague(i, j, k);
            }
        }
    }

    private List<Match> iterateThroughMatches(int day){
        List<Match> matches = new ArrayList<Match>();
        int numberOfMatches = pinnBetPage.getNumberOfMatchesForGivenDay(day);

        for(int i = 1; i <= numberOfMatches; i++){
            String homeTeam = timovi.get(pinnBetPage.getHomeTeamName(day, i));
            String awayTeam = timovi.get(pinnBetPage.getAwayTeamName(day, i));

            Match m = new Match(homeTeam, awayTeam);
            m.setKvote(openMatch(day, i));

            matches.add(m);
            pinnBetPage.goBack();
        }
        return matches;
    }

    private HashMap<String, Odd> openMatch(int day, int i){
        pinnBetPage.goToMatch(day, i);
        pinnBetPage.waitForMatchPageToLoad();
        int numberOfOdds = pinnBetPage.getNumberOfOdds();

        return iterateThroughOdds(numberOfOdds);
    }

    private HashMap<String, Odd> iterateThroughOdds(int numberOfOdds){
        HashMap<String, Odd> kvote = new HashMap<String, Odd>();
        int numberOfIterations = Math.min(numberOfOdds, 4);

        for(int j = 1; j <= numberOfIterations; j++){

            int numberOfOddFields = pinnBetPage.getNumberOfOptionsForOdd(j);
            pinnBetPage.waitForOdds(j);

            String gameName = nameFactory.getGameName(StringUtils.strip(pinnBetPage.getGameName(j)));

            Odd odd = createOdd(numberOfOddFields,gameName,j);
            if(odd != null){
                kvote.put(gameName, odd);
            }

        }
        return kvote;
    }

    private Odd createOdd(int numberOfFields, String gameName, int j){
        Odd odd = factory.getOdd(gameName);
        if(odd != null){
            for(int k = 2; k <= numberOfFields; k++){
                String oddName = pinnBetPage.getOddName(j, k);
                String oddValue = pinnBetPage.getOddValue(j, k);
                if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                    oddName = StringUtils.strip(oddName);
                    if ( gameName.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
                        oddName = nameFactory.getGameNameOption(gameName, oddName);
                    }
                    if(NumberUtils.isCreatable(oddValue)) {
                        odd.setParameter(oddName, Double.parseDouble(oddValue));
                    }
                }
            }
        }
        return odd;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
