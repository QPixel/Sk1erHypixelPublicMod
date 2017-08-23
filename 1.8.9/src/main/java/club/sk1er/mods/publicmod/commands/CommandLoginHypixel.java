package club.sk1er.mods.publicmod.commands;

import club.sk1er.mods.publicmod.Sk1erCommand;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by mitchellkatz on 7/24/17.
 */
public class CommandLoginHypixel extends Sk1erCommand {

    @Override
    public String getCommandName() {
        return "loginhypixel";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Sk1erPublicMod.getInstance().joinedHypixel();
    }
}
