package com.group.application.names;

import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;

public class Sport888NameFactory implements INameFactory{
    public String getGameName(String name) {
        if(name.equalsIgnoreCase("Full Time")){
            return OddNames.FullTime.toString();
        } else if(name.equalsIgnoreCase("Total Goals")){
            return OddNames.TotalGoals.toString();
        } else if(name.equalsIgnoreCase("Both Teams To Score")){
            return OddNames.BothTeamsToScore.toString();
        }
        return "";
    }

    public String getGameNameOption(String name, String option) {
        if(name.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())){
            if(option.equalsIgnoreCase("yes")){
                return "gg";
            } else if(option.equalsIgnoreCase("no")){
                return "ng";
            }
        } else if(name.equalsIgnoreCase(OddNames.TotalGoals.toString())){
            String[] opcije = StringUtils.splitByCharacterTypeCamelCase(option);
            if(opcije[1].equalsIgnoreCase("2")){
                if(opcije[0].equalsIgnoreCase("over")){
                    return "3+";
                }else if(opcije[0].equalsIgnoreCase("under")){
                    return "0-2";
                }
            }
        }
        return "";
    }

    public String getGranica(String granica, String option) {
        return null;
    }
}
