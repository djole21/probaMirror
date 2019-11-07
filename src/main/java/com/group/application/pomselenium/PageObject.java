package com.group.application.pomselenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PageObject {

    protected WebDriver driver;
    WebDriverWait wait;


    public PageObject(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver,10);
        org.openqa.selenium.support.PageFactory.initElements(driver, this);

    }

    public void waitForElement(By locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void goToPage(String url){
        driver.get(url);
    }

}
