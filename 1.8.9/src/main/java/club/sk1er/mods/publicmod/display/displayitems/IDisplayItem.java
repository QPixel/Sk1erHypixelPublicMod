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
                return new PlayerCountDisplay(item,ord);
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


