package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.stats.games.SimpleStatsHandler;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 2/22/2017.
 */
public class UHCGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("uhc_team"));
        quests.add(HypixelQuest.fromBackend("uhc_solo"));
        quests.add(HypixelQuest.fromBackend("uhc_dm"));
        quests.add(HypixelQuest.fromBackend("uhc_weekly"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("uhc") || game.equalsIgnoreCase("Hypixel UHC") || game.equalsIgnoreCase("ultra hardcore") || game.equalsIgnoreCase("uhc champions");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.UHC.getDatabaseName());
    }
}
