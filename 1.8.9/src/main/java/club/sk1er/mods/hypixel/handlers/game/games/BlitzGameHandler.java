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
public class BlitzGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("blitz_game_of_the_day"));
        quests.add(HypixelQuest.fromBackend("blitz_win"));
        quests.add(HypixelQuest.fromBackend("blitz_kills"));
        quests.add(HypixelQuest.fromBackend("blitz_weekly_master"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("sg") || game.equalsIgnoreCase("blitz") || game.equalsIgnoreCase("blitzsg") || game.equalsIgnoreCase("survivalgames") || game.equalsIgnoreCase("blitz sg");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.SURVIVAL_GAMES.getDatabaseName());
    }
}
