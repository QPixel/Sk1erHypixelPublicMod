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
public class SmashHeroesGameHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("supersmash_solo_win"));
        quests.add(HypixelQuest.fromBackend("supersmash_solo_kills"));
        quests.add(HypixelQuest.fromBackend("supersmash_team_win"));
        quests.add(HypixelQuest.fromBackend("supersmash_team_kills"));
        quests.add(HypixelQuest.fromBackend("supersmash_weekly_kills"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("smash") || game.equalsIgnoreCase("smash heroes") || game.equalsIgnoreCase("supersmash");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.SUPER_SMASH.getDatabaseName());
    }
}
