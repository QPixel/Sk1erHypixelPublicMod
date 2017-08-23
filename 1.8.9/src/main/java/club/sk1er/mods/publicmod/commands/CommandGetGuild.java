package club.sk1er.mods.publicmod.commands;

import club.sk1er.mods.publicmod.C;
import club.sk1er.mods.publicmod.Multithreading;
import club.sk1er.mods.publicmod.Sk1erCommand;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.utils.ChatUtils;
import club.sk1er.mods.publicmod.utils.Sk1erDateUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by mitchellkatz on 8/10/17.
 */
public class CommandGetGuild extends Sk1erCommand {

    @Override
    public String getCommandName() {
        return "getguild";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length <= 1) {
            ChatUtils.sendMessage("/getguild <player, name> <player,name>");
        } else {
            Multithreading.runAsync(() -> {

                JsonObject guild;
                boolean player = false;

                if (args[0].equalsIgnoreCase("player")) {
                    if (args[1].length() > 16) {
                        ChatUtils.sendMessage("Player name " + args[1] + " is too long!");
                        return;
                    }
                    guild = Sk1erPublicMod.getInstance().getApiHandler().getGuildPlayer(args[1]);
                    player = true;
                } else if (args[0].equalsIgnoreCase("name")) {
                    guild = Sk1erPublicMod.getInstance().getApiHandler().getGuildByName(args[1]);
                } else {
                    ChatUtils.sendMessage("Fetch type '" + args[0] + "' is not valid! Must be either player or name");
                    return;
                }
                if (guild.has("success") && guild.get("success").getAsBoolean()) {
                    guild = guild.get("guild").getAsJsonObject();
                    ChatUtils.sendMessage(C.GREEN + "Guild name: " + guild.get("name").getAsString());
                    String guildMaster = null;
                    List<String> officers = new ArrayList<>();
                    List<String> members = new ArrayList<>();
                    JsonArray memberArray = guild.getAsJsonArray("members");
                    for (JsonElement jsonElement : memberArray) {
                        JsonObject tmp = jsonElement.getAsJsonObject();
                        String rank = tmp.get("rank").getAsString();
                        String displayname = tmp.get("displayname").getAsString();
                        if (rank.equalsIgnoreCase("member")) {
                            members.add(displayname);
                        } else if (rank.equalsIgnoreCase("officer")) {
                            officers.add(displayname);
                        } else if (rank.equalsIgnoreCase("guildmaster")) {
                            guildMaster = displayname;
                        } else {
                            ChatUtils.sendMessage("Error guild rank '" + rank + "' is not defiined!");
                        }
                    }
                    ChatUtils.sendMessage(C.GREEN + "Guild master - " + guildMaster);
                    StringBuilder officerString = new StringBuilder();
                    Iterator<String> iterator = officers.iterator();
                    while (iterator.hasNext()) {
                        officerString.append(iterator.next());
                        if (iterator.hasNext())
                            officerString.append(", ");
                    }
                    ChatUtils.sendMessage(C.GREEN + "Officers - " + officerString.toString());


                    StringBuilder memberBuilder = new StringBuilder();
                    iterator = members.iterator();
                    while (iterator.hasNext()) {
                        memberBuilder.append(iterator.next());
                        if (iterator.hasNext())
                            memberBuilder.append(", ");
                    }
                    ChatUtils.sendMessage(C.GREEN + "Members: " + memberBuilder.toString());
                    if (guild.has("canTag") && guild.get("canTag").getAsBoolean()) {
                        ChatUtils.sendMessage(C.GREEN + "Guild tag - " + C.GRAY + "[" + guild.get("tag").getAsString() + "]");
                    }
                    ChatUtils.sendMessage(C.GREEN + "Total coins - " + C.WHITE +  NumberFormat.getNumberInstance(Locale.US).format(guild.get("coinsEver").getAsInt()));
                    ChatUtils.sendMessage(C.GREEN + "Current coins - " + C.WHITE +  NumberFormat.getNumberInstance(Locale.US).format(guild.get("coins").getAsInt()));
                    ChatUtils.sendMessage(C.GREEN + "Founded on - " + C.WHITE + Sk1erDateUtil.getTime("DD-MM-YYYY", guild.get("created").getAsLong()));

                } else {
                    if (player) {
                        ChatUtils.sendMessage("Player " + args[1] + " is not in a guild!");
                    }
                }
            });

        }
    }
}
