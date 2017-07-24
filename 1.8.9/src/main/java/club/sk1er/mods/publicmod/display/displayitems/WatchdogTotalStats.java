package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;

import java.awt.*;

/**
 * Created by mitchellkatz on 7/21/17.
 */
public class WatchdogTotalStats implements IDisplayItem {
    private int ordinal;
    private JsonObject data;

    public WatchdogTotalStats(int ordinal, JsonObject data) {
        this.ordinal = ordinal;
        this.data = data;
    }

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.WATCHDOG_TOTAL;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        JsonObject watchdogStats = Sk1erPublicMod.getInstance().getApiHandler().getWatchdogStats();
        String string =  "Total Bans: " + (watchdogStats.has("watchdog_total") ? watchdogStats.get("watchdog_total").getAsInt() : 0);
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
