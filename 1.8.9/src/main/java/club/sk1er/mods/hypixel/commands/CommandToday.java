package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.config.Sk1erTempDataSaving;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 12/4/2016.
 */
public class CommandToday extends CommandBase {
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "today";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/today";
    }
    private Sk1erPublicMod mod;
    public CommandToday(Sk1erPublicMod mod) {
     this.mod=mod;
    }
    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        Sk1erTempDataSaving dataSaving = mod.getDataSaving();
        ChatUtils.sendMessage("Loading info from "  + dataSaving.getLastDate());
        List<String> games = new ArrayList<>();
        for(String s : dataSaving.getCoinsForGame().keySet()) {
            games.add(s);
        }
        for(String s : dataSaving.getXpForGame().keySet()) {
            if(!games.contains(s)) {
                games.add(s);
            }
        }
        ChatUtils.sendMessage("Info for games");
        for(String s : games) {
            ChatUtils.sendMessage(s);
            ChatUtils.sendMessage("  -XP: " + dataSaving.getXpForGame(s));
            ChatUtils.sendMessage("  -Coins: " + dataSaving.getCoinsForGame(s));
        }
        ChatUtils.sendMessage("Overall coins: "+ dataSaving.getCoins());
        ChatUtils.sendMessage("Overall XP: " + dataSaving.getXp());



    }
}
