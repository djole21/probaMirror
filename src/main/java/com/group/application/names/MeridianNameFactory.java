package com.group.application.names;

import com.group.application.utils.OddNames;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class MeridianNameFactory implements INameFactory {

    public String getGameName(String name) {
        if(name != null) {
            String n = StringUtils.stripAccents(name);
            if (n.equalsIgnoreCase("KONACAN ISHOD")) {
                return OddNames.FullTime.toString();
            } else if (n.equalsIgnoreCase("UKUPNO") ||
                    n.equalsIgnoreCase("Uk. Golova") || n.equalsIgnoreCase("UKUPNO GOLOVA")) {
                return OddNames.TotalGoals.toString();
            } else if (n.equalsIgnoreCase("Oba tima daju gol") || n.equalsIgnoreCase("GG/NG")) {
                return OddNames.BothTeamsToScore.toString();
            }
        }
        return "";
    }


    public String getGameNameOption(String name, String option) {
        if(name != null && option != null) {
            String n = StringUtils.stripAccents(name);

            if (n.equalsIgnoreCase(OddNames.BothTeamsToScore.toString())) {
                if (option.equalsIgnoreCase("da") || option.equalsIgnoreCase("GG")) {
                    return "gg";
                } else if (option.equalsIgnoreCase("ne") || option.equalsIgnoreCase("NG")) {
                    return "ng";
                }
            } else if (n.equalsIgnoreCase(OddNames.TotalGoals.toString())) {
                if (option.equalsIgnoreCase("manje") || option.equalsIgnoreCase("0-2")) {
                    return "0-2";
                } else if ("VISE".equalsIgnoreCase(StringUtils.stripAccents(option)) || option.equalsIgnoreCase("3+")) {
                    return "3+";
                }
            }
        }
        return "";
    }

    public String getGranica(String granica, String option){
        if(granica != null && option != null) {
            String opt = StringUtils.stripAccents(option);
            String[] granice = new String[2];
            if (NumberUtils.isCreatable(granica)) {
                int gr = (int) Double.parseDouble(granica);

                switch (gr) {
                    case 0:
                        granice[0] = "0";
                        granice[1] = "1+";
                        break;
                    case 1:
                        granice[0] = "0-1";
                        granice[1] = "2+";
                        break;
                    case 2:
                        granice[0] = "0-2";
                        granice[1] = "3+";
                        break;
                    case 3:
                        granice[0] = "0-3";
                        granice[1] = "4+";
                        break;
                    case 4:
                        granice[0] = "0-4";
                        granice[1] = "5+";
                        break;
                    case 5:
                        granice[0] = "0-5";
                        granice[1] = "6+";
                        break;
                    case 6:
                        granice[0] = "0-6";
                        granice[1] = "7+";
                        break;
                }

                //System.out.println(gr + " + " + granice[0] + " + "+granice[1] + " + " + opt);

                if (opt.equalsIgnoreCase("vise")) {
                    return granice[1];
                } else if (opt.equalsIgnoreCase("manje")) {
                    return granice[0];
                }
            }
        }
        return "";
    }
}
