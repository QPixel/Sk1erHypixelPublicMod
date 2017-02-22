package club.sk1er.mods.hypixel.commands;

import club.sk1er.html.Utils;
import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.hypixel.api.GameType;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandStats extends CommandBase {

    private Sk1erPublicMod mod;

    public CommandStats(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "stats";
    }

    @Override
    public void processCommand(final ICommandSender sender, final String[] args) throws CommandException {
        Multithreading.runAsync(() -> {
            try {
                if (args.length == 0) {
                    ChatUtils.sendMessage(getCommandUsage(sender));
                    return;
                }
                if (args.length == 1) {
                    String playername = args[0];
                    JSONObject raw = mod.getApiHandler().pullPlayerProfile(playername);
                    if (raw.optBoolean("success")) {
                        ChatUtils.sendMessage(raw.toString());
                        Arrays.stream(raw.getNames()).forEach(ChatUtils::sendMessage);
                        ChatUtils.sendMessage("Player '" + playername + "' could not be found!");
                        return;
                    }
//                ChatUtils.sendMessage(raw.toString());
                    ChatUtils.sendMessage("Loading stats for: " + Utils.getFormatedName(raw));
                    ChatUtils.sendMessage("Network Level: " + ((Utils.get(raw, "player#networkLevel")) + 1));
                    ChatUtils.sendMessage("Karma: " + Utils.get(raw, "player#karma"));
                    ChatUtils.sendMessage("Quests: " + Utils.get(raw, "player#questNumber"));
                    ChatUtils.sendMessage("Achievements: " + Utils.get(raw, "player#points"));
                    ChatUtils.sendMessage("Total wins: " + Utils.get(raw, "player#wins"));
                    ChatUtils.sendMessage("Total coins: " + Utils.get(raw, "player#coins"));
                    ChatUtils.sendMessage("Total kills: " + Utils.get(raw, "player#kills"));
                    ChatUtils.sendMessage("Friends: " + Utils.get(raw, "player#friends"));
                    return;
                } else {
                    String game = args[1];
                    GameType g = GameType.fromRealPersonName(game);
                    if (g == null) {
                        String s = "That game could not be found! The games are: ";
                        for (GameType t : GameType.values()) {
                            if (!t.getName().equalsIgnoreCase("housing")) {
                                s += t.getDatabaseName() + " ";
                            }
                        }
                        ChatUtils.sendMessage(s);
                        return;
                    }
                    String playername = args[0];
                    JSONObject raw = mod.getApiHandler().pullPlayerProfile(playername);
                    if (raw.optBoolean("success")) {
                        ChatUtils.sendMessage(raw.toString());
                        Arrays.stream(raw.getNames()).forEach(tmp -> ChatUtils.sendMessage(tmp));
                        ChatUtils.sendMessage("Player '" + playername + "' could not be found!");
                        return;
                    }
//                    ChatUtils.sendMessage(raw.toString());
                    ChatUtils.sendMessage("Loading stats for: " + Utils.getFormatedName(raw));
                    try {
                        g.getGameHandler().handlePlayerStats(raw);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                mod.newError(e);
            }
        });
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] current, BlockPos pos) {
        List<String> toReturn = new ArrayList<>();
        if (current.length == 2) {
            String last = current[1];
            for (GameType gameType : GameType.values()) {
                if (gameType.getName().startsWith(last)) {
                    toReturn.add(gameType.getName());
                }
            }
        }
        return toReturn;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/stats <player> [game]";
    }


}
