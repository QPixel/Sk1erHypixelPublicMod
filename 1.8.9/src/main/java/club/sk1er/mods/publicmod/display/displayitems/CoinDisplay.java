package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Mitchell Katz on 8/9/2017.
 */
public class CoinDisplay implements IDisplayItem {
    private int ordinal;
    private JsonObject state;

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.COINS;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        String game = "Coins: " + NumberFormat.getNumberInstance(Locale.US).format(Sk1erPublicMod.getInstance().getDatSaving().getDailyInt("coins"));
        ElementRenderer.draw(starX, startY, game);
        return new Dimension(isConfig ? ElementRenderer.width(game) : 0, 10);
    }

    public CoinDisplay(int ordinal, JsonObject state) {
        this.ordinal = ordinal;
        this.state = state;
    }

    @Override
    public JsonObject getRaw() {
        return state;
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
