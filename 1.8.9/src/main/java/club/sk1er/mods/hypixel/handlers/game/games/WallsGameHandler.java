package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.stats.games.SimpleStatsHandler;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mitchellkatz on 2/14/17.
 */
public class WallsGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        return Arrays.asList(HypixelQuest.fromBackend("walls_daily_kill"),
                HypixelQuest.fromBackend("walls_daily_win"),
                HypixelQuest.fromBackend("walls_daily_play"),
                HypixelQuest.fromBackend("walls_weekly"));
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("walls2") || game.equalsIgnoreCase("walls") || game.startsWith("wa");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.WALLS.getDatabaseName());

    }
}
