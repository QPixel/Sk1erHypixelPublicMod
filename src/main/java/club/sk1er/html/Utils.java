
package club.sk1er.html;


import club.sk1er.mods.hypixel.C;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;

public class Utils {
    private static HashMap<String, String> col = new HashMap<String, String>();

    public static void init() {
        col.put("0", "#000000");
        col.put("1", "#0000AA");
        col.put("2", "#008000");
        col.put("3", "#00AAAA");
        col.put("4", "#AA0000");
        col.put("5", "#AA00AA");
        col.put("6", "#FFAA00");
        col.put("7", "#AAAAAA");
        col.put("8", "#555555");
        col.put("9", "#5555FF");
        col.put("a", "#3CE63C");
        col.put("b", "#3CE6E6");
        col.put("c", "#FF5555");
        col.put("d", "#FF55FF");
        col.put("e", "#FFFF55");
        col.put("f", "#FFFFFF");
    }

    public static String comma(String s) {
        double amount = Double.parseDouble(s);
        DecimalFormat formatter = new DecimalFormat("#,###");

        return formatter.format(amount);
    }

    public static String numeral(int amount) {
        String s = "";
        switch (amount) {
            case 0:
                s = "0";
                break;
            case 1:
                s = "I";
                break;
            case 2:
                s = "II";
                break;
            case 3:
                s = "III";
                break;
            case 4:
                s = "IV";
                break;
            case 5:
                s = "V";
                break;
            case 6:
                s = "VI";
                break;
            case 7:
                s = "VII";
                break;
            case 8:
                s = "VIII";
                break;
            case 9:
                s = "IX";
                break;
            case 10:
                s = "X";
                break;


        }
        return s;
    }

    public static String getCoinMultiplier(int level) {
        String s = "";
        if (level >= 50 && level < 100) {
            s = "5";
        }
        if (level >= 0 && level < 5) {
            s = "1";
        }
        if (level >= 5 && level < 10) {
            s = "1.5";
        }
        if (level >= 10 && level < 15) {
            s = "2";
        }
        if (level >= 15 && level < 20) {
            s = "2.5";
        }
        if (level >= 20 && level < 25) {
            s = "3";
        }
        if (level >= 25 && level < 30) {
            s = "3.5";
        }
        if (level >= 30 && level < 45) {
            s = "4";
        }
        if (level >= 45 && level < 50) {
            s = "4.5";
        }


        if (level >= 100 && level < 125) {
            s = "5.5";
        }
        if (level >= 125 && level < 150) {
            s = "6";
        }
        if (level >= 150 && level < 200) {
            s = "6.5";
        }
        if (level >= 200 && level < 250) {
            s = "7";
        }
        if (level >= 250) {
            s = "8";
        }
        return s;
    }


    public static String buildRatio(int a, int b) {

        double c = (double) a;
        double d = (double) b;
        if (a + b == 0) {
            return "0";
        }
        if (b == 0) {
            return "&infin;";
        }
        if (a == 0) {
            return "0";
        }
        double e = c / d;
        String s = e + "";
        if (s.indexOf(".") + 4 > s.length()) {
            if (s.indexOf(".") + 3 > s.length()) {
                if (s.indexOf(".") + 2 > s.length()) {
                    if (s.indexOf(".") + 1 > s.length()) {
                        return s.substring(0, s.indexOf("."));
                    } else {
                        return s.substring(0, s.indexOf(".") + 1);
                    }
                } else {
                    return s.substring(0, s.indexOf(".") + 2);
                }
            } else {
                return s.substring(0, s.indexOf(".") + 3);
            }
        }
        return s.substring(0, s.indexOf(".") + 4);
    }

    public static String MonthName(String input) {
        String tmp = "";
        switch (input) {
            case "01":
                tmp = "January";
                break;
            case "02":
                tmp = "February";
                break;
            case "03":
                tmp = "March";
                break;
            case "04":
                tmp = "April";
                break;
            case "05":
                tmp = "May";
                break;
            case "06":
                tmp = "June";
                break;
            case "07":
                tmp = "July";
                break;
            case "08":
                tmp = "August";
                break;
            case "09":
                tmp = "September";
                break;
            case "10":
                tmp = "October";
                break;
            case "11":
                tmp = "November";
                break;
            case "12":
                tmp = "December";
                break;

        }
        return tmp;

    }

