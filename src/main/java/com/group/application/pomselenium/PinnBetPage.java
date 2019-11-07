package com.group.application.pomselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PinnBetPage extends PageObject implements IPage{

    public PinnBetPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.XPATH, using = "//ngb-modal-window[2]//button")
    private
    WebElement modal1;

    @FindBy(how = How.XPATH, using = "//ngb-modal-window[1]//button")
    private
    WebElement modal2;

    @FindBy(how = How.XPATH, using = "//section[@class='main-container']/div[@id='scroll']//app-competitionmain")
    private
    WebElement container;

    @FindBy(how = How.XPATH, using = "//section[@class='main-container']/app-input/div/div[1]/span")
    private
    WebElement backButton;

    @FindBy(how = How.XPATH, using = "//app-selections")
    private
    WebElement singleMatch;

    public void closeAndGoToPage(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ngb-modal-window[2]//button")));
        modal1.click();
        myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//ngb-modal-window[1]//button")));
        modal2.click();
    }
    public void waitForMenuToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']" +
                        "//ul//li[@class='ng-star-inserted']"+
                        "//span[@class='title ellipsis_live ng-star-inserted']")));
    }

    public String getSportName(int i){
        return driver.findElement(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']" +
                "//ul//li[@class='ng-star-inserted']["+ i +"]" +
                "//span[@class='title ellipsis_live ng-star-inserted']")).getAttribute("textContent");
    }

    public int getSubMenuSize(int i){
        return driver.findElements(By.xpath("//app-leftsidebar//nav[@class='leftsidebar']" +
                "/ul/li[@class='ng-star-inserted']["+ i +"]/ul[@class='submenu']" +
                "/li[@class='ng-star-inserted']")).size();
    }

    public boolean isThereCountries(int i, int j){
        return (driver.findElements(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']" +
                "/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']" +
                "/li[@class='ng-star-inserted'][" + j + "]" +
                "/a/span[@class='title clickable ellipsis_live ng-star-inserted']")).size() != 0);
    }

    public String getCountryName(int i, int j){
        return driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']" +
                "/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']" +
                "/li[@class='ng-star-inserted'][" + j + "]" +
                "/a/span[@class='title clickable ellipsis_live ng-star-inserted']"
        )).getAttribute("textContent");
    }

    public int getLeaguesSize(int i, int j){
        return driver.findElements(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']" +
                "/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']" +
                "/li[@class='ng-star-inserted'][" + j + "]/ul/li")).size();
    }

    public String getLeagueName(int i, int j, int k){
        return driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']" +
                "/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']" +
                "/li[@class='ng-star-inserted'][" + j + "]/ul/li[@class='ng-star-inserted'][" + k + "]" +
                "/a/span")).getAttribute("textContent");
    }

    public void clickOnLeague(int i, int j, int k){
        WebElement liga = driver.findElement(By.xpath("//app-leftsidebar/nav[@class='leftsidebar']" +
                "/ul/li[@class='ng-star-inserted'][" + i + "]/ul[@class='submenu']" +
                "/li[@class='ng-star-inserted'][" + j + "]/ul/li[@class='ng-star-inserted'][" + k + "]" +
                "/a/span"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", liga);
    }

    public void goBack(){
        WebDriverWait wait2 = new WebDriverWait(driver, 10);
        wait2.until(ExpectedConditions.elementToBeClickable(backButton));
        backButton.click();
    }

    public int getNumberOfDays(){
        return container.findElements(By.xpath("//section[@class='ng-star-inserted']" +
                "//div[@class='events_table ng-star-inserted']")).size();
    }

    public void waitForPageToLoad(int day){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("" +
                        "//div[@class='events_table ng-star-inserted']["+ day +"]//app-event[1]")));
    }

    public int getNumberOfMatchesForGivenDay(int day){
        return driver.findElements(By.xpath("//section[@class='main-container']/div[@id='scroll']" +
                "//app-competitionmain//section[@class='ng-star-inserted']" +
                "//div[@class='events_table ng-star-inserted']["+ day +"]//app-event")).size();
    }

    public String getHomeTeamName(int day, int game){
        return driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted']["+ day +"]" +
                "//app-event[" + game + "]/div/div/div[starts-with(@class, 'home_away_match')]" +
                "//span[contains(@class,'homeTeamName')]")).getAttribute("textContent");
    }
    public String getAwayTeamName(int day, int game){
        return driver.findElement(By.xpath("//div[@class='events_table ng-star-inserted']["+ day +"]" +
                "//app-event[" + game + "]/div/div/div[starts-with(@class, 'home_away_match')]" +
                "//span[contains(@class,'awayTeamName')]")).getAttribute("textContent");
    }

    public void goToMatch(int day, int game){
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        WebElement button = driver.findElement(By.xpath("" +
                "//div[@class='events_table ng-star-inserted']["+ day +"]" +
                "//app-event[" + game + "]/div/div/div[4]" +
                "/div[starts-with(@class, 'add_more_game')]/span"));
        executor.executeScript("arguments[0].click();", button);
    }

    public void waitForMatchPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("" +
                        "//app-selections//app-select-list"))));
    }


    public int getNumberOfOdds(){
        return singleMatch.findElements(By.xpath("//app-select-list/div")).size();
    }

    public int getNumberOfOptionsForOdd(int j){
        return singleMatch.findElements(By.xpath("//app-select-list/div[" + j + "]" +
                "/app-select/div/div")).size();
    }



    public String getGameName(int j){
        return  singleMatch.findElement(By.xpath("//app-select-list/div[" + j + "]" +
                "/app-select/div/div[1]/span/span")).getAttribute("textContent");
    }
    public String getOddName(int j, int k){
        return  singleMatch.findElement(By.xpath("//app-select-list/div[" + j + "]" +
                "/app-select/div/div[" + k + "]/div[1]")).getAttribute("textContent");
    }
    public String getOddValue(int j, int k){
        return  singleMatch.findElement(By.xpath("//app-select-list/div[" + j + "]" +
                "/app-select/div/div[" + k + "]/div[2]/span")).getAttribute("textContent");
    }

    public void waitForOdds(int j){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated((By.xpath("" +
                        "//app-select-list/div[" + j + "]/app-select/div/div"))));
    }




    @Override
    public String getGameName(int j, int k) {
        return null;
    }
    @Override
    public void goToMatch(int i) {

    }

    @Override
    public void waitForPageToLoad() {

    }


}
