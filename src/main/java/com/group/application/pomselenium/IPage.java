package com.group.application.pomselenium;

public interface IPage {

    public String getGameName(int j, int k);
    public String getGameName(int j);




    public void waitForMatchPageToLoad();
    public void goToMatch(int i);
    public void waitForPageToLoad();
    public String getOddName(int j, int k);
    public String getOddValue(int j, int k);
    public int getNumberOfOptionsForOdd(int j);
}
