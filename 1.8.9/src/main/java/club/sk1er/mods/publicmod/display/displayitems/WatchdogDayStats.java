package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by mitchellkatz on 7/21/17.
 */
public class WatchdogDayStats implements IDisplayItem {
    private int ordinal;
    private JsonObject data;

    public WatchdogDayStats(int ordinal, JsonObject data) {
        this.ordinal = ordinal;
        this.data = data;
    }

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.WATCHDOG_DAY;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        JsonObject watchdogStats = Sk1erPublicMod.getInstance().getApiHandler().getWatchdogStats();
        String string =  "Bans last day: " + NumberFormat.getNumberInstance(Locale.US).format(watchdogStats.has("watchdog_rollingDaily") ? watchdogStats.get("watchdog_rollingDaily").getAsInt() : 0);
        ElementRenderer.draw(starX, startY, string);
        return new Dimension(isConfig ? ElementRenderer.width(string) : 0, 10);

    }

    @Override
    public JsonObject getRaw() {
        return data;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
