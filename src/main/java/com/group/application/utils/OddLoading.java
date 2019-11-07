package com.group.application.utils;

import com.group.application.match.Match;
import com.group.application.names.INameFactory;
import com.group.application.names.NameFactory;
import com.group.application.odds.GetOddFactory;
import com.group.application.odds.Odd;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class OddLoading {


    public static INameFactory nameFactory;

    static List<Match> loadMeridian(String url, WebDriver driver, HashMap<String, String> timovi) {

        nameFactory = NameFactory.getFactory("meridian");
        List<Match> utakmice = new ArrayList<Match>();

        GetOddFactory factory = new GetOddFactory();


        driver.get(url);

        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//event-list-item[1]//div[@class='standard application.match']"))));

        WebElement m = driver.findElement(By.xpath("//leagues-component//div[@id='league']//div[@id='matches']"));

        List<WebElement> left = m.findElements(By.xpath("//leagues-component//div[@id='league']//div[@id='matches']//event-list-item"));


        for (int i = 1; i <= left.size(); i++) {
        //    for (int i = 1; i <= 7; i++) {
            myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable((By.xpath("//event-list-item[1]//div[@class='standard application.match']"))));
            m = driver.findElement(By.xpath("//leagues-component//div[@id='league']//div[@id='matches']"));
            Match m1;
            WebElement match;
            WebElement home;
            WebElement away;
            try {
                match = m.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']"));
                home = match.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']//div[@class='rivals']//div[@class='home']"));
                away = match.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']//div[@class='rivals']//div[@class='away']"));
                String h = timovi.get(home.getText());
                String a = timovi.get(away.getText());

                m1 = new Match(h, a);
            }catch (Exception e){
                break;
            }

            WebElement asd = match.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']//event-market-number")).findElement(By.className("show-all"));
            asd.click();

            myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.visibilityOfElementLocated((By.id("single-application.match"))));


            WebElement w = driver.findElement(By.xpath("//application.match-page"));


            //WebElement singleMatch = driver.findElement(By.xpath("//application.match-page//div[@id='page']//all-games-component//div[@id='single-application.match']"));



            List<WebElement> nizIgara = w.findElements(By.xpath("//div[@class='game-wrapper']"));

            int bb;
            if(nizIgara.size()>= 5)
                bb = 5;
            else bb = nizIgara.size();
            for (int j = 1; j <= bb; j++) {

                String gameName = w.findElement(By.xpath("//div[@class='game-wrapper']["+ j +"]//div[@class='game-name tooltip']//div[@class='title']")).getAttribute("textContent");
                List<WebElement> jednaIgra = w.findElements(By.xpath("//div[@class='game-wrapper']["+ j +"]//div[starts-with(@class, 'selection tooltip')]"));

                Odd odd = null;

                gameName = nameFactory.getGameName(gameName);

                if(gameName.equalsIgnoreCase("UKUPNO GOLOVA")) {
                    if (m1.get("UKUPNO GOLOVA") == null)
                        odd = factory.getOdd(gameName);
                    else
                        odd = m1.get("UKUPNO GOLOVA");
                }else {
                    odd = factory.getOdd(gameName);
                }
                if(odd != null) {
                    for (int k = 1; k <= jednaIgra.size(); k++) {

                        if (gameName.equalsIgnoreCase("UKUPNO GOLOVA")) {

                            int broj = w.findElements(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[@class='selections two three']//div[@class='selection tooltip ou']")).size();

                            if(broj != 0) {
                                for (int kk = 1; kk <= broj; kk++) {
                                    WebElement manjeViseGranica = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[@class='selections two three']//div[" + kk  +"]//div[@class='selection tooltip ou']//div[@class='selection-odd']"));
                                    String granica = manjeViseGranica.getAttribute("textContent");

                                    for(int p = 1; p <= 2; p++) {
                                        WebElement na = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[@class='selections two three']//div[" + kk + "]//div[starts-with(@class, 'selection tooltip')][" + p + "]//div[@class='selection-name']"));
                                        String naziv = na.getAttribute("textContent");

                                        WebElement kvot = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[@class='selections two three']//div[" + kk + "]//div[starts-with(@class, 'selection tooltip')][" + p + "]//div[@class='selection-odd']"));
                                        String kvota = kvot.getAttribute("textContent");

                                        if (StringUtils.isNotEmpty(naziv) && StringUtils.isNotEmpty(kvota)) {
                                            String n = nameFactory.getGranica(granica, naziv);

                                            if (NumberUtils.isCreatable(kvota)) {
                                                odd.setParameter(n, Double.parseDouble(kvota));
                                                m1.add(gameName, odd);
                                            }
                                        }
                                    }
                                }
                                k += jednaIgra.size();

                            }
                            else{
                                for(int kk = 1; kk <= 2; kk++) {
                                    WebElement na = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[@class='selections two']//div[starts-with(@class, 'selection tooltip')][" + kk + "]//div[@class='selection-name']"));
                                    String naziv = na.getAttribute("textContent");
                                    WebElement kvot = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[@class='selections two']//div[starts-with(@class, 'selection tooltip')][" + kk + "]//div[@class='selection-odd']"));
                                    String kvota = kvot.getAttribute("textContent");



                                    if (StringUtils.isNotEmpty(naziv) && StringUtils.isNotEmpty(kvota)) {


                                        odd.setParameter(naziv, Double.parseDouble(kvota));
                                        m1.add(gameName, odd);
                                    }
                                }
                            }


                        }
                        else {

                                WebElement na = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[starts-with(@class, 'selection tooltip')]["+ k +"]//div[@class='selection-name']"));
                                String naziv = na.getAttribute("textContent");
                                WebElement kvot = w.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]//div[starts-with(@class, 'selection tooltip')]["+ k +"]//div[@class='selection-odd']"));
                                String kvota = kvot.getAttribute("textContent");

                                if (StringUtils.isNotEmpty(naziv) && StringUtils.isNotEmpty(kvota)) {
                                    if(gameName.equalsIgnoreCase("OBA TIMA DAJU GOL"))
                                        naziv = nameFactory.getGameNameOption(gameName, naziv);


                                    if(NumberUtils.isCreatable(kvota)) {
                                        odd.setParameter(naziv, Double.parseDouble(kvota));
                                        m1.add(gameName, odd);
                                    }
                                }
                        }
                    }
                }
            }


            utakmice.add(m1);

            driver.navigate().to(url);
        }

        return utakmice;
    }

    static List<Match> loadMozzart(String url, WebDriver driver, HashMap<String, String> timovi){


        List<Match> utakmice = new ArrayList<Match>();

        GetOddFactory factory = new GetOddFactory();

        nameFactory = NameFactory.getFactory("mozzart");

        driver.get(url);

        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//div[@class='sportsoffer']"))));

        List<WebElement> games = driver.findElements(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article"));

        for(int i = 1; i <= games.size(); i++){
        //    for(int i = 1; i <= 9; i++){
            String home = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='part1']//a[@class='pairs']/div/span[1]")).getAttribute("textContent");
            String away = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='part1']//a[@class='pairs']/div/span[2]")).getAttribute("textContent");
            home = timovi.get(home);
            away = timovi.get(away);
            Match m = new Match(home, away);


            WebElement goToMatch = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='part3']"));
            //WebDriverWait wait2 = new WebDriverWait(driver, 10);
            //wait2.until(ExpectedConditions.elementToBeClickable(goToMatch));

            JavascriptExecutor jse2 = (JavascriptExecutor)driver;
            jse2.executeScript("arguments[0].click()", goToMatch);

            //goToMatch.click();

            myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.visibilityOfElementLocated((By.className("oddsName"))));

            //WebElement allOddsHolder = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='singleMatchHolder']"));

            List<WebElement> allOdds = driver.findElements(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='application.odds']"));

            for(int j = 1; j <= allOdds.size(); j++){
                String gameName = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='application.odds']["+ j +"]//div[@class='oddsName']")).getAttribute("textContent");
                gameName = nameFactory.getGameName(gameName);
                Odd odd = factory.getOdd(gameName);
                if(odd != null) {
                    List<WebElement> odds = driver.findElements(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='application.odds'][" + j + "]//div[@class='odd']"));
                    for (int k = 1; k <= odds.size(); k++) {
                        String name = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='application.odds'][" + j + "]//div[@class='odd'][" + k + "]//span[@class='oddssubname']")).getAttribute("textContent");
                        String value = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='application.odds'][" + j + "]//div[@class='odd'][" + k + "]//span[@class='odd-font betting-full-application.match']")).getAttribute("textContent");

                        if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
                            if (gameName.equalsIgnoreCase("OBA TIMA DAJU GOL"))
                                name = nameFactory.getGameNameOption(gameName, name);

                            odd.setParameter(name, Double.parseDouble(value));
                            m.add(gameName, odd);
                        }
                    }
                }



            }
            utakmice.add(m);
            goToMatch.click();
        }


        return utakmice;
    }


    static List<Match> loadPinnBet(String url, WebDriver driver, String country, String league, HashMap<String, String> timovi){

        List<Match> utakmice = new ArrayList<Match>();
        GetOddFactory factory = new GetOddFactory();

        nameFactory = NameFactory.getFactory("pinnbet");
        driver.get(url);

        WebElement myDynamicElement;

        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ngb-modal-window[2]//button")));

        driver.findElement(By.xpath("//ngb-modal-window[2]//button")).click();

        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ngb-modal-window[1]//button")));

        driver.findElement(By.xpath("//ngb-modal-window[1]//button")).click();



        //myDynamicElement = (new WebDriverWait(driver, 10))
          //      .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']")));

        //List<WebElement> listaLevo = wasd.findElements(By.xpath("//li[@class='ng-star-inserted']"));


        List<WebElement> fudbal = driver.findElements(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted']"));
        //List<WebElement> fudbal = driver.findElements(By.cssSelector("app-leftsidebar>nav.leftsidebar>ul>li.ng-star-inserted"));

        for(int i = 1; i < 5; i++){
            String s = driver.findElement(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']//ul//li[@class='ng-star-inserted']["+ i +"]//span[@class='title ellipsis_live ng-star-inserted']")).getAttribute("textContent");
            if(s.equalsIgnoreCase("fudbal")){
                List<WebElement> subMenu = driver.findElements(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted']["+ i +"]/ul[@class='submenu']/li[@class='ng-star-inserted']"));

                for(int j = 1; j <= subMenu.size(); j++) {
                                                        ////*[@id="content"]/app-leftsidebar/nav/ul/li[9]/ul/li[3]
                    if (driver.findElements(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/a/span[@class='title clickable ellipsis_live ng-star-inserted']")).size() != 0) {

                        String drzava = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/a/span[@class='title clickable ellipsis_live ng-star-inserted']")).getAttribute("textContent");


                        if (drzava.equalsIgnoreCase(country)) {
                            List<WebElement> lige = driver.findElements(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/ul/li"));

                            for (int k = 1; k <= lige.size(); k++) {


                                WebElement liga = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/ul/li[@class='ng-star-inserted'][" + k + "]/a/span"));
                                String nazivLige = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/ul/li[@class='ng-star-inserted'][" + k + "]/a/span")).getAttribute("textContent");

                                if (nazivLige.equalsIgnoreCase(league)) {

                                    JavascriptExecutor executor = (JavascriptExecutor)driver;
                                    executor.executeScript("arguments[0].click();", liga);
                                    //liga.click();
                                }
                            }
                        }
                    }
                }
            }

        }

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        WebElement container = driver.findElement(By.xpath("//section[@class='main-container']/div[@id='scroll']//app-competitionmain"));
        WebElement backButton = driver.findElement(By.xpath("//section[@class='main-container']/app-input/div/div[1]/span"));

        WebElement a = container.findElement(By.xpath("//section[@class='ng-star-inserted']"));


        List<WebElement> days = a.findElements(By.xpath("//div[@class='events_table ng-star-inserted']"));
        System.out.println(days.size() + " broj dana");
        for(int ii = 1; ii <= days.size(); ii++) {
        //    for(int ii = 1; ii <= 1; ii++) {
            myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='events_table ng-star-inserted']["+ ii +"]//app-event[1]")));

            List<WebElement> matches = driver.findElements(By.xpath("//section[@class='main-container']/div[@id='scroll']//app-competitionmain//section[@class='ng-star-inserted']//div[@class='events_table ng-star-inserted']["+ ii +"]//app-event"));

            System.out.println("no of matches: " + matches.size());
            for (int i = 1; i <= matches.size(); i++) {
            //    for (int i = 1; i <= 9; i++) {
                String home = driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted']["+ ii +"]//app-event[" + i + "]/div/div/div[starts-with(@class, 'home_away_match')]//span[contains(@class,'homeTeamName')]")).getAttribute("textContent");
                String away = driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted']["+ ii +"]//app-event[" + i + "]/div/div/div[starts-with(@class, 'home_away_match')]//span[contains(@class,'awayTeamName')]")).getAttribute("textContent");

                home = timovi.get(home);
                away = timovi.get(away);

                Match m = new Match(home, away);

                WebElement button = driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted']["+ ii +"]//app-event[" + i + "]/div/div/div[4]/div[starts-with(@class, 'add_more_game')]/span"));
                executor.executeScript("arguments[0].click();", button);

                myDynamicElement = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//app-selections//app-select-list"))));
                WebElement singleMatch = driver.findElement(By.xpath("//app-selections"));
                List<WebElement> allOdds = singleMatch.findElements(By.xpath("//app-select-list/div"));
                Odd odd = null;
                int bb;
                if(allOdds.size() >= 4)
                    bb = 4;
                else
                    bb = allOdds.size();
                /*
                broj igara za jednu utakmicu

                 */

                for (int j = 1; j <= bb; j++) {


                    List<WebElement> singleOdds = singleMatch.findElements(By.xpath("//app-select-list/div[" + j + "]/app-select/div/div"));
                    String n = null;

                    /*
                    broj razlicitih kvota za jednu igru

                     */

                    myDynamicElement = (new WebDriverWait(driver, 10))
                            .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//app-select-list/div[" + j + "]/app-select/div/div"))));
                    for (int k = 1; k <= singleOdds.size(); k++) {

                        if (k == 1) {
                            String gameName = singleMatch.findElement(By.xpath("//app-select-list/div[" + j + "]/app-select/div/div[1]/span/span")).getAttribute("textContent");

                            n = nameFactory.getGameName(StringUtils.strip(gameName));
                            odd = factory.getOdd(n);


                        } else {
                            if (odd != null) {

                                String naziv = singleMatch.findElement(By.xpath("//app-select-list/div[" + j + "]/app-select/div/div[" + k + "]/div[1]")).getAttribute("textContent");
                                String kvota = singleMatch.findElement(By.xpath("//app-select-list/div[" + j + "]/app-select/div/div[" + k + "]/div[2]/span")).getAttribute("textContent");

                                if (StringUtils.isNotEmpty(naziv) && StringUtils.isNotEmpty(kvota)) {

                                    naziv = StringUtils.strip(naziv);
                                    if ( n.equalsIgnoreCase("OBA TIMA DAJU GOL"))
                                        naziv = nameFactory.getGameNameOption(n, naziv);


                                    odd.setParameter(naziv, Double.parseDouble(kvota));
                                    m.add(n, odd);
                                }
                            }
                        }

                    }
                }


                WebDriverWait wait2 = new WebDriverWait(driver, 10);
                wait2.until(ExpectedConditions.elementToBeClickable(backButton));
                utakmice.add(m);

                executor.executeScript("arguments[0].click();", backButton);

            }
        }





        return utakmice;


    }


    static List<Match> load888Sport(String url, WebDriver driver, HashMap<String, String> timovi) {

        List<Match> utakmice = new ArrayList<Match>();

        GetOddFactory factory = new GetOddFactory();

        nameFactory = NameFactory.getFactory("888sport");

        driver.get(url);

        JavascriptExecutor jse2 = (JavascriptExecutor)driver;


        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//div[@class='KambiBC-time-ordered-list-content']"))));

        WebElement container = driver.findElement(By.xpath("//div[@class='KambiBC-event-groups-list']"));

        List<WebElement> days = container.findElements(By.xpath("//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]"));

        System.out.println(days.size() + " broj dana");
        for(int i = 1; i <= days.size(); i++){
            //jse2.executeScript("arguments[0].click()", days.get(i-1));
            List<WebElement> openNewDay;
            if(i > 1) {
                openNewDay = container.findElements(By.xpath("//div[@class='KambiBC-event-groups-list']//div[starts-with(@class,'KambiBC-collapsible-container KambiBC-mod-event-group-container')][" + i + "]/header/div"));
                if(openNewDay.size() != 0){
                    jse2.executeScript("arguments[0].click()", openNewDay.get(0));

                }
            }


            List<WebElement> games = container.findElements(By.xpath("//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]["+ i +"]/div/ul[2]/li"));

            Match m;
            for(int j = 1; j <= games.size(); j++){
                WebElement toGameButton = container.findElement(By.xpath("//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]["+ i +"]/div/ul[2]/li["+ j +"]/a"));
                jse2.executeScript("arguments[0].click()", toGameButton);
                //KambiBC-event-page-component__column KambiBC-event-page-component__column--1
                myDynamicElement = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//span"))));

                WebElement header = driver.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']"));
                String home = header.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']//span[1]")).getAttribute("textContent");
                String away = header.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']//span[3]")).getAttribute("textContent");

                home = timovi.get(home);
                away = timovi.get(away);

                m = new Match(home, away);
                myDynamicElement = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[5]"))));

                Odd odd;
                for(int k = 1; k <= 5; k++){
                    String gameName = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li["+ k +"]/div/h3")).getAttribute("textContent");

                    gameName = nameFactory.getGameName(gameName);
                    odd = factory.getOdd(gameName);

                    List<WebElement> params = driver.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li["+ k +"]//div[@class='KambiBC-outcomes-list__column']"));
                    if(gameName.equalsIgnoreCase("KONACAN ISHOD")) {
                        for (int l = 1; l <= params.size(); l++) {
                            String oddName = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__column'][" + l + "]//div[@class='KambiBC-mod-outcome__label-wrapper']")).getAttribute("textContent");
                            String oddValue = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__column'][" + l + "]//div[@class='KambiBC-mod-outcome__odds-wrapper']")).getAttribute("textContent");
                            if(oddName.equalsIgnoreCase(home)){
                                oddName = "1";
                            }else if(oddName.equalsIgnoreCase(away)){
                                oddName = "2";
                            }
                            else if(oddName.equalsIgnoreCase("Draw")){
                                oddName = "x";
                            }
                            if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {

                                if(NumberUtils.isCreatable(oddValue)) {
                                    odd.setParameter(oddName, Double.parseDouble(oddValue));
                                    m.add(gameName, odd);
                                }
                            }
                        }
                    }
                    else if(gameName.equalsIgnoreCase("OBA TIMA DAJU GOL")){
                        for (int l = 1; l <= params.size(); l++) {
                            String oddName = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__column'][" + l + "]//div[@class='KambiBC-mod-outcome__label-wrapper']")).getAttribute("textContent");
                            String oddValue = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__column'][" + l + "]//div[@class='KambiBC-mod-outcome__odds-wrapper']")).getAttribute("textContent");

                            oddName = nameFactory.getGameNameOption(gameName, oddName);

                            if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {

                                if(NumberUtils.isCreatable(oddValue)) {
                                    odd.setParameter(oddName, Double.parseDouble(oddValue));
                                    m.add(gameName, odd);
                                }
                            }
                        }
                    }
                    else if(gameName.equalsIgnoreCase("UKUPNO GOLOVA")){

                        WebElement showAllButton = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']/button"));
                        jse2.executeScript("arguments[0].click()", showAllButton);
                        myDynamicElement = (new WebDriverWait(driver, 10))
                                .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']//div[@class='KambiBC-outcomes-list KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']"))));

                        WebElement allOUOdds = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']//div[@class='KambiBC-outcomes-list KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']"));
                        List<WebElement> kol = allOUOdds.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']//div[@class='KambiBC-outcomes-list KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']/div[@class='KambiBC-outcomes-list__column']"));

                        for(int ii = 1; ii <= kol.size(); ii++){
                            List<WebElement> left = driver.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']//div[@class='KambiBC-outcomes-list KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']/div[@class='KambiBC-outcomes-list__column']["+ ii +"]/button"));
                            for(int jj = 1; jj <= left.size(); jj++){
                                String oddName = allOUOdds.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']//div[@class='KambiBC-outcomes-list KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']/div[@class='KambiBC-outcomes-list__column']["+ii+"]/button["+jj+"]//div[@class='KambiBC-mod-outcome__label-wrapper']")).getAttribute("textContent");
                                String oddValue = allOUOdds.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']//div[@class='KambiBC-outcomes-list KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']/div[@class='KambiBC-outcomes-list__column']["+ii+"]/button["+jj+"]//div[@class='KambiBC-mod-outcome__odds-wrapper']")).getAttribute("textContent");

                                oddName = nameFactory.getGameNameOption(gameName, oddName);
                                if (StringUtils.isNotEmpty(oddName) && StringUtils.isNotEmpty(oddValue)) {
                                    if(NumberUtils.isCreatable(oddValue)) {
                                        odd.setParameter(oddName, Double.parseDouble(oddValue));
                                        m.add(gameName, odd);
                                    }
                                }
                            }
                        }
                    }
                }
                utakmice.add(m);
            }

        }



        return utakmice;
    }

}
