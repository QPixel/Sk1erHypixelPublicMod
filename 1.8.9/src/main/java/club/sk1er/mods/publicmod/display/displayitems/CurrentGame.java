package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;
import net.hypixel.api.GameType;

import java.awt.*;

/**
 * Created by Mitchell Katz on 8/9/2017.
 */
public class CurrentGame implements IDisplayItem {
    private JsonObject data;
    private int ordinal;

    public CurrentGame(JsonObject data, int ordinal) {
        this.data = data;
        this.ordinal = ordinal;
    }

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.CURRENT_GAME;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        GameType currentGame = Sk1erPublicMod.getInstance().getCurrentGame();
        String game = "Current Game: " + currentGame.getName();
        ElementRenderer.draw(starX, startY, game);
        return new Dimension(isConfig ? ElementRenderer.width(game) : 0, 10);
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
