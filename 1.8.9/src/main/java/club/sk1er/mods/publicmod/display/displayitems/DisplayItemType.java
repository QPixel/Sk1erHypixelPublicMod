package club.sk1er.mods.publicmod.display.displayitems;

/**
 * Created by Mitchell Katz on 5/25/2017.
 */
public enum DisplayItemType {
    WATCHDOG_TOTAL("Total watchdog bans"),
    WATCHDOG_MIN("Watchdog bans last minute"),
    WATCHDOG_DAY("Watchdog bans last day"),
    CURRENT_GAME("Current Game"),
    COINS("Coin Counter"),
    EXP("Exp Counter"),
    PLAYER_COUNT("Player count");

    private String name;

    DisplayItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
