package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.stats.games.SimpleStatsHandler;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 2/13/17.
 */
public class SkyWarsGameHandler extends Sk1erGameHandler{

    public SkyWarsGameHandler() {
    }
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("skywars_solo_win"));
        quests.add(HypixelQuest.fromBackend("skywars_team_win"));
        quests.add(HypixelQuest.fromBackend("skywars_team_kills"));
        quests.add(HypixelQuest.fromBackend("skywars_solo_kills"));
        quests.add(HypixelQuest.fromBackend("skywars_weekly_kills"));
        return quests;
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("skywars") || game.equalsIgnoreCase("sw");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.SKYWARS.getDatabaseName());
    }

}
