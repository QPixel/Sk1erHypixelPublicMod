package club.sk1er.mods.hypixel.config;


/**
 * Created by Mitchell Katz on 11/28/2016.
 */

public enum CValue {

    // CHAT
    GUILD_CHAT_MASTER_PREFIX("GUILD_CHAT_MASTER_PREFIX","&6[GM]&f","*","Guild Master prefix", "The prefix that is put before the guild master's name in guild chat when guild ranks are displayed"),
    GUILD_CHAT_OFFICER_PREFIX("GUILD_CHAT_OFFICER_PREFIX","&c[O]&f","*","Officer prefix", "The prefix that is put before an officers' name in guild chat when guild ranks are displayed"),
    GUILD_CHAT_MEMBER_PREFIX("GUILD_CHAT_MEMBER_PREFIX","&2[M]&f","*", "Member prefix", "The prefix that is put before a members' name in guild chat when guild ranks are displayed"),
    GUILD_CHAT_SHOW_RANK("GUILD_CHAT_SHOW_RANK", true, "true,false","Show ranks in guild chat","Decides if rank of the user in the guild is shown in guild chat"),
    GUILD_CHAT_CUSTOM_PREFIX("GUILD_CHAT_CUSTOM_PREFIX", "&aG&6u&ci&bl&ad &2> ", "*","Custom header to guild chat messages", "When toggled on, this will show before any guild message"),
    GUILD_CHAT_START("GUILD_CHAT","NAME","CUSTOM,DEFAULT,SIMPLE","Header to a guild chat message", "This is the first thing in the line of chat containing a guild chat message"),
    COLORED_GUILD_CHAT("COLORED_GUILD_CHAT",false,"true,false","Guild chat color","Should automatic broadcasts to guild chat be formatted with color"),
    PARTY_CHAT_HEADER("PARTY_CHAT_HEADER","&8P > ", "*","Header to party chat messages"),
    DISPLAY_GUILD_CHAT_BOOSTERS("DISPLAY_GUILD_CHAT_BOOSTERS",true,"true,false","Broadcast in guild chat when you queue a booster"),
    DISPLAY_GUILD_CHAT_LEVELS("DISPLAY_GUILD_CHAT_LEVELS",true,"true,false","Broadcast in guild chat when you level up"),
    DISPLAY_GUILD_CHAT_ACHIEVEMENTS("DISPLAY_GUILD_CHAT_ACHIEVEMENTS",true,"true,false","Broadcast in guild chat when you get an achievement"),

    //DISPLAY OPTIONS

    DISPLAY_LOCATION_ALIGN("DISPLAY_LOCATION_ALIGN","LEFT","LEFT,RIGHT","The text alignment. ","This will decide where the text will be rendered"),
    CUSTOM_DISPLAY_LOCATION_X("CUSTOM_DISPLAY_LOCATION_X",.8,"DOUBLE","The X location of the text","This decides where on the screen the text will render"),
    CUSTOM_DISPLAY_LOCATION_Y("CUSTOM_DISPLAY_LOCATION_Y",.01,"DOUBLE","The Y location of the text","This decides where on the screen the text will render"),
    DISPLAY_PRIMARY_COLOR("DISPLAY_PRIMARY_COLOR", "&b","*","The color of text in the display"),
    DISPLAY_SECONDARY_COLOR("DISPLAY_SECONDARY_COLOR", "&5","*","The color of text in the display"),
    //TOGGLEABLE FEATURES FOR DISPLAY

