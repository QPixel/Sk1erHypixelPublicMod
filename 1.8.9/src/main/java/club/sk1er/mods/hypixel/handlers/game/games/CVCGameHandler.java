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
public class CVCGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("cvc_win_daily_normal"));
        quests.add(HypixelQuest.fromBackend("cvc_kill_daily_normal"));
        quests.add(HypixelQuest.fromBackend("cvc_win_daily_deathmatch"));
        quests.add(HypixelQuest.fromBackend("cvc_kill"));
        quests.add(HypixelQuest.fromBackend("cvc_kill_weekly"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        game = game.toLowerCase();
        return game.equalsIgnoreCase("cvc") || game.equals("cops and crims") || game.equals("cac") || game.equalsIgnoreCase("mcgo");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.MCGO.getDatabaseName());
    }
}
