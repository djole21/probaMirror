package com.group.application.oddload;

import com.group.application.match.Match;
import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.odds.GetOddFactory;
import com.group.application.odds.Odd;
import com.group.application.pomselenium.IPage;
import com.group.application.pomselenium.Sport888Page;
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

public class Load888Sport implements ILoad, ApplicationContextAware {

    private Sport888Page sport888Page;
    private HashMap<String, String> timovi;
    private GetOddFactory factory;
    private INameFactory nameFactory;
    private ApplicationContext context;
    private String home;
    private String away;

    public Load888Sport() {
        factory = new GetOddFactory();
        nameFactory = NameFactory.getFactory(BettingPlaceNames.Sport888.toString());
    }

    public Load888Sport(IPage sport888Page, ITeamNames timovi) {
        this.sport888Page = (Sport888Page) sport888Page;
        this.timovi = timovi.getTeams();
        factory = new GetOddFactory();

        //nameFactory = NameFactory.getFactory(BettingPlaceNames.Sport888.toString());
    }


    public void setSport888Page(Sport888Page sport888Page) {
        this.sport888Page = sport888Page;
    }

    public void setTimovi(HashMap<String, String> timovi) {
        this.timovi = timovi;
    }

    public List<Match> load(String url) {
        nameFactory = context.getBean("888SportNameFactory", INameFactory.class);
        sport888Page.goToPage(url);
        sport888Page.waitForPageToLoad();
        int numberOfDays = sport888Page.getNumberOfDays();

        return new ArrayList<Match>(iterateThroughAllDays(numberOfDays));
    }

    private List<Match> iterateThroughAllDays(int numberOfDays){
        List<Match> utakmice = new ArrayList<Match>();
        for(int i = 1; i <= numberOfDays; i++){
            if(i > 1) {
                sport888Page.openNewDayContainer(i);
            }
            utakmice.addAll(iterateThroughGamesForGivenDay(i));
        }
        return utakmice;
    }

    private List<Match> iterateThroughGamesForGivenDay(int i){
        List<Match> utakmice = new ArrayList<Match>();
        int numberOfGames = sport888Page.getNumberOfMatchesForGivenDay(i);
        for(int j = 1; j <= numberOfGames; j++){
            sport888Page.goToMatch(i, j);
            sport888Page.waitForMatchPageToLoad();
            String homeTeam = timovi.get(sport888Page.getHomeTeamName());
            String awayTeam = timovi.get(sport888Page.getAwayTeamName());
            home = sport888Page.getHomeTeamName();
            away = sport888Page.getAwayTeamName();
            Match m = new Match(homeTeam, awayTeam);
            m.setKvote(iterateThroughAllOdds());

            utakmice.add(m);

        }
        return utakmice;
    }

    private HashMap<String, Odd> iterateThroughAllOdds(){
        HashMap<String, Odd> kvote = new HashMap<String, Odd>();
        Odd odd;
        sport888Page.waitForAllOddsToLoad();
        int numberOfOdds = sport888Page.getNumberOfOdds();
        if(numberOfOdds >= 3)
            numberOfOdds = 5;
        for(int k = 1; k <= numberOfOdds; k++){
            String gameName = nameFactory.getGameName(sport888Page.getGameName(k));
            odd = factory.getOdd(gameName);
            if(odd != null) {
                odd = setOddParams(k, gameName, odd);
                kvote.put(gameName, odd);
            }
        }
        return kvote;
    }

    private Odd setOddParams(int k, String gameName, Odd baseOdd){
        Odd o = null;
        if(gameName.equalsIgnoreCase(OddNames.FullTime.toString())){
            o = setParamsFinalOutcome(k, baseOdd);
        } else if(gameName.equalsIgnoreCase(OddNames.TotalGoals.toString())){
            o = setParamsTotalGoals(k, baseOdd);
        } else if(gameName.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())){
            o = setParamsBothTeamsToScore(k, baseOdd);
        }
        return o;
    }

    private Odd setParamsFinalOutcome(int k, Odd odd){
        Odd odd1 = odd;
        int numberOfParams = sport888Page.getNumberOfOptionsForOdd(k);
        for(int l = 1; l <= numberOfParams; l++){
            String oddName = getFinalOutcomeOddName(sport888Page.getOddName(k, l));
            String oddValue = sport888Page.getOddValue(k, l);
            if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                if(NumberUtils.isCreatable(oddValue)) {
                    odd1.setParameter(oddName, Double.parseDouble(oddValue));
                }
            }
        }
        return odd1;
    }

    private String getFinalOutcomeOddName(String odd){
        String oddName = "";
        if(odd.equalsIgnoreCase(home)){
            oddName = "1";
        }else if(odd.equalsIgnoreCase(away)){
            oddName = "2";
        } else if(odd.equalsIgnoreCase("Draw")){
            oddName = "x";
        }
        return oddName;
    }

    private Odd setParamsTotalGoals(int k, Odd odd){
        Odd o = odd;
        sport888Page.clickAndWaitToShowAllOverUnderOdds(k);
        sport888Page.setAllOUOdds(k);
        int numberOfColumns = sport888Page.getNumberOfColumnsForOUOdds(k);
        for(int ii = 1; ii <= numberOfColumns; ii++){
            int numberOfOUOdds = sport888Page.getNumberOfOUOddsInSingleColumn(k, ii);
            for(int jj = 1; jj <= numberOfOUOdds; jj++){
                String oddName = nameFactory.getGameNameOption(OddNames.TotalGoals.toString(),
                        sport888Page.getTotalGoalsOverUnderOddName(k, ii, jj));
                String oddValue = sport888Page.getTotalGoalsOverUnderOddValue(k, ii, jj);

                if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                    if(NumberUtils.isCreatable(oddValue)) {
                        o.setParameter(oddName, Double.parseDouble(oddValue));
                    }
                }
            }
        }
        return o;
    }

    private Odd setParamsBothTeamsToScore(int k, Odd odd){
        Odd odd1 = odd;
        int numberOfParams = sport888Page.getNumberOfOptionsForOdd(k);
        for(int l = 1; l <= numberOfParams; l++){
            String oddName = nameFactory.getGameNameOption(OddNames.BothTeamsToScore.toString(),
                    sport888Page.getOddName(k, l));
            String oddValue = sport888Page.getOddValue(k, l);

            if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                if(NumberUtils.isCreatable(oddValue)) {
                    odd1.setParameter(oddName, Double.parseDouble(oddValue));
                }
            }
        }
        return odd1;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context =  applicationContext;
    }
}