    DISPLAY_CURRENT_GAME("DISPLAY_CURRENT_GAME",true,"true,false","Show what game you are in"),
    DISPLAY_CURRENT_GAME_XP("DISPLAY_CURRENT_GAME_XP",true,"true,false","Show the XP for the game you are in"),
    DISPLAY_CURRENT_GAME_COINS("DISPLAY_CURRENT_GAME_COINS",true,"true,false","Show the coins for game you are in"),
    DISPLAY_BOOSTERS_DAY("DISPLAY_BOOSTERS_DAY",false,"true,false","Show your boosters that activate that day"),
    DISPLAY_ONLINE_PLAYERS("DISPLAY_ONLINE_PLAYERS",true,"true,false","Show online players"),
    DISPLAY_WATCHDOG_BANS("DISPLAY_WATCHDOG_BANS",false,"true,false","Display Watchdog bans"),
    WATCHDOG_BANS_MIN("WATCHDOG_BANS_MIN",true,"true,false","Display Watchdog bans in the last minute"),
    WATCHDOG_BANS_DAY("WATCHDOG_BANS_DAY",true,"true,false","Display Watchdog bans in the last day"),
    WATCHDOG_BANS_TOTAL("WATCHDOG_BANS_TOTAL",true,"true,false","Display total Watchdog bans"),
    PERCENT_TO_NEXT_GOAL("PERCENT_TO_NEXT_GOAL",true,"true,false","Display xp to your goal"),
    PERCENT_TO_NEXT_LEVEL("PERCENT_TO_NEXT_LEVEL",true,"true,false","Display xp to next level"),
    XP_EARNED_DAY("XP_EARNED_DAY",true,"true,false","Display the XP you earned today"),
    COINS_EARNED_DAY("COINS_EARNED_DAY",true,"false,true","Display the coins you earned today"),
    DISPLAY_RANKED_RATING("DISPLAY_RANKED_RATING",false,"true,false","Display your ranked rating"),
    DISPLAY_QUESTS("DISPLAY_QUESTS",true,"true,false","Display quests for game you are in"),
    DISPLAY_WATERMARK("DISPLAY_WATERMARK",true,"true,false","Display 'Sk1er Public Mod' and the version"),
    AUTO_GG("AUTOGG",false, "true,false","Enable auto gg (Disabled until admin approval)"),
    //CONSTANTS
    XP_LEVEL_GOAL("XP_LEVEL_GOAL",150,"INTEGER","Your XP Goal"),
    RANKED_RATING("RANKED_RATING_VALUE",0,"INTEGER","Your ranked rating"),
    SHOW_GUILD_CHAT("SHOW_GUILD_CHAT", true,"true,false","Show guild chat"),
    SHOW_PARTY_CHAT("SHOW_PARTY_CHAT",true,"true,false","Show party chat"),
    SHOW_DIRECT_MESSAGES("SHOW_DIRECT_MESSAGES",true,"true,false","Show direct messages"),
    SHOW_JOIN_LEAVE_MESSAGES("SHOW_JOIN_LEAVE_MESSAGES",true,"true,false","Show join leave messages"),

    ;


    private String name,allvals,dname,desc;
    private Object defaultvalue;
    CValue(String name, Object def, String alLVals, String dname) {
    this.name=name;
    this.defaultvalue=def;
    this.allvals=alLVals;
    this.dname=dname;
    this.desc="";
    }

    public String getDesc() {
        return desc;
    }

    CValue(String name, Object defaultvalue, String allvals, String dname, String desc) {
        this.name=name;
        this.defaultvalue=defaultvalue;
        this.allvals=allvals;
        this.dname=dname;
        this.desc=desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getDefaultvalue() {
        return defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    public String getAllvals() {
        return allvals;
    }

    public void setAllvals(String allvals) {
        this.allvals = allvals;
    }
    public boolean isValidState(String state) {
        if(allvals.equalsIgnoreCase("*")) {
            return true;
        }
        if(allvals.equalsIgnoreCase("DOUBLE")) {
            try {
                Double.parseDouble(state);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if(allvals.equalsIgnoreCase("INTEGER")) {
            try {
                Integer.parseInt(state);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        for(String s : allvals.split(",")) {
            if(s.equalsIgnoreCase(state))
                return true;
        }
        return false;
    }

    public String getDname() {
        return dname;
    }
}
