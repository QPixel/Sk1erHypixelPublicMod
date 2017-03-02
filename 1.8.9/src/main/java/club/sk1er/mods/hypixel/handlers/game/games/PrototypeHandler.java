package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 3/2/17.
 */
public class PrototypeHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        return new ArrayList<>();
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("prototype") || game.equalsIgnoreCase("Bed wars") || game.equalsIgnoreCase("Murder Mystery");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {

    }
}
