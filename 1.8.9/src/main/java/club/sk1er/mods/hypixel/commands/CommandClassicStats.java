package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by mitchellkatz on 4/17/17.
 */
public class CommandClassicStats extends Sk1erCommand {

    public CommandClassicStats(Sk1erPublicMod mod) {
        super(mod, "classicstats", "/classicstats <name>");
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        if (a.length == 1)
            ChatUtils.sendMesssageToServer("/stats " + a[0]);
        else ChatUtils.sendMessage(getCommandUsage(iCommandSender));
    }
}
