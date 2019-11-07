package com.group.application.utils;

import com.group.application.match.Match;
import com.group.application.odds.KonacanIshod;
import com.group.application.odds.ObaTimaDajuGol;
import com.group.application.odds.UkupnoGolova;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


class OddComparator {
    private NumberFormat formatter = new DecimalFormat("#0.00");

    private List<String> compareTotalGoals(Match d, Match g, String klad)
            throws CloneNotSupportedException {
        List<String> list = new ArrayList<String>();
        UkupnoGolova ug = (UkupnoGolova) (d.get(OddNames.TotalGoals.toString())).getClone();

        UkupnoGolova ugg = (UkupnoGolova) g.get(OddNames.TotalGoals.toString());

        if (ug.getNulaDoDva() < ugg.getNulaDoDva()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost()
                    + " Uk. golova 0-2" + " kvota: " + ugg.getNulaDoDva()
                    + " OK(" + formatter.format(ug.getNulaDoDva()) + ") "
                    + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " Uk. golova 0-2" + " kvota: " + ugg.getNulaDoDva()
                    + " OK(" + formatter.format(ug.getNulaDoDva()) + ") "
                    + " u kladionici " + klad+"</p>");


        }
        if (ug.getTriPlus() < ugg.getTriPlus()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost() +
                    " Uk. golova 3+" + " kvota: " + ugg.getTriPlus() +
                    " OK(" + formatter.format(ug.getTriPlus()) +
                    ")" + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " Uk. golova 3+" + " kvota: " + ugg.getTriPlus()
                    + " OK(" + formatter.format(ug.getTriPlus()) + ")"
                    + " u kladionici " + klad+"</p>");

        }
        return list;
    }

    private List<String> compareFullTimeResult(Match d, Match g, String klad )
            throws CloneNotSupportedException {
        List<String> list = new ArrayList<String>();
        KonacanIshod ki = (KonacanIshod) (d.get(OddNames.FullTime.toString()));
        ki = (KonacanIshod) ki.getClone();
        KonacanIshod kig = (KonacanIshod) g.get(OddNames.FullTime.toString());

        if (ki.getDomacin() < kig.getDomacin()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost()
                    + " konacan ishod 1" + " kvota: " + kig.getDomacin()
                    + " OK(" + formatter.format(ki.getDomacin()) + ")"
                    + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " konacan ishod 1" + " kvota: " + kig.getDomacin()
                    + " OK(" + formatter.format(ki.getDomacin()) + ")"
                    + " u kladionici " + klad+"</p>");

        }
        if (ki.getGost() < kig.getGost()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost()
                    + " konacan ishod 2" + " kvota: " + kig.getGost()
                    + " OK(" + formatter.format(ki.getGost()) + ")"
                    + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " konacan ishod 2" + " kvota: " + kig.getGost()
                    + " OK(" + formatter.format(ki.getGost()) + ")"
                    + " u kladionici " + klad+"</p>");

        }
        if (ki.getNereseno() < kig.getNereseno()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost()
                    + " konacan ishod x" + " kvota: " + kig.getNereseno()
                    + " OK(" + formatter.format(ki.getNereseno()) + ")"
                    + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " konacan ishod x" + " kvota: " + kig.getNereseno()
                    + " OK(" + formatter.format(ki.getNereseno()) + ")"
                    + " u kladionici " + klad+"</p>");

        }
        return list;
    }

    private List<String> compareBothTeamsToScore(Match d, Match g, String klad)
            throws CloneNotSupportedException {
        List<String> list = new ArrayList<String>();
        ObaTimaDajuGol gg = (ObaTimaDajuGol) (d.get(OddNames.BothTeamsToScore.toString()));
        gg = (ObaTimaDajuGol) gg.getClone();
        ObaTimaDajuGol ggg = (ObaTimaDajuGol) (g.get(OddNames.BothTeamsToScore.toString()));

        if (gg.getGg() < ggg.getGg()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost()
                    + " oba tima daju gol: gg" + " kvota: " + ggg.getGg()
                    + " OK(" + formatter.format(gg.getGg()) + ")"
                    + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " oba tima daju gol: gg" + " kvota: " + ggg.getGg()
                    + " OK(" + formatter.format(gg.getGg()) + ")"
                    + " u kladionici " + klad+"</p>");

        }
        if (gg.getNg() < ggg.getNg()) {
            System.out.println(d.getDomacin() + " vs " + d.getGost()
                    + " oba tima daju gol: ng" + " kvota: " + ggg.getNg()
                    + " OK(" + formatter.format(gg.getNg()) + ")"
                    + " u kladionici " + klad + "\n");

            list.add("<p>"+d.getDomacin() + " vs " + d.getGost()
                    + " oba tima daju gol: ng" + " kvota: " + ggg.getNg()
                    + " OK(" + formatter.format(gg.getNg()) + ")"
                    + " u kladionici " + klad+"</p>");

        }
        return list;
    }

    List<String> compare(Match d, Match g, String klad) throws CloneNotSupportedException {
        List<String> listaKvota = new ArrayList<String>();
        if(d.get(OddNames.TotalGoals.toString()) != null && g.get(OddNames.TotalGoals.toString()) != null) {
            listaKvota.addAll(compareTotalGoals(d, g, klad));
            }

        if(d.get(OddNames.FullTime.toString()) != null && g.get(OddNames.FullTime.toString()) != null) {
            listaKvota.addAll(compareFullTimeResult(d, g, klad));
        }

        if(d.get(OddNames.BothTeamsToScore.toString()) != null && g.get(OddNames.BothTeamsToScore.toString()) != null) {
            listaKvota.addAll(compareBothTeamsToScore(d, g, klad));
        }
        return listaKvota;
    }
}
