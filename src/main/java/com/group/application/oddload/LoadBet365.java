package com.group.application.oddload;

import com.group.application.match.Match;
import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.odds.GetOddFactory;
import com.group.application.odds.Odd;
import com.group.application.pomselenium.Bet365Page;
import com.group.application.pomselenium.IPage;
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

public class LoadBet365 implements ILoad, ApplicationContextAware {

    private HashMap<String, String> timovi;
    private GetOddFactory factory;
    private INameFactory nameFactory;
    private Bet365Page bet365Page;
    private String home;
    private String away;
    private ApplicationContext context;

    public LoadBet365() {
        factory = new GetOddFactory();
        nameFactory = NameFactory.getFactory(BettingPlaceNames.Bet365.toString());
    }

    public LoadBet365(IPage bet365Page, ITeamNames timovi) {
        this.timovi = timovi.getTeams();
        this.bet365Page = (Bet365Page)bet365Page;
        factory = new GetOddFactory();
        //nameFactory = NameFactory.getFactory(BettingPlaceNames.Bet365.toString());
    }

    public void setTimovi(HashMap<String, String> timovi) {
        this.timovi = timovi;
    }

    public void setBet365Page(Bet365Page bet365Page) {
        this.bet365Page = bet365Page;
    }

    @Override
    public List<Match> load(String url) {

        nameFactory = context.getBean("Bet365NameFactory", INameFactory.class);
        bet365Page.goToPage(url);
        openPage();
        bet365Page.goToPage(url);

        bet365Page.waitForPageToLoad();
        int numberOfMatches = bet365Page.getNumberOfMatches();
        return iterateThroughAllGames(numberOfMatches);
    }

    private void openPage(){
        bet365Page.closeAndGoToPage();
        bet365Page.closeAndGoToPage();
    }

    private List<Match> iterateThroughAllGames(int numberOfMatches){
        List<Match> utakmice = new ArrayList<Match>();

        for(int i = 1; i <= numberOfMatches; i++){
            bet365Page.waitForPageToLoad();
            String[] homeAndAway = StringUtils.splitByWholeSeparator(bet365Page.getHomeAndAwayTeamName(i), " v ");
            if(homeAndAway.length == 2) {
                String homeTeam = timovi.get(homeAndAway[0]);
                String awayTeam = timovi.get(homeAndAway[1]);
                home = homeAndAway[0];
                away = homeAndAway[1];
                Match m = new Match(homeTeam, awayTeam);
                goToMatch(i);
                int numberOfOdds = bet365Page.getNumberOfOdds();

                m.setKvote(iterateThroughAllOdds(numberOfOdds));
                utakmice.add(m);
            }
            bet365Page.goBack();
        }
        return utakmice;
    }

    private HashMap<String, Odd> iterateThroughAllOdds(int numberOfOdds){
        HashMap<String, Odd> kvote = new HashMap<String, Odd>();

        for(int j = 1; j <= numberOfOdds; j++){
            String gameName = nameFactory.getGameName(bet365Page.getGameName(j));
            if(StringUtils.isNotEmpty(gameName)) {
                int numberOfOddFields;
                if (gameName.equalsIgnoreCase(OddNames.TotalGoals.toString())) {
                    Odd odd = createOverUnderOdd(gameName, j);
                    if (odd != null) {
                        kvote.put(gameName, odd);
                    }
                } else {
                    numberOfOddFields = bet365Page.getNumberOfOptionsForOdd(j);
                    Odd odd = createOdd(gameName, j, numberOfOddFields);
                    if (odd != null) {
                        kvote.put(gameName, odd);
                    }
                }
            }
        }
        return kvote;
    }

    private Odd createOverUnderOdd(String gameName, int j){
        Odd odd = factory.getOdd(gameName);
        String margin = bet365Page.getTotalGoalsOverUnderMargin(j);
        for(int k = 1; k <= 2; k++){
            String oddName = nameFactory.getGameNameOption(gameName,
                    bet365Page.getTotalGoalsOverUnderOddName(j, k));
            String oddValue = bet365Page.getTotalGoalsOverUnderOddValue(j, k);

            if(StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)){
                if(NumberUtils.isCreatable(oddValue)) {
                    odd.setParameter(oddName, Double.parseDouble(oddValue));
                }
            }

        }
        return odd;
    }

    private Odd createOdd(String gameName, int j, int numberOfOddFields){
        Odd odd = factory.getOdd(gameName);
        if(odd != null) {
            for (int k = 1; k <= numberOfOddFields; k++) {
                String oddName;
                if (gameName.equalsIgnoreCase(OddNames.FullTime.toString())) {
                    oddName = setOddNameForFinalOutcome(bet365Page.getOddName(j, k));
                } else {
                    oddName = nameFactory.getGameNameOption(gameName, bet365Page.getOddName(j, k));
                }
                String oddValue = bet365Page.getOddValue(j, k);

                if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                    if (NumberUtils.isCreatable(oddValue)) {
                        odd.setParameter(oddName, Double.parseDouble(oddValue));
                    }
                }
            }
        }
        return odd;
    }

    private String setOddNameForFinalOutcome(String name){
        if(name.equalsIgnoreCase(home)) {
            return "1";
        }else if(name.equalsIgnoreCase(away)) {
            return "2";
        }else if(name.equalsIgnoreCase("Draw")) {
            return "X";
        }
        return "";
    }

    private void goToMatch(int i){
        bet365Page.goToMatch(i);
        bet365Page.waitForMatchPageToLoad();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
