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
public class TKRGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("gingerbread_bling_bling"));
        quests.add(HypixelQuest.fromBackend("gingerbread_maps"));
        quests.add(HypixelQuest.fromBackend("gingerbread_racer"));
        quests.add(HypixelQuest.fromBackend("gingerbread_mastery"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        return game.equals("tkr") || game.equalsIgnoreCase("turbo kart racers") || game.equalsIgnoreCase("gingerbread");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.GINGERBREAD.getDatabaseName());
    }
}
