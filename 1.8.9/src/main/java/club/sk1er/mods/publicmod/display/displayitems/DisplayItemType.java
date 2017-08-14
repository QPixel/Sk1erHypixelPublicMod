package club.sk1er.mods.publicmod.display.displayitems;

/**
 * Created by Mitchell Katz on 5/25/2017.
 */
public enum DisplayItemType {
    WATCHDOG_TOTAL("Total watchdog bans"),
    WATCHDOG_MIN("Watchdog Bans Last Minute"),
    WATCHDOG_DAY("Watchdog Bans Last Day"),
    CURRENT_GAME("Current Game"),
    COINS("Coin Counter"),
    EXP("Exp Counter"),
    STAFF_DAY("Staff Bans Last Day"),
    STAFF_TOTAL("Total Staff Bans"),
    PLAYER_COUNT("Player count");

    private String name;

    DisplayItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
