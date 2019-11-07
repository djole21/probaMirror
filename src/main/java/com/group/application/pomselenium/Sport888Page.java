package com.group.application.pomselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Sport888Page extends PageObject implements IPage{

    @FindBy(how = How.XPATH, using = "//div[@class='KambiBC-event-groups-list']")
    private
    WebElement container;

    private JavascriptExecutor jse2 = (JavascriptExecutor)driver;
    private WebElement myDynamicElement;

    @FindBy(how = How.XPATH, using = "//div[@class='KambiBC-event-page-component__scoreboard-container']" +
            "//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']")
    private
    WebElement header;

    private WebElement allOUOdds;

    public Sport888Page(WebDriver driver) {
        super(driver);
    }

    public void waitForPageToLoad(){
        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("" +
                        "//div[@class='KambiBC-time-ordered-list-content']"))));
    }

    public int getNumberOfDays(){
        return container.findElements(By.xpath("" +
                "//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]"
        )).size();
    }
    public void openNewDayContainer(int i){
        List<WebElement> c = container.findElements(By.xpath("//div[@class='KambiBC-event-groups-list']" +
                "//div[@class='KambiBC-collapsible-container KambiBC-mod-event-group-container KambiBC-expanded']" +
                "[" + i + "]/header/div"));
        if(c.size() == 0){
            WebElement newDay = container.findElement(By.xpath("//div[@class='KambiBC-event-groups-list']" +
                "//div[starts-with(@class,'KambiBC-collapsible-container KambiBC-mod-event-group-container')]" +
                "[" + i + "]/header/div"));
            jse2.executeScript("arguments[0].click()", newDay);
        }
    }
    public int getNumberOfMatchesForGivenDay(int i){
        return container.findElements(By.xpath("/" +
                "/div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]" +
                "["+ i +"]/div/ul[2]/li")).size();
    }
    public void goToMatch(int i, int j){
        WebElement toGameButton = container.findElement(By.xpath("" +
                "//div[starts-with(@class, 'KambiBC-collapsible-container KambiBC-mod-event-group-container')]" +
                "["+ i +"]/div/ul[2]/li["+ j +"]/a"));
        jse2.executeScript("arguments[0].click()", toGameButton);
    }
    public void waitForMatchPageToLoad(){
        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("" +
                        "//div[@class='KambiBC-event-page-component__scoreboard-container']//span"))));
    }
    public String getHomeTeamName(){
        return header.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']" +
                "//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']" +
                "//span[1]")).getAttribute("textContent");
    }
    public String getAwayTeamName(){
        return header.findElement(By.xpath("//div[@class='KambiBC-event-page-component__scoreboard-container']" +
                "//div[@class='KambiBC-modularized-scoreboard KambiBC-modularized-scoreboard--no-score']" +
                "//span[3]")).getAttribute("textContent");
    }
    public void waitForAllOddsToLoad(){
        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("" +
                        "//ul[@class='KambiBC-bet-offer-category__subcategories']/li"))));
    }
    public int getNumberOfOdds(){
        return driver.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li")).size();
    }
    public String getGameName(int k){
        return driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li["+ k +"]/div/h3")).getAttribute("textContent");
    }
    public int getNumberOfOptionsForOdd(int k){
        return driver.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li["+ k +"]//div[@class='KambiBC-outcomes-list__column']")).size();
    }
    public String getOddName(int k, int l){
        return driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__column'][" + l + "]" +
                "//div[@class='KambiBC-mod-outcome__label-wrapper']")).getAttribute("textContent");
    }
    public String getOddValue(int k, int l){
        return driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__column'][" + l + "]" +
                "//div[@class='KambiBC-mod-outcome__odds-wrapper']")).getAttribute("textContent");
    }
    public void clickAndWaitToShowAllOverUnderOdds(int k){
        WebElement showAllButton = driver.findElement(By.xpath("" +
                "//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]" +
                "//div[@class='KambiBC-outcomes-list__toggler']/button"));

        jse2.executeScript("arguments[0].click()", showAllButton);
        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("" +
                        "//ul[@class='KambiBC-bet-offer-category__subcategories']/li[" + k + "]" +
                        "//div[@class='KambiBC-outcomes-list__toggler']" +
                        "//div[@class='KambiBC-outcomes-list " +
                        "KambiBC-outcomes-list--layout-grouped-betoffers-with-headers " +
                        "KambiBC-outcomes-list--columns-2']"))));
    }
    public void setAllOUOdds(int k){
        allOUOdds = driver.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']" +
                "//div[@class='KambiBC-outcomes-list " +
                "KambiBC-outcomes-list--layout-grouped-betoffers-with-headers " +
                "KambiBC-outcomes-list--columns-2']"));
    }
    public int getNumberOfColumnsForOUOdds(int k){
        return allOUOdds.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']" +
                "//div[@class='KambiBC-outcomes-list " +
                "KambiBC-outcomes-list--layout-grouped-betoffers-with-headers " +
                "KambiBC-outcomes-list--columns-2']/div[@class='KambiBC-outcomes-list__column']")).size();
    }
    public int getNumberOfOUOddsInSingleColumn(int k, int ii){
        return driver.findElements(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']" +
                "//div[@class='KambiBC-outcomes-list " +
                "KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']" +
                "/div[@class='KambiBC-outcomes-list__column']["+ ii +"]/button")).size();
    }
    public String getTotalGoalsOverUnderOddName(int k, int ii, int jj){
        return allOUOdds.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']" +
                "//div[@class='KambiBC-outcomes-list " +
                "KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']" +
                "/div[@class='KambiBC-outcomes-list__column']["+ii+"]/button["+jj+"]" +
                "//div[@class='KambiBC-mod-outcome__label-wrapper']")).getAttribute("textContent");
    }
    public String getTotalGoalsOverUnderOddValue(int k, int ii, int jj){
        return allOUOdds.findElement(By.xpath("//ul[@class='KambiBC-bet-offer-category__subcategories']" +
                "/li[" + k + "]//div[@class='KambiBC-outcomes-list__toggler']" +
                "//div[@class='KambiBC-outcomes-list " +
                "KambiBC-outcomes-list--layout-grouped-betoffers-with-headers KambiBC-outcomes-list--columns-2']" +
                "/div[@class='KambiBC-outcomes-list__column']["+ii+"]/button["+jj+"]" +
                "//div[@class='KambiBC-mod-outcome__odds-wrapper']")).getAttribute("textContent");
    }


    @Override
    public String getGameName(int j, int k) {
        return null;
    }
    @Override
    public void goToMatch(int i) {

    }

}