    public static int get(JSONObject tmp, String path) {
        try {
            if (path.contains("#")) {
                int max = path.split("#").length;
                int cur = 0;
                JSONObject curent = tmp;
                for (String s : path.split("#")) {
                    if (cur >= max - 1) {
                        return (curent.has(s) ? curent.getInt(s) : 0);
                    } else {
                        curent = curent.has(s) ? curent.getJSONObject(s) : new JSONObject();
                    }
                    cur++;
                }
            } else {
                return tmp.has(path) ? tmp.getInt(path) : 0;

            }

            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static Object getObj(JSONObject ins, String loc) {
        if (loc.contains("#")) {
            String o = loc.split("#")[0];
            String v = loc.split("#")[1];
            if (ins.has(o)) {
                JSONObject tmp = ins.getJSONObject(o);
                return tmp.has(v) ? tmp.get(v) : "";
            } else {
                return "";
            }
        } else {
            return ins.has(loc) ? ins.getInt(loc) : 0;
        }
    }


    public static String getFormatedName(JSONObject player) {
        if (player == null)
            return "ERROR - null";
        if (!player.has("player")) {
            return "ERROR - does not contain Player";
        }
        JSONObject jo = player.getJSONObject("player");
        boolean s = false;


        if (jo.has("rank") && !jo.getString("rank").equalsIgnoreCase("NORMAL")) {
            if (jo.getString("rank").equalsIgnoreCase("admin")) {
                return "" + C.COLOR_CODE_SYMBOL + "c[ADMIN]" + jo.getString("displayname");
            } else if (jo.getString("rank").equalsIgnoreCase("MODERATOR")) {
                return "" + C.COLOR_CODE_SYMBOL + "2[MOD] " + jo.getString("displayname");
            } else if (jo.getString("rank").equalsIgnoreCase("helper")) {
                return "" + C.COLOR_CODE_SYMBOL + "1[HELPER] " + jo.getString("displayname");
            } else if (jo.getString("rank").equalsIgnoreCase("YOUTUBER")) {
                return "" + C.COLOR_CODE_SYMBOL + "6[YT] " + jo.getString("displayname");
            }
        } else {
            if (jo.has("newPackageRank")) {
                if (jo.getString("newPackageRank").equalsIgnoreCase("VIP")) {
                    return "" + C.COLOR_CODE_SYMBOL + "a[VIP] " + jo.getString("displayname");
                } else if (jo.getString("newPackageRank").equalsIgnoreCase("VIP_PLUS")) {
                    return "" + C.COLOR_CODE_SYMBOL + "a[VIP" + C.COLOR_CODE_SYMBOL + "6+" + C.COLOR_CODE_SYMBOL + "a] " + jo.getString("displayname");
                } else if (jo.getString("newPackageRank").equalsIgnoreCase("mvp")) {
                    return "" + C.COLOR_CODE_SYMBOL + "b[MVP] " + jo.getString("displayname");
                } else if (jo.getString("newPackageRank").equalsIgnoreCase("mvp_plus")) {
                    if (jo.has("rankPlusColor")) {
                        String raw = jo.getString("rankPlusColor");
                        String color = handleRankPlus(raw);
                        return "" + C.COLOR_CODE_SYMBOL + "b[MVP" + color + "+" + C.COLOR_CODE_SYMBOL + "b] " + jo.getString("displayname");

                    }
                    return "" + C.COLOR_CODE_SYMBOL + "b[MVP" + C.COLOR_CODE_SYMBOL + "c+" + C.COLOR_CODE_SYMBOL + "b] " + jo.getString("displayname");
                }
            }
            if (jo.has("packageRank")) {
                if (jo.getString("packageRank").equalsIgnoreCase("VIP")) {
                    return "" + C.COLOR_CODE_SYMBOL + "a[VIP] " + jo.getString("displayname");

                } else if (jo.getString("packageRank").equalsIgnoreCase("VIP_PLUS")) {
                    return "" + C.COLOR_CODE_SYMBOL + "a[VIP" + C.COLOR_CODE_SYMBOL + "6+" + C.COLOR_CODE_SYMBOL + "a] " + jo.getString("displayname");

                } else if (jo.getString("packageRank").equalsIgnoreCase("mvp")) {
                    return "" + C.COLOR_CODE_SYMBOL + "b[MVP] " + jo.getString("displayname");

                } else if (jo.getString("packageRank").equalsIgnoreCase("mvp_plus")) {
                    if (jo.has("rankPlusColor")) {
                        String raw = jo.getString("rankPlusColor");
                        String color = handleRankPlus(raw);

                        return "" + C.COLOR_CODE_SYMBOL + "b[MVP" + color + "+" + C.COLOR_CODE_SYMBOL + "b] " + jo.getString("displayname");

                    }
                    return "" + C.COLOR_CODE_SYMBOL + "b[MVP" + C.COLOR_CODE_SYMBOL + "c+" + C.COLOR_CODE_SYMBOL + "b] " + jo.getString("displayname");
                }
            }

        }
        return "" + C.COLOR_CODE_SYMBOL + "7" + jo.getString("displayname");
    }

    private static String handleRankPlus(String raw) {
        String color = "";
        switch (raw.toLowerCase()) {
            case "green":
                color = "" + C.COLOR_CODE_SYMBOL + "a";
                break;
            case "gold":
                color = "" + C.COLOR_CODE_SYMBOL + "6";
                break;
            case "light_purple":
                color = "" + C.COLOR_CODE_SYMBOL + "d";
                break;
            case "yellow":
                color = "" + C.COLOR_CODE_SYMBOL + "e";
                break;
            case "white":
                color = "" + C.COLOR_CODE_SYMBOL + "f";
                break;
            case "blue":
                color = "" + C.COLOR_CODE_SYMBOL + "9";
                break;
            case "dark_green":
                color = "" + C.COLOR_CODE_SYMBOL + "2";
                break;
            case "dark_red":
                color = "" + C.COLOR_CODE_SYMBOL + "4";
                break;
            case "dark_aqua":
                color = "" + C.COLOR_CODE_SYMBOL + "3";
                break;
            case "dark_purple":
                color = "" + C.COLOR_CODE_SYMBOL + "5";
                break;
            case "dark_gray":
                color = "" + C.COLOR_CODE_SYMBOL + "7";
                break;
            case "black":
                color = "" + C.COLOR_CODE_SYMBOL + "0";
                break;
        }
        return color;
    }
}
