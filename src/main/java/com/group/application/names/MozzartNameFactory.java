package com.group.application.names;

import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;

public class MozzartNameFactory implements INameFactory {

    public String getGameName(String name) {
        String n = StringUtils.stripAccents(name);
        if(n.equalsIgnoreCase("KONACAN ISHOD")) {
            return OddNames.FullTime.toString();
        }else if(n.equalsIgnoreCase("Ukupno golova na mecu")) {
            return OddNames.TotalGoals.toString();
        }else if(n.equalsIgnoreCase("Oba tima daju gol")) {
            return OddNames.BothTeamsToScore.toString();
        }

        return "";
    }

    public String getGameNameOption(String name, String option) {
        String n = StringUtils.stripAccents(name);

        if(n.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
            if (option.equalsIgnoreCase("da") || option.equalsIgnoreCase("GG")) {
                return "gg";
            }else if (option.equalsIgnoreCase("ne") || option.equalsIgnoreCase("NG")) {
                return "ng";
            }
        }

        return "";
    }


    public String getGranica(String granica, String option) {
        return null;
    }
}
