package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 4/17/17.
 */
public abstract class Sk1erCommand extends CommandBase {
    private Sk1erPublicMod mod;
    private String name;
    private String usage;
    public Sk1erCommand(Sk1erPublicMod mod, String name,String usage) {
        this.mod = mod;
        this.name = name;
        this.usage=usage;
    }

    public Sk1erPublicMod getMod() {
        return mod;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return usage;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
        return new ArrayList<>();
    }


    public abstract void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException;
}
