package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 2/22/2017.
 */
public class TNTGamesHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("tnt_daily_play"));
        quests.add(HypixelQuest.fromBackend("tnt_daily_win"));
        quests.add(HypixelQuest.fromBackend("tnt_weekly_play"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("tnt") || game.equalsIgnoreCase("tntgames") || game.equalsIgnoreCase("tnt games") || game.equalsIgnoreCase("explosive") || game.equalsIgnoreCase("explosive games") || game.equalsIgnoreCase("the tnt games");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        //TODO add specific handler
        ChatUtils.sendMessage("This is not finished");
    }
}
