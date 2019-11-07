package com.group.application.match;

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;

public class MatchComparator implements Comparator<Match> {
    public int compare(Match o1, Match o2) {
        if(o1.getGost() != null && o2.getGost() != null && o1.getDomacin()!= null && o2.getDomacin() != null) {
            String[] t1m1 = StringUtils.split(o1.getDomacin().toLowerCase(), " ");
            String t1m2 = o2.getDomacin().toLowerCase();
            String[] t2m1 = StringUtils.split(o1.getGost().toLowerCase(), " ");
            String t2m2 = o2.getGost().toLowerCase();

            int tim1 = 0;
            int tim2 = 0;

            for (String s : t1m1) {
                if (StringUtils.contains(t1m2, s)) {
                    tim1 = 1;
                    break;
                }
            }
            for (String s : t2m1) {
                if (StringUtils.contains(t2m2, s)) {
                    tim2 = 1;
                    break;
                }
            }

            if (tim1 == 1 && tim2 == 1) {
                return 1;
            } else {
                return 0;
            }
        }else{
            return 0;
        }

    }

    public int compareMatches(Match o1, Match o2){
        if(o1.getGost() != null && o2.getGost() != null && o1.getDomacin()!= null && o2.getDomacin() != null) {
            if (o1.getDomacin().equalsIgnoreCase(o2.getDomacin()) && o1.getGost().equalsIgnoreCase(o2.getGost())) {
                return 1;
            }
            else return 0;
        }
        else return 0;
    }

}
