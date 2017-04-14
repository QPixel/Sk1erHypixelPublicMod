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
public class WarlordsGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("warlords_ctf"));
        quests.add(HypixelQuest.fromBackend("warlords_win"));
        quests.add(HypixelQuest.fromBackend("warlords_tdm"));
        quests.add(HypixelQuest.fromBackend("warlords_domination"));
        quests.add(HypixelQuest.fromBackend("warlords_dedication"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("war") || game.equalsIgnoreCase("warlords") || game.equalsIgnoreCase("battleground") || game.equalsIgnoreCase("battle");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.BATTLEGROUND.getDatabaseName());
    }
}
