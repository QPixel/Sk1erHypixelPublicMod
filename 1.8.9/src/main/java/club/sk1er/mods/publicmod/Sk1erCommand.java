package club.sk1er.mods.publicmod;

import club.sk1er.mods.publicmod.utils.ChatUtils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.List;

/**
 * Created by mitchellkatz on 7/20/17.
 */
public abstract class Sk1erCommand extends CommandBase {

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    public String getCommandUsage() {
        return getCommandUsage(null);
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return null;
    }

    public void sendMessage(String message) {
        ChatUtils.sendMessage(message);
    }
}
