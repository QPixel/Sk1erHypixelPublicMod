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
public class ArcadeGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("arcade_gamer"));
        quests.add(HypixelQuest.fromBackend("arcade_winner"));
        quests.add(HypixelQuest.fromBackend("arcade_specialist"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        game = game.toLowerCase();
        return game.contains("arcade") || game.contains("arcade games") || game.contains("hypixel says");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        ChatUtils.sendMessage("Not finished :(");
    }
}
