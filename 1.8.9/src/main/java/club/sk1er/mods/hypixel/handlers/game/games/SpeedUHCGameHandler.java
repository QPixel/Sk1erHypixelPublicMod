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
public class SpeedUHCGameHandler extends Sk1erGameHandler{
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("normal_brawler"));
        quests.add(HypixelQuest.fromBackend("insane_brawler"));
        quests.add(HypixelQuest.fromBackend("hunting_season"));
        quests.add(HypixelQuest.fromBackend("uhc_addict"));
        quests.add(HypixelQuest.fromBackend("uhc_madness"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("speed") || game.equalsIgnoreCase("speed uhc");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        SimpleStatsHandler.handleStatic(player, GameType.SPEED_UHC.getDatabaseName());
    }
}
