package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;

import java.awt.*;

/**
 * Created by mitchellkatz on 7/21/17.
 */
public class PlayerCountDisplay implements IDisplayItem {
    private JsonObject data;
    private int ordinal;

    public PlayerCountDisplay(JsonObject data, int ordinal) {
        this.data = data;
        this.ordinal = ordinal;
    }

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.PLAYER_COUNT;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        JsonObject watchdogStats = Sk1erPublicMod.getInstance().getApiHandler().getWatchdogStats();
        String string = "Player count: " + (watchdogStats.has("players") ? watchdogStats.get("players").getAsInt() : 0);
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
