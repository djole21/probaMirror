package com.group.application.odds;

import com.group.application.utils.OddNames;

public class KonacanIshod extends Odd implements Cloneable {

    private double domacin;
    private double nereseno;
    private double gost;

    private String lbl;

    public KonacanIshod() {
        this.lbl = OddNames.FullTime.toString();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        KonacanIshod ki = (KonacanIshod) super.clone();
        double margin = ((100.0F/domacin) + (100.0F/gost) + (100.0F/nereseno))/100.0F;
        if(domacin > 4.0F) {
            ki.setParameter("1", domacin * margin* margin);
        } else {
            ki.setParameter("1", domacin * margin);
        } if(gost > 4.0F) {
            ki.setParameter("2", gost * margin * margin);
        } else {
            ki.setParameter("2", gost * margin );
        } if(nereseno > 4.0F) {
            ki.setParameter("x", nereseno * margin * margin);
        } else {
            ki.setParameter("x", nereseno * margin );
        }

        return ki;
    }

    public double getDomacin() {
        return domacin;
    }

    public double getNereseno() {
        return nereseno;
    }

    public double getGost() {
        return gost;
    }

    public void setDomacin(double domacin) {
        this.domacin = domacin;
    }

    public void setNereseno(double nereseno) {
        this.nereseno = nereseno;
    }

    public void setGost(double gost) {
        this.gost = gost;
    }

    public void setParameter(String s, double b){
        if(s.equalsIgnoreCase("1")){
            this.domacin = b;
        } else if(s.equalsIgnoreCase("x")) {
            this.nereseno = b;
        } else if(s.equalsIgnoreCase("2")) {
            this.gost = b;
        }
    }

    public Object getClone() throws CloneNotSupportedException {
        return clone();
    }

    @Override
    public String toString() {
        return  "1: " + domacin + " x: " + nereseno + " 2: " + gost;
    }
}
