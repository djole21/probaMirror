package com.group.application.pomselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MeridianPage extends PageObject implements IPage {


    public MeridianPage(WebDriver driver) {
        super(driver);
    }
    @FindBy(how = How.XPATH, using = "//leagues-component//div[@id='league']//div[@id='matches']")
    private
    WebElement matches;

    @FindBy(how = How.XPATH, using = "//match-page")
    private
    WebElement singleMatch;


    public void waitForPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.elementToBeClickable((By.xpath("//event-list-item[1]" +
                        "//div[@class='standard match']"))));
    }

    public int getNumberOfMatches(){
        return matches.findElements(By.xpath("//leagues-component//div[@id='league']" +
                "//div[@id='matches']//event-list-item")).size();
    }
    public void reloadMatches(){
        matches =  driver.findElement(By.xpath("//leagues-component//div[@id='league']" +
                "//div[@id='matches']"));
    }
    public String getHomeTeamName(int i){
        return matches.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard match']" +
                "//div[@class='rivals']//div[@class='home']")).getText();
    }
    public String getAwayTeamName(int i){
        return matches.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard match']" +
                "//div[@class='rivals']//div[@class='away']")).getText();
    }

    public void goToMatch(int i){
        matches.findElement(By.xpath("//event-list-item[" + i + "]//div[@class='standard match']" +
                "//event-market-number")).findElement(By.className("show-all")).click();
    }

    public void waitForMatchPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated((By.id("single-match"))));
    }

    public int getNumberOfOdds(){
        return singleMatch.findElements(By.xpath("//div[@class='game-wrapper']")).size();
    }

    @Override
    public String getGameName(int j, int k) {
        return null;
    }

    public String getGameName(int j){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper']["+ j +"]" +
                "//div[@class='game-name tooltip']//div[@class='title']"
        )).getAttribute("textContent");
    }
    public int getNumberOfOptionsForOdd(int j){
        return singleMatch.findElements(By.xpath("//div[@class='game-wrapper']["+ j +"]" +
                "//div[starts-with(@class, 'selection tooltip')]")).size();
    }
    public int getTotalGoalsOverUnder(int j){
        return singleMatch.findElements(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[@class='selections two three']//div[@class='selection tooltip ou']")).size();
    }
    public String getTotalGoalsOverUnderMargin(int j, int kk){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[@class='selections two three']//div[" + kk  +"]//div[@class='selection tooltip ou']" +
                "//div[@class='selection-odd']")).getAttribute("textContent");
    }
    public String getTotalGoalsOverUnderOddName(int j, int kk, int p){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[@class='selections two three']//div[" + kk + "]" +
                "//div[starts-with(@class, 'selection tooltip')][" + p + "]" +
                "//div[@class='selection-name']")).getAttribute("textContent");
    }
    public String getTotalGoalsOverUnderOddValue(int j, int kk, int p){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[@class='selections two three']//div[" + kk + "]" +
                "//div[starts-with(@class, 'selection tooltip')][" + p + "]" +
                "//div[@class='selection-odd']")).getAttribute("textContent");
    }
    public String getTotalGoalsOddName(int j, int kk){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[@class='selections two']//div[starts-with(@class, 'selection tooltip')][" + kk + "]" +
                "//div[@class='selection-name']")).getAttribute("textContent");
    }
    public String getTotalGoalsOddValue(int j, int kk){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[@class='selections two']//div[starts-with(@class, 'selection tooltip')][" + kk + "]" +
                "//div[@class='selection-odd']")).getAttribute("textContent");
    }
    public String getOddName(int j, int k){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[starts-with(@class, 'selection tooltip')]["+ k +"]" +
                "//div[@class='selection-name']")).getAttribute("textContent");
    }
    public String getOddValue(int j, int k){
        return singleMatch.findElement(By.xpath("//div[@class='game-wrapper'][" + j + "]" +
                "//div[starts-with(@class, 'selection tooltip')]["+ k +"]" +
                "//div[@class='selection-odd']")).getAttribute("textContent");
    }
    public void navigateToUrl(String url){
        driver.navigate().to(url);
    }




}
