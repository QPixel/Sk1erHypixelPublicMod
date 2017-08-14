package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Mitchell Katz on 8/13/2017.
 */
public class TotalStaffBans implements IDisplayItem {
    private JsonObject data;
    private int ordinal = 0;

    public TotalStaffBans(JsonObject data, int ordinal) {
        this.data = data;
        this.ordinal = ordinal;
    }

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.STAFF_TOTAL;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        JsonObject watchdogStats = Sk1erPublicMod.getInstance().getApiHandler().getWatchdogStats();
        String string = "Total Staff Bans: " + NumberFormat.getNumberInstance(Locale.US).format(watchdogStats.has("staff_total") ? watchdogStats.get("staff_total").getAsInt() : 0);
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
