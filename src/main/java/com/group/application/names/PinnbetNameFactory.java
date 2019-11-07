package com.group.application.names;

import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;

public class PinnbetNameFactory implements INameFactory {

    public String getGameName(String name) {
        String n = StringUtils.stripAccents(name);
        if(n.equalsIgnoreCase("KONACAN ISHOD")) {
            return OddNames.FullTime.toString();
        }else if(n.equalsIgnoreCase("UKUPNO GOLOVA")) {
            return OddNames.TotalGoals.toString();
        }else if(n.equalsIgnoreCase("Gol - ne gol")) {
            return OddNames.BothTeamsToScore.toString();
        }

        return "";
    }


    public String getGameNameOption(String name, String option) {
        String n = StringUtils.stripAccents(name);

        if(n.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
            if (option.equalsIgnoreCase("GG")) {
                return "gg";
            }else if (option.equalsIgnoreCase("NG")) {
                return "ng";
            }
        } else if(n.equalsIgnoreCase(OddNames.TotalGoals.toString())) {
            if (option.equalsIgnoreCase("0-2")) {
                return "0-2";
            }else if (option.equalsIgnoreCase("3+")) {
                return "3+";
            }
        }

        return "";
    }


    public String getGranica(String granica, String option) {
        return null;
    }
}
