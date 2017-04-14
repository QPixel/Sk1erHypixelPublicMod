package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.stats.games.SimpleStatsHandler;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 2/21/2017.
 */
public class SkyClashGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("skyclash_play_games"));
        quests.add(HypixelQuest.fromBackend("skyclash_kills"));
        quests.add(HypixelQuest.fromBackend("skyclash_play_points"));
        quests.add(HypixelQuest.fromBackend("skyclash_void"));
        quests.add(HypixelQuest.fromBackend("skyclash_weekly_kills"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        game = game.toLowerCase();
        return game.equals("sc") || game.equalsIgnoreCase("skyclash") || game.startsWith("skyc");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        //TODO add more to this
        SimpleStatsHandler.handleStatic(player, GameType.SKYCLASH.getDatabaseName());
    }
}
