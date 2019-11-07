package com.group.application.oddload;

import com.group.application.match.Match;
import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.odds.GetOddFactory;
import com.group.application.odds.Odd;
import com.group.application.pomselenium.IPage;
import com.group.application.pomselenium.MeridianPage;
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

public class LoadMeridian implements ILoad, ApplicationContextAware {

    private MeridianPage meridianPage;
    private HashMap<String, String> timovi;
    private GetOddFactory factory;
    private INameFactory nameFactory;
    private ApplicationContext context;


    public LoadMeridian() {
        factory = new GetOddFactory();
        nameFactory = NameFactory.getFactory(BettingPlaceNames.Meridian.toString());
    }

    public LoadMeridian(IPage meridianPage, ITeamNames timovi) {
        this.meridianPage = (MeridianPage)meridianPage;
        this.timovi = timovi.getTeams();
        factory = new GetOddFactory();
//        nameFactory = NameFactory.getFactory(BettingPlaceNames.Meridian.toString());
    }

    public void setMeridianPage(MeridianPage meridianPage) {
        this.meridianPage = meridianPage;
    }

    public void setTimovi(HashMap<String, String> timovi) {
        this.timovi = timovi;
    }

    public List<Match> load(String url) {
        List<Match> utakmice;
        nameFactory = context.getBean("MeridianNameFactory", INameFactory.class);
        meridianPage.goToPage(url);
        meridianPage.waitForPageToLoad();
        int numberOfGames = meridianPage.getNumberOfMatches();
        utakmice = iterateThroughAllMatches(numberOfGames, url);


        return utakmice;
    }

    private List<Match> iterateThroughAllMatches(int numberOfGames, String url){
        List<Match> utakmice = new ArrayList<Match>();
        for(int i = 1; i <= numberOfGames; i++){
            meridianPage.waitForPageToLoad();
            meridianPage.reloadMatches();
            Match m;
            try {
                String homeTeam = timovi.get(meridianPage.getHomeTeamName(i));
                String awayTeam = timovi.get(meridianPage.getAwayTeamName(i));
                m = new Match(homeTeam, awayTeam);
            } catch (Exception e){
                break;
            }
            m.setKvote(iterateThroughAllOdds(i));
            utakmice.add(m);
            meridianPage.navigateToUrl(url);

        }
        return utakmice;
    }

    private HashMap<String, Odd> iterateThroughAllOdds(int i){
        HashMap<String, Odd> kvote = new HashMap<String, Odd>();
        meridianPage.goToMatch(i);
        meridianPage.waitForMatchPageToLoad();
        int numberOfOdds = Math.min(meridianPage.getNumberOfOdds(), 5);
        Odd odd;
        for(int j = 1; j <= numberOfOdds; j++){
            String gameName = nameFactory.getGameName(meridianPage.getGameName(j));
            odd = checkAndSetOddIfAlreadyExist(kvote, gameName);

            if(odd != null){
                int numberOfOddFields = meridianPage.getNumberOfOptionsForOdd(j);
                for(int k = 1; k <= numberOfOddFields; k++){
                    odd = getOddNameAndValue(j, k, gameName, odd);
                    if(isEndOfOU(j, gameName)) {
                        k += numberOfOddFields;
                    }
                }
                kvote.put(gameName, odd);
            }
        }
        return kvote;
    }

    private boolean isEndOfOU(int j, String gameName) {
        if (gameName.equalsIgnoreCase(OddNames.TotalGoals.toString())) {
            return meridianPage.getTotalGoalsOverUnder(j) != 0;
        }
        return false;
    }

    private Odd getOddNameAndValue(int j, int k, String gameName, Odd odd){
        Odd o;
        if(gameName.equalsIgnoreCase(OddNames.TotalGoals.toString())){
            if(meridianPage.getTotalGoalsOverUnder(j) != 0){
                o = setOddNameAndValueForOverUnderTotalGoals(j, odd);
            } else{
                o = setOddNameAndValueTotalGoals(j, odd);
            }
        } else{
            o = setOthersOddNameAndValue(j, k, odd, gameName);
        }

        return o;
    }

    private Odd setOthersOddNameAndValue(int j, int k, Odd odd, String gameName){
        Odd o = odd;
        String oddName = meridianPage.getOddName(j, k);
        String oddValue = meridianPage.getOddValue(j, k);

        if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
            if(gameName.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
                oddName = nameFactory.getGameNameOption(gameName, oddName);
            }
            if(NumberUtils.isCreatable(oddValue)) {
                odd.setParameter(oddName, Double.parseDouble(oddValue));
            }
        }
        return o;
    }

    private Odd setOddNameAndValueForOverUnderTotalGoals(int j, Odd initOdd){
        Odd odd = initOdd;
        int numberOfOverUnderMargins = meridianPage.getTotalGoalsOverUnder(j);
        for(int kk = 1; kk <= numberOfOverUnderMargins; kk++) {
            String granica = meridianPage.getTotalGoalsOverUnderMargin(j, kk);
            for(int p = 1; p <= 2; p++){
                String oddName = meridianPage.getTotalGoalsOverUnderOddName(j, kk, p);
                String oddValue = meridianPage.getTotalGoalsOverUnderOddValue(j, kk, p);

                if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                    String n = nameFactory.getGranica(granica, oddName);

                    if (NumberUtils.isCreatable(oddValue)) {
                        odd.setParameter(n, Double.parseDouble(oddValue));
                    }
                }
            }
        }
        return odd;
    }

    private Odd setOddNameAndValueTotalGoals(int j, Odd initOdd){
        Odd odd = initOdd;
        for(int kk = 1; kk <= 2; kk++){
            String oddName= meridianPage.getTotalGoalsOddName(j, kk);
            String oddValue = meridianPage.getTotalGoalsOddValue(j, kk);

            if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                if(NumberUtils.isCreatable(oddValue)) {
                    odd.setParameter(oddName, Double.parseDouble(oddValue));
                }
            }
        }
        return odd;
    }

    private Odd checkAndSetOddIfAlreadyExist(HashMap<String, Odd> kvote, String gameName){
        Odd odd;
        if(gameName.equalsIgnoreCase(OddNames.TotalGoals.toString())) {
            if (kvote.get(OddNames.TotalGoals.toString()) == null) {
                odd = factory.getOdd(gameName);
            } else {
                odd = kvote.get(OddNames.TotalGoals.toString());
            }
        } else {
            odd = factory.getOdd(gameName);
        }
        return odd;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
