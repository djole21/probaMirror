package com.group.application.odds;

public abstract class Odd implements Cloneable {

    public void setParameter(String s, double b){};
    public abstract Object getClone() throws CloneNotSupportedException;

}
