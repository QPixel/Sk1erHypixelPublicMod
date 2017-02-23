package club.sk1er.mods.hypixel.handlers.stats.games;

import club.sk1er.html.Utils;
import club.sk1er.mods.hypixel.handlers.stats.ApiGameStatsHandler;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import org.json.JSONObject;

/**
 * Created by mitchellkatz on 1/25/17.
 */
public class SimpleStatsHandler extends ApiGameStatsHandler{
    private String name;
    public SimpleStatsHandler(String name) {
        this.name=name;
    }

    public static void handleStatic(JSONObject player, String backend) {
        ChatUtils.sendMessage("Coins: " + Utils.get(player, "player#stats#"+backend+"#coins"));
        ChatUtils.sendMessage("Wins: " + Utils.get(player, "player#stats#"+backend+"#wins"));
        ChatUtils.sendMessage("Kills: " + Utils.get(player, "player#stats#"+backend+"#kills"));
        ChatUtils.sendMessage("Deaths: " + Utils.get(player, "player#stats#"+backend+"#deaths"));
        ChatUtils.sendMessage("Losses: " + Utils.get(player, "player#stats#"+backend+"#losses"));
        ChatUtils.sendMessage("W/L: " + (double) Utils.get(player, "player#stats#"+backend+"#wins") / (double) Utils.get(player, "player#stats#"+backend+"#losses"));
        ChatUtils.sendMessage("K/D: " + (double) Utils.get(player, "player#stats#"+backend+"#kills") / (double) Utils.get(player, "player#stats#"+backend+"#deaths"));

    }
    @Override
    public void handle(JSONObject player) {
       handleStatic(player, name);;
    }
}
