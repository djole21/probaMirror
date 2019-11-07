package com.group.application.pomselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Bet365Page extends PageObject implements IPage{


    public Bet365Page(WebDriver driver) {
        super(driver);
    }
    @FindBy(how = How.XPATH, using = "//div[@class='wc-CommonElementStyle_PrematchCenter ']")
    private
    WebElement container;

    @FindBy(how = How.XPATH, using = "//div[@class='cl-BreadcrumbTrail_BackButton ']")
    private
    WebElement backButton;

    @FindBy(how = How.XPATH, using = "//div[@class='cm-CouponModule ']")
    private
    WebElement matchPage;

    public void goBack(){
        backButton.click();
    }

    @Override
    public void waitForPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("" +
                        "//div[@class='gll-MarketGroup_Wrapper '][1]" +
                        "//div[@class='sl-MarketCouponFixtureLabelBase gll-Market_General gll-Market_HasLabels ']" +
                        "//div[starts-with(@class, 'sl-CouponParticipantWithBookCloses sl-CouponParticipantIPPGBase')]"
                )));
    }
    public void closeAndGoToPage(){
        driver.findElement(By.xpath("//*[@id='frmMain']/div[3]/center/div/div[2]/ul/li[1]/a")).click();
    }
    public String getHomeAndAwayTeamName(int i){
        return container.findElement(By.xpath("//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[@class='sl-MarketCouponFixtureLabelBase gll-Market_General gll-Market_HasLabels ']" +
                "//div[starts-with(@class, 'sl-CouponParticipantWithBookCloses sl-CouponParticipantIPPGBase ')]["+i+"]" +
                "//div[@class='sl-CouponParticipantWithBookCloses_NameContainer ']")).getAttribute("textContent");
    }


    public int getNumberOfMatches(){
        return container.findElements(By.xpath("//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[@class='sl-MarketCouponFixtureLabelBase gll-Market_General gll-Market_HasLabels ']" +
                "//div[starts-with(@class,'sl-CouponParticipantWithBookCloses sl-CouponParticipantIPPGBase ')]")).size();
    }
    @Override
    public void goToMatch(int i){
        WebElement button = container.findElement(By.xpath("//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[contains(@class, 'sl-MarketCouponFixtureLink ')]" +
                "//div[starts-with(@class, 'sl-CouponFixtureLinkParticipant ')]["+i+"]"));
        JavascriptExecutor jse2 = (JavascriptExecutor)driver;
        jse2.executeScript("arguments[0].click()", button);
    }
    @Override
    public void waitForMatchPageToLoad(){
        WebElement myDynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.visibilityOf(matchPage));
    }
    public int getNumberOfOdds(){
        return matchPage.findElements(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]")).size();
    }
    @Override
    public String getGameName(int j){
        return matchPage.findElement(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[contains(@class,'gll-MarketGroupButton')]//span")).getAttribute("textContent");
    }
    @Override
    public int getNumberOfOptionsForOdd(int j){
        return matchPage.findElements(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[starts-with(@class, 'gll-Participant_General')]")).size();
    }
    @Override
    public String getOddName(int j, int k){
        return matchPage.findElement(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[@class='gll-MarketGroup_Wrapper '][1]//div[starts-with(@class, 'gll-Participant_General')]["+k+"]" +
                "//span[@class='gll-Participant_Name']")).getAttribute("textContent");
    }
    @Override
    public String getOddValue(int j, int k){
        return matchPage.findElement(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[starts-with(@class, 'gll-Participant_General')]["+k+"]" +
                "//span[@class='gll-Participant_Odds']")).getAttribute("textContent");
    }
    public String getTotalGoalsOverUnderMargin(int j){
        return matchPage.findElement(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[starts-with(@class, 'gll-MarketLabel')]" +
                "//div[starts-with(@class, 'gll-ParticipantRowValue')]")).getAttribute("textContent");
    }
    public String getTotalGoalsOverUnderOddName(int j, int k){
        return matchPage.findElement(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[starts-with(@class, 'gll-MarketValuesExplicit2 ')]["+k+"]" +
                "/div[@class='gll-MarketColumnHeader ']")).getAttribute("textContent");
    }
    public String getTotalGoalsOverUnderOddValue(int j, int k){
        return matchPage.findElement(By.xpath("//div[contains(@class, 'gll-MarketGroup ')]["+j+"]" +
                "//div[@class='gll-MarketGroup_Wrapper '][1]" +
                "//div[starts-with(@class, 'gll-MarketValuesExplicit2 ')]["+k+"]" +
                "//div[@class='gll-Participant_General gll-ParticipantOddsOnly ']"
        )).getAttribute("textContent");
    }
    @Override
    public String getGameName(int j, int k) {
        return null;
    }




}
