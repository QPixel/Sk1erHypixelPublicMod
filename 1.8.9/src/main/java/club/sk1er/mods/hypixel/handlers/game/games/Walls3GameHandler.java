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
public class Walls3GameHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("mega_walls_player"));
        quests.add(HypixelQuest.fromBackend("mega_walls_win"));
        quests.add(HypixelQuest.fromBackend("mega_walls_kill"));
        quests.add(HypixelQuest.fromBackend("mega_walls_weekly"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("mega walls") || game.equalsIgnoreCase("mw") || game.equalsIgnoreCase("walls3");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.WALLS3.getDatabaseName());
    }
}
