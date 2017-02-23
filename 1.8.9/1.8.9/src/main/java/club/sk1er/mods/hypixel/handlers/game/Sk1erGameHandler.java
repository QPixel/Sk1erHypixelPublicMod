package club.sk1er.mods.hypixel.handlers.game;

import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by mitchellkatz on 2/13/17.
 */
public abstract class Sk1erGameHandler {

    public abstract List<HypixelQuest> getQuests();
    public abstract boolean isGame(String game);
    public abstract void handlePlayerStats(JSONObject player);

}
