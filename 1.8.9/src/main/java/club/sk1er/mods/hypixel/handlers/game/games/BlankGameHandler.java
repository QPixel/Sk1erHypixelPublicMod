package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 2/21/2017.
 */
public class BlankGameHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        return new ArrayList<>();
    }

    @Override
    public boolean isGame(String game) {
        return false;
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        ChatUtils.sendMessage("Black game handle stats reference!!!!");
    }
}
