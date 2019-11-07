package com.group.application.names;

import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;

public class Bet365NameFactory implements INameFactory {

    @Override
    public String getGameName(String name) {
        if(name != null) {
            String n = StringUtils.stripAccents(name);
            if (n.equalsIgnoreCase("Full Time Result")) {
                return OddNames.FullTime.toString();
            } else if (n.equalsIgnoreCase("Goals Over/Under")) {
                return OddNames.TotalGoals.toString();
            } else if (n.equalsIgnoreCase("Both Teams to Score")) {
                return OddNames.BothTeamsToScore.toString();
            }
        }

        return "";
    }

    @Override
    public String getGameNameOption(String name, String option) {
        if(name != null && option != null) {
            String n = StringUtils.stripAccents(name);

            if (n.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
                if (option.equalsIgnoreCase("yes")) {
                    return "gg";
                } else if (option.equalsIgnoreCase("no")) {
                    return "ng";
                }
            } else if (n.equalsIgnoreCase(OddNames.TotalGoals.toString())) {
                if (option.equalsIgnoreCase("under")) {
                    return "0-2";
                } else if (option.equalsIgnoreCase("over")) {
                    return "3+";
                }
            }
        }
        return "";
    }

    @Override
    public String getGranica(String granica, String option) {
        return null;
    }
}
