package com.group.application.oddload;

import com.group.application.match.Match;
import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.odds.GetOddFactory;
import com.group.application.odds.Odd;
import com.group.application.pomselenium.IPage;
import com.group.application.pomselenium.MozzartPage;
import com.group.application.teamnames.ITeamNames;
import com.group.application.utils.BettingPlaceNames;
import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadMozzart implements ILoad, ApplicationContextAware {

    private MozzartPage mp;
    private HashMap<String, String> timovi;
    private GetOddFactory factory;
    ITeamNames teamNames;
    private INameFactory nameFactory;
    private ApplicationContext context;


    public LoadMozzart() {
        factory = new GetOddFactory();
        nameFactory = NameFactory.getFactory(BettingPlaceNames.Mozzart.toString());
    }

    public LoadMozzart(IPage mp, ITeamNames timovi) {
        this.mp = (MozzartPage) mp;
        teamNames = timovi;
        this.timovi = teamNames.getTeams();
        factory = new GetOddFactory();
        //nameFactory = NameFactory.getFactory(BettingPlaceNames.Mozzart.toString());
    }

    public void setTeamNames(HashMap<String, String> timovi) {
        this.teamNames.setTeams(timovi);
    }

    public void setMp(MozzartPage mp) {
        this.mp = mp;
    }

    public void setTimovi(HashMap<String, String> timovi) {
        this.timovi = timovi;
    }

    public List<Match> load(String url){
        nameFactory = context.getBean("MozzartNameFactory", INameFactory.class);
        int numberOfGames;
        List<Match> utakmice;

        mp.goToPage(url);
        mp.waitForPageToLoad();

        numberOfGames = mp.getNumberOfMatches();

        utakmice = iterateThroughAllGames(numberOfGames);
        return utakmice;

    }

    private List<Match> iterateThroughAllGames(int numberOfGames){

        mp.waitOddsToLoad();
        List<Match> utakmice = new ArrayList<Match>();
        for(int i = 1; i <= numberOfGames; i++){
            String homeTeam = timovi.get(mp.getHomeTeamName(i));
            String awayTeam = timovi.get(mp.getAwayTeamName(i));

            Match m = new Match(homeTeam, awayTeam);

            m.setKvote(openMatch(i));

            utakmice.add(m);
            mp.goToMatch(i);
        }
        return utakmice;
    }

    private HashMap<String, Odd> openMatch(int i){
        int numberOfOdds;
        mp.goToMatch(i);
        mp.waitForMatchPageToLoad();
        numberOfOdds = mp.getNumberOfOdds(i);
        return iterateThroughAllOdds(numberOfOdds, i);
    }



    private HashMap<String, Odd> iterateThroughAllOdds(int numberOfOdds, int i){
        HashMap<String, Odd> kvote = new HashMap<String, Odd>();
        int oddFieldsSize;
        for (int j = 1; j <= numberOfOdds; j++) {
            String gameName = nameFactory.getGameName(mp.getGameName(i, j));
            Odd odd = factory.getOdd(gameName);
            if (odd != null) {
                oddFieldsSize = mp.getNumberOfOptionsForOdd(i, j);
                for (int k = 1; k <= oddFieldsSize; k++) {
                    String[] oddNameAndValue = getOddNameAndValue(i, j, k, gameName);

                    if (StringUtils.isNotEmpty(oddNameAndValue[0]) && StringUtils.isNotEmpty(oddNameAndValue[1])) {
                        if (gameName.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
                            oddNameAndValue[0] = nameFactory.getGameNameOption(gameName, oddNameAndValue[0]);
                        }
                        odd.setParameter(oddNameAndValue[0], Double.parseDouble(oddNameAndValue[1]));
                        kvote.put(gameName, odd);
                    }

                }
            }
        }

        return kvote;
    }

    private String[] getOddNameAndValue(int i, int j, int k, String gameName){
        String[] nameValue = new String[2];
        String oddName = mp.getOddName(i, j, k);
        String oddValue = mp.getOddValue(i, j, k);

        nameValue[0] = oddName;
        nameValue[1] = oddValue;

        return  nameValue;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
