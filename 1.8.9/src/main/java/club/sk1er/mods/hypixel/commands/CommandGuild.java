package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class CommandGuild extends CommandBase {

    private Sk1erPublicMod mod;

    public CommandGuild(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "getguild";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/getguild <name,player> [name,player]";
    }

    @Override
    public void processCommand(ICommandSender sender, final String[] args) throws CommandException {
        Multithreading.runAsync(() -> {
            try {
                if (args.length <= 1) {
                    ChatUtils.sendMessage(getCommandUsage(sender));
                    return;
                }
                JSONObject returned;
                if (args[0].equalsIgnoreCase("player")) {
                    returned = mod.getApiHandler().getGuildByPlayer(args[1]);
                } else if (args[0].equalsIgnoreCase("name")) {
                    returned = mod.getApiHandler().getGuildByName(args[1]);
                } else {
                    ChatUtils.sendMessage(getCommandUsage(sender));
                    return;
                }
                if (returned.optString("cause").equalsIgnoreCase("nonguild")) {
                    ChatUtils.sendMessage("Guild not found!");
                    return;
                }
                returned = returned.getJSONObject("guild");
                ChatUtils.sendMessage("Name: " + returned.getString("name"));
                if (returned.optBoolean("canTag")) {
                    ChatUtils.sendMessage("Tag: [" + returned.getString("tag") + "]");
                }
                ChatUtils.sendMessage("Current coins: " + returned.optInt("coins"));
                ChatUtils.sendMessage("Total coins: " + returned.optInt("coinsEver"));
                HashMap<Long, String> officers = new HashMap<>();
                HashMap<Long, String> members = new HashMap<>();
                int total = 0;
                for (int i = 0; i < returned.getJSONArray("members").length(); i++) {
                    total++;
                    JSONObject tmp = returned.getJSONArray("members").getJSONObject(i);
                    if (tmp.optString("rank").toLowerCase().equalsIgnoreCase("officer")) {
                        officers.put(tmp.getLong("joined"), tmp.getString("displayname"));
                    } else if (tmp.optString("rank").toLowerCase().equalsIgnoreCase("guildmaster")) {
                        ChatUtils.sendMessage("Guild Master: " + tmp.getString("displayname"));
                    } else {
                        members.put(tmp.getLong("joined"), tmp.getString("displayname"));
                    }
                }
                List<Long> officerLong = new ArrayList<>(officers.keySet());
                Collections.sort(officerLong);
                String message1 = "Officers: ";
                for (long l : officerLong) {
                    message1 += officers.get(l) + ", ";
                }
                ChatUtils.sendMessage(message1.trim().substring(0, message1.length() - 2));
                List<Long> longs = new ArrayList<>(members.keySet());
                Collections.sort(longs);
                String message = "Members: ";
                for (long l : longs) {
                    message += members.get(l) + ", ";
                }
                ChatUtils.sendMessage(message.trim().substring(0, message.length() - 2));
                ChatUtils.sendMessage("Members: " + total + "/" + (25 + (5 * returned.getInt("memberSizeLevel"))));


            } catch (Exception e) {
                mod.newError(e);
            }
        });


    }


}
