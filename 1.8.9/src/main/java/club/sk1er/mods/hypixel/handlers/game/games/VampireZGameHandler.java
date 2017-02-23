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
public class VampireZGameHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("vampirez_daily_play"));
        quests.add(HypixelQuest.fromBackend("vampirez_daily_kill"));
        quests.add(HypixelQuest.fromBackend("vampirez_daily_win"));
        quests.add(HypixelQuest.fromBackend("vampirez_weekly_wn"));
        quests.add(HypixelQuest.fromBackend("vampirez_weekly_kill"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("vampirez") || game.equalsIgnoreCase("vamp") || game.equalsIgnoreCase("vampz") || game.equalsIgnoreCase("vampire");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.VAMPIREZ.getDatabaseName());
    }
}
