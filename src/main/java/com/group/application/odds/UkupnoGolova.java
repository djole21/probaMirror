package com.group.application.odds;

public class UkupnoGolova extends Odd implements Cloneable {
    private double nulaDoDva;
    private double triPlus;

    public UkupnoGolova() {

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        UkupnoGolova uk = (UkupnoGolova) super.clone();
        double margin = ((100.0f/nulaDoDva) + (100.0f/triPlus))/100.0f;
        if(nulaDoDva > 4.0F) {
            uk.setParameter("0-2", nulaDoDva * margin * margin);
        } else {
            uk.setParameter("0-2", nulaDoDva * margin);
        } if(triPlus > 4.0F) {
            uk.setParameter("3+", triPlus * margin * margin);
        } else {
            uk.setParameter("3+", triPlus * margin);
        }
        return uk;
    }

    public double getNulaDoDva() {
        return nulaDoDva;
    }

    public double getTriPlus() {
        return triPlus;
    }
/*
    public void setNulaDoDva(double nulaDoDva) {
        this.nulaDoDva = nulaDoDva;
    }

    public void setTriPlus(double triPlus) {
        this.triPlus = triPlus;
    }
  */
    public void setParameter(String s, double b){
        if(s.equalsIgnoreCase("0-2")){
            this.nulaDoDva = b;
        } else if(s.equalsIgnoreCase("3+")) {
            this.triPlus = b;
        }

    }

    public Object getClone() throws CloneNotSupportedException {
        return clone();
    }

    @Override
    public String toString() {
        return "0-2: " + nulaDoDva + " 3+: " + triPlus;
    }
}
