package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by mitchellkatz on 4/18/17.
 */
public class CommandResetDisplay extends Sk1erCommand{

    public CommandResetDisplay(Sk1erPublicMod mod) {
        super(mod, "resetdisplay", "/resetdisplay");
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        getMod().getConfig().forceValue(CValue.CUSTOM_DISPLAY_LOCATION_X, .5);
        getMod().getConfig().forceValue(CValue.CUSTOM_DISPLAY_LOCATION_Y, .5);
        ChatUtils.sendMessage("Display reset!");
    }
}
