package com.group.application.config;

import com.group.application.pomselenium.*;
import com.group.application.utils.BettingPlaceNames;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Lazy
@Configuration
public class PageObjectConfig {

    private PageFactory pageFactory = new PageFactory();
    WebDriver driver;
    @Bean
    WebDriver getDriver(){
        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        return driver;
    }
    @Bean
    MozzartPage getMozzartPage(){
        return (MozzartPage) pageFactory.getPage(BettingPlaceNames.Mozzart.toString(), getDriver());
    }
    @Bean
    MeridianPage getMeridianPage(){
        return (MeridianPage) pageFactory.getPage(BettingPlaceNames.Meridian.toString(), getDriver());
    }
    @Bean
    Bet365Page getBet365Page(){
        return (Bet365Page) pageFactory.getPage(BettingPlaceNames.Bet365.toString(), getDriver());
    }
    @Bean
    Sport888Page get888SportPage() {
        return (Sport888Page)pageFactory.getPage(BettingPlaceNames.Sport888.toString(), getDriver());
    }
    @Bean
    PinnBetPage getPinnbetPage() {
        return (PinnBetPage) pageFactory.getPage(BettingPlaceNames.Pinnbet.toString(), getDriver());
    }
}
