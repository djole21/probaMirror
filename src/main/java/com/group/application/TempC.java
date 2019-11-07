package com.group.application;

import com.group.application.match.Match;
import com.group.application.odds.GetOddFactory;
import com.group.application.pomselenium.Bet365Page;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TempC {

    public static void loadNames(String url, WebDriver driver) throws IOException {

        GetOddFactory factory = new GetOddFactory();
        BufferedWriter bw = new BufferedWriter(new FileWriter("/home/djordje/Desktop/matches/timovi-meridian.txt", true));

        List<String> list = new ArrayList<String>();
        driver.get(url);

        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//event-list-item[1]//div[@class='standard application.match']"))));

        WebElement m = driver.findElement(By.xpath("//leagues-component//div[@id='league']//div[@id='matches']"));

        List<WebElement> left = m.findElements(By.xpath("//leagues-component//div[@id='league']//div[@id='matches']//event-list-item"));


        for (int i = 1; i <= left.size(); i++) {
            //for (int i = 1; i <= 7; i++) {
            myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.elementToBeClickable((By.xpath("//event-list-item[1]//div[@class='standard application.match']"))));
            m = driver.findElement(By.xpath("//leagues-component//div[@id='league']//div[@id='matches']"));

            WebElement match;
            WebElement home;
            WebElement away;
            try {
                match = m.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']"));
                home = match.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']//div[@class='rivals']//div[@class='home']"));
                away = match.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard application.match']//div[@class='rivals']//div[@class='away']"));

            } catch (Exception e) {
                break;
            }
            String h = home.getText();
            String a = away.getText();

            if(!list.contains(h)){
                list.add(h);
                bw.write(h);
                bw.newLine();
            }
            if(!list.contains(a)){
                list.add(a);
                bw.write(a);
                bw.newLine();
            }




        }
        bw.flush();
    }

    public static void loadNamesMozz(String url, WebDriver driver) throws IOException {

        GetOddFactory factory = new GetOddFactory();
        BufferedWriter bw = new BufferedWriter(new FileWriter("/home/djordje/Desktop/matches/timovi-Mozzart.txt", true));

        List<String> list = new ArrayList<String>();
        driver.get(url);

        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//div[@class='sportsoffer']"))));

        List<WebElement> games = driver.findElements(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article"));

        for (int i = 1; i <= games.size(); i++) {
            //    for(int i = 1; i <= 9; i++){
            String home = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='part1']//a[@class='pairs']/div/span[1]")).getAttribute("textContent");
            String away = driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']//article[" + i + "]//div[@class='part1']//a[@class='pairs']/div/span[2]")).getAttribute("textContent");



            if(!list.contains(home)){
                list.add(home);
                bw.write(home);
                bw.newLine();
            }
            if(!list.contains(away)){
                list.add(away);
                bw.write(away);
                bw.newLine();
            }


        }
        bw.flush();
    }
    public static void loadNamesPinn(String url, WebDriver driver, String country, String league) throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter("/home/djordje/Desktop/matches/timovi-pinnbet.txt", true));

        List<String> list = new ArrayList<String>();
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

        for (int i = 1; i < 5; i++) {
            String s = driver.findElement(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']//ul//li[@class='ng-star-inserted'][" + i + "]//span[@class='title ellipsis_live ng-star-inserted']")).getAttribute("textContent");
            if (s.equalsIgnoreCase("fudbal")) {
                List<WebElement> subMenu = driver.findElements(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted']"));

                for (int j = 1; j <= subMenu.size(); j++) {
                    ////*[@id="content"]/app-leftsidebar/nav/ul/li[9]/ul/li[3]
                    if (driver.findElements(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/a/span[@class='title clickable ellipsis_live ng-star-inserted']")).size() != 0) {

                        String drzava = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/a/span[@class='title clickable ellipsis_live ng-star-inserted']")).getAttribute("textContent");


                        if (drzava.equalsIgnoreCase(country)) {
                            List<WebElement> lige = driver.findElements(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/ul/li"));

                            for (int k = 1; k <= lige.size(); k++) {


                                WebElement liga = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/ul/li[@class='ng-star-inserted'][" + k + "]/a/span"));
                                String nazivLige = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']/li[@class='ng-star-inserted'][" + j + "]/ul/li[@class='ng-star-inserted'][" + k + "]/a/span")).getAttribute("textContent");

                                if (nazivLige.equalsIgnoreCase(league)) {

                                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                                    executor.executeScript("arguments[0].click();", liga);
                                    //liga.click();
                                }
                            }
                        }
                    }
                }
            }

        }

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        WebElement container = driver.findElement(By.xpath("//section[@class='main-container']/div[@id='scroll']//app-competitionmain"));
        WebElement backButton = driver.findElement(By.xpath("//section[@class='main-container']/app-input/div/div[1]/span"));

        WebElement a = container.findElement(By.xpath("//section[@class='ng-star-inserted']"));


        List<WebElement> days = a.findElements(By.xpath("//div[@class='events_table ng-star-inserted']"));
        System.out.println(days.size() + " broj dana");
        for (int ii = 1; ii <= days.size(); ii++) {
            //for(int ii = 1; ii <= 1; ii++) {
            myDynamicElement = (new WebDriverWait(driver, 10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='events_table ng-star-inserted'][" + ii + "]//app-event[1]")));

            List<WebElement> matches = driver.findElements(By.xpath("//section[@class='main-container']/div[@id='scroll']//app-competitionmain//section[@class='ng-star-inserted']//div[@class='events_table ng-star-inserted'][" + ii + "]//app-event"));

            System.out.println("no of matches: " + matches.size());
            for (int i = 1; i <= matches.size(); i++) {
                //    for (int i = 1; i <= 9; i++) {
                String home = driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted'][" + ii + "]//app-event[" + i + "]/div/div/div[starts-with(@class, 'home_away_match')]//span[contains(@class,'homeTeamName')]")).getAttribute("textContent");
                String away = driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted'][" + ii + "]//app-event[" + i + "]/div/div/div[starts-with(@class, 'home_away_match')]//span[contains(@class,'awayTeamName')]")).getAttribute("textContent");


                if (!list.contains(home)) {
                    list.add(home);
                    bw.write(home);
                    bw.newLine();
                }
                if (!list.contains(away)) {
                    list.add(away);
                    bw.write(away);
                    bw.newLine();
                }


            }
            bw.flush();
        }
    }

    public static void loadNames888(String url, WebDriver driver) throws IOException {


        List<Match> utakmice = new ArrayList<Match>();
        BufferedWriter bw = new BufferedWriter(new FileWriter("/home/djordje/Desktop/matches/timovi-888sport.txt", true));

        List<String> list = new ArrayList<String>();

        GetOddFactory factory = new GetOddFactory();


        driver.get(url);

        JavascriptExecutor jse2 = (JavascriptExecutor) driver;


        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//div[@class='KambiBC-time-ordered-list-content']"))));

        WebElement container = driver.findElement(By.xpath("//div[@class='KambiBC-event-groups-list']"));

        List<WebElement> days = container.findElements(By.xpath("//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]"));

        System.out.println(days.size() + " broj dana");
        for (int i = 1; i <= days.size(); i++) {
            //jse2.executeScript("arguments[0].click()", days.get(i-1));
            List<WebElement> openNewDay;
            if (i > 1) {
                openNewDay = container.findElements(By.xpath("//div[@class='KambiBC-event-groups-list']//div[starts-with(@class,'KambiBC-collapsible-container KambiBC-mod-event-group-container')][" + i + "]/header/div"));
                if (openNewDay.size() != 0) {
                    jse2.executeScript("arguments[0].click()", openNewDay.get(0));

                }
            }


            List<WebElement> games = container.findElements(By.xpath("//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')][" + i + "]/div/ul[2]/li"));

            Match m;
            for (int j = 1; j <= games.size(); j++) {
                WebElement toGameButton = container.findElement(By.xpath("//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')][" + i + "]/div/ul[2]/li[" + j + "]/a"));
                jse2.executeScript("arguments[0].click()", toGameButton);
                //KambiBC-event-page-component__column KambiBC-event-page-component__column--1
                myDynamicElement = (new WebDriverWait(driver, 10))
                        .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//span"))));

                WebElement header = driver.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']"));
                String home = header.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']//span[1]")).getAttribute("textContent");
                String away = header.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']//span[3]")).getAttribute("textContent");

                if (!list.contains(home)) {
                    list.add(home);
                    bw.write(home);
                    bw.newLine();
                }
                if (!list.contains(away)) {
                    list.add(away);
                    bw.write(away);
                    bw.newLine();
                }

            }
            bw.flush();
        }
    }

    public static void loadNamesBet365(Bet365Page bp, String url) throws IOException {
        Bet365Page bet365Page = bp;
        BufferedWriter bw = new BufferedWriter(new FileWriter("/home/djordje/Desktop/matches/timovi-bet365.txt", true));
        bet365Page.goToPage(url);
        bet365Page.closeAndGoToPage();
        bet365Page.closeAndGoToPage();
        bet365Page.goToPage(url);

        List<String> list = new ArrayList<String>();

        bet365Page.waitForPageToLoad();
        int numberOfMatches = bet365Page.getNumberOfMatches();
        System.out.println(numberOfMatches);
        //(numberOfMatches);

        List<Match> utakmice = new ArrayList<Match>();

        for (int i = 1; i <= numberOfMatches; i++) {
            bet365Page.waitForPageToLoad();
            String[] homeAndAway = StringUtils.splitByWholeSeparator(bet365Page.getHomeAndAwayTeamName(i), " v ");
            String home = homeAndAway[0];
            String away = homeAndAway[1];

            if (!list.contains(home)) {
                list.add(home);
                bw.write(home);
                bw.newLine();
            }
            if (!list.contains(away)) {
                list.add(away);
                bw.write(away);
                bw.newLine();
            }
            bw.flush();
        }


    }


}
