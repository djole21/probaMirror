package com.group.application.odds;

public class ObaTimaDajuGol extends Odd implements Cloneable {
    private double gg;
    private double ng;

    public ObaTimaDajuGol() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ObaTimaDajuGol ggbg = (ObaTimaDajuGol) super.clone();
        double margin = ((100.0f/gg) + (100.0f/ng))/100.0f;
        if(gg > 4.0F) {
            ggbg.setParameter("gg", gg * margin * margin);
        } else {
            ggbg.setParameter("gg", gg * margin);
        }
        if(ng > 4.0F) {
            ggbg.setParameter("ng", ng * margin * margin);
        } else {
            ggbg.setParameter("ng", ng * margin);
        }
        return ggbg;
    }

    public double getGg() {
        return gg;
    }

    public double getNg() {
        return ng;
    }
/*
    public void setGg(double gg) {
        this.gg = gg;
    }

    public void setNg(double ng) {
        this.ng = ng;
    }
  */
    public void setParameter(String s, double b){
        if(s.equalsIgnoreCase("gg")){
            this.gg = b;
        }else if(s.equalsIgnoreCase("ng")) {
            this.ng = b;
        }

    }

    public Object getClone() throws CloneNotSupportedException {
        return clone();
    }

    @Override
    public String toString() {
        return "gg=" + gg +", ng=" + ng;
    }
}
