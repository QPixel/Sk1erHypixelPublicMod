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
public class Walls2GameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("waller"));
        quests.add(HypixelQuest.fromBackend("walls_daily_kill"));
        quests.add(HypixelQuest.fromBackend("walls_daily_win"));
        quests.add(HypixelQuest.fromBackend("walls_weekly"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("walls") || game.equalsIgnoreCase("walls2") || game.equalsIgnoreCase("wa") || game.equalsIgnoreCase("the walls");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.WALLS.getDatabaseName());
    }
}
