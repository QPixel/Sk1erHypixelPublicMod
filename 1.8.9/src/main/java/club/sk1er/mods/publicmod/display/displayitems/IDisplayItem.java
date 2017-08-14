package club.sk1er.mods.publicmod.display.displayitems;

import com.google.gson.JsonObject;

import java.awt.*;

/**
 * Created by Mitchell Katz on 5/25/2017.
 */
public interface IDisplayItem {


    static IDisplayItem parse(DisplayItemType type, int ord, JsonObject item) {
        switch (type) {
            case PLAYER_COUNT:
                return new PlayerCountDisplay(item, ord);
            case WATCHDOG_DAY:
                return new WatchdogDayStats(ord, item);
            case WATCHDOG_MIN:
                return new WatchdogMinuteStats(ord, item);
            case WATCHDOG_TOTAL:
                return new WatchdogTotalStats(ord, item);
            case CURRENT_GAME:
                return new CurrentGame(item, ord);
            case COINS:
                return new CoinDisplay(ord, item);
            case EXP:
                return new ExpDisplay(ord, item);
            case STAFF_DAY:
                return new StaffBansLastDay(item,ord);
            case STAFF_TOTAL: return  new TotalStaffBans(item,ord);

            default:
                throw new IllegalArgumentException("No defined case for " + type);
        }
    }

    DisplayItemType getState();

    Dimension draw(int starX, int startY, boolean isConfig);

    JsonObject getRaw();

    int getOrdinal();

    void setOrdinal(int ordinal);
}


