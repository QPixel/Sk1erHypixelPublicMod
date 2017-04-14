package club.sk1er.mods.hypixel.handlers.game.games;

import club.sk1er.html.Utils;
import club.sk1er.mods.hypixel.handlers.game.Sk1erGameHandler;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mitchellkatz on 2/14/17.
 */
public class PaintballGameHandler extends Sk1erGameHandler {
    @Override
    public List<HypixelQuest> getQuests() {
        return Arrays.asList(
                HypixelQuest.fromBackend("paintball_killer"),
                HypixelQuest.fromBackend("paintball_expert"),
                HypixelQuest.fromBackend("paintballer"));
    }

    @Override
    public boolean isGame(String game) {
        return game.equalsIgnoreCase("paintball") || game.equalsIgnoreCase("pb") || game.equalsIgnoreCase("paint") || game.equalsIgnoreCase("ball");
    }

    @Override
    public void handlePlayerStats(JSONObject player) {
        String backend = GameType.PAINTBALL.getDatabaseName();
        ChatUtils.sendMessage("Coins: " + Utils.get(player, "player#stats#" + backend + "#coins"));
        ChatUtils.sendMessage("Wins: " + Utils.get(player, "player#stats#" + backend + "#wins"));
        ChatUtils.sendMessage("Kills: " + Utils.get(player, "player#stats#" + backend + "#kills"));
        ChatUtils.sendMessage("Deaths: " + Utils.get(player, "player#stats#" + backend + "#deaths"));
        ChatUtils.sendMessage("K/D: " + (double) Utils.get(player, "player#stats#" + backend + "#kills") / (double) Utils.get(player, "player#stats#" + backend + "#deaths"));
        ChatUtils.sendMessage("Kill streaks: " + Utils.get(player, "player#stats#" + backend + "#killstreaks"));

    }
}
