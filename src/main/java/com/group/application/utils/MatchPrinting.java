package com.group.application.utils;

import com.group.application.match.Match;
import com.group.application.match.MatchComparator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatchPrinting {


    public static List<String> printMatches(List<Match> utakmice, String kladionica){
        List<String> list = new ArrayList<>();
        System.out.println("Kladionica "+kladionica+": ");
        for(Match u : utakmice){
            System.out.println(u.toString());
            list.add(u.toString());
        }
        return list;
    }

    public static List<String> compareMatchesAndPrintOdds(List<Match> utakmice1,
                                                          List<Match> utakmice2, String kladionica2)
            throws IOException, CloneNotSupportedException {
        MatchComparator mc = new MatchComparator();
        OddComparator oddComparator = new OddComparator();
        List<String> list = new ArrayList<String>();
        for(Match u: utakmice1) {
            for (Match u1 : utakmice2) {
                if (mc.compareMatches(u, u1) == 1) {
                    list.addAll(oddComparator.compare(u, u1, kladionica2));
                    break;
                }
            }
        }
        return list;
    }
}
