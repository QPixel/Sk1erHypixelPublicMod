package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.html.Utils;
import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 2/13/17.
 */
public class QuakecraftGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        List<HypixelQuest> quests = new ArrayList<>();
        quests.add(HypixelQuest.fromBackend("quake_daily_kill"));
        quests.add(HypixelQuest.fromBackend("quake_daily_play"));
        quests.add(HypixelQuest.fromBackend("quake_daily_win"));
        quests.add(HypixelQuest.fromBackend("quake_weekly_play"));
        return quests;

    }

    @Override
    public boolean isGame(String game) {
        String[] aliases = {"quake", "quakecraft", "qc"};
        for (String s : aliases) {
            if (game.toLowerCase().equals(s)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        ChatUtils.sendMessage("Coins: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#coins"));
        ChatUtils.sendMessage("Solo kills: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#kills"));
        ChatUtils.sendMessage("Solo wins: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#wins"));
        ChatUtils.sendMessage("Solo kill streaks: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#killstreaks"));
        ChatUtils.sendMessage("Solo K/D: " + Utils.buildRatio(Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#kills"), Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#wins")));
        ChatUtils.sendMessage("Team kills: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#kills_teams"));
        ChatUtils.sendMessage("Team wins: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#wins_teams"));
        ChatUtils.sendMessage("Team kill streaks: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#killstreaks_teams"));
        ChatUtils.sendMessage("Team kills: " + Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#kills_teams"));
        ChatUtils.sendMessage("Team K/D: " + Utils.buildRatio(Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#kills_teams"), Utils.get(player, "player#" + GameType.QUAKECRAFT.getDatabaseName() + "#wins_team")));
    }


}
