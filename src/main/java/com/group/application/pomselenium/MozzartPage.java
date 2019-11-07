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

public class MozzartPage extends PageObject implements IPage {


    @FindBy(xpath = "//div[@class='sportsoffer']//div[@class='competition']//article")
    private
    List<WebElement> games;

    private By allOdds = By.xpath("//div[@class='singleMatchHolder']//div[@class='odds']");

    @FindBy(xpath = "//div[@class='singleMatchHolder']//div[@class='odds']")
    private
    List<WebElement> allods;


    @FindBy(how = How.XPATH, using = ".//div[@class='odd']")
    List<WebElement> odds;

    @FindBy(how = How.XPATH, using = "//div[@class='part1']//a[@class='pairs']/div/span[1]")
    private
    WebElement homeName;

    @FindBy(how = How.XPATH, using = "//div[@class='part1']//a[@class='pairs']/div/span[2]")
    private
    WebElement awayName;

    @FindBy(how = How.XPATH, using = "//div[@class='part3']")
    private
    WebElement goToMatch;

    @FindBy(how = How.XPATH, using = "//div[@class='oddsName']")
    private
    WebElement gameName;

    @FindBy(how = How.XPATH, using = "//span[@class='oddssubname']")
    private
    WebElement oddName;

    @FindBy(how = How.XPATH, using = "//span[@class='odd-font betting-full-match']")
    private
    WebElement oddValue;

    @FindBy(how = How.XPATH, using = "//div[@class='sportsoffer']")
    private
    WebElement page;

    @FindBy(how = How.CLASS_NAME, using = "oddsName")
    private
    WebElement oddsNameWait;


    public MozzartPage(WebDriver driver) {
        super(driver);
    }

    public int getNumberOfMatches(){
        return games.size();
    }

    public String getHomeTeamName(int i){
        return driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='part1']//a[@class='pairs']/div/span[1]"
        )).getAttribute("textContent");
    }
    public String getAwayTeamName(int i){
        return driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='part1']//a[@class='pairs']/div/span[2]"
        )).getAttribute("textContent");
    }
    public void goToMatch(int i){
        WebElement button = driver.findElement(By.xpath("//div[@class='sportsoffer']" +
                "//div[@class='competition']//article[" + i + "]//div[@class='part3']"));
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click()", button);

    }

    public void waitForPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(page));
    }

    public void waitForMatchPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(oddsNameWait));
    }
    public void waitOddsToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("" +
                        "//div[@class='sportsoffer']" +
                        "//div[@class='competition']//article//div[@class='part1']" +
                        "//div[@class='infos']//div[@class='time']")));
    }

    public int getNumberOfOdds(int i){
        return driver.findElements(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='odds']")).size();
    }

    public String getGameName(int i, int j){
        return driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='odds']["+ j +"]" +
                "//div[@class='oddsName']")).getAttribute("textContent");
    }

    public int getNumberOfOptionsForOdd(int i, int j){
        return driver.findElements(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='odds'][" + j + "]" +
                "//div[@class='odd']")).size();
    }


    public String getOddName(int i, int j, int k){
        return driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='odds'][" + j + "]" +
                "//div[@class='odd'][" + k + "]//span[@class='oddssubname']")).getAttribute("textContent");
    }

    public String getOddValue(int i, int j, int k){
        return driver.findElement(By.xpath("//div[@class='sportsoffer']//div[@class='competition']" +
                "//article[" + i + "]//div[@class='singleMatchHolder']//div[@class='odds'][" + j + "]" +
                "//div[@class='odd'][" + k + "]//span[@class='odd-font betting-full-match']"
        )).getAttribute("textContent");
    }





    @Override
    public String getGameName(int j) {
        return null;
    }
    @Override
    public String getOddName(int j, int k) {
        return null;
    }

    @Override
    public String getOddValue(int j, int k) {
        return null;
    }

    @Override
    public int getNumberOfOptionsForOdd(int j) {
        return 0;
    }
}
