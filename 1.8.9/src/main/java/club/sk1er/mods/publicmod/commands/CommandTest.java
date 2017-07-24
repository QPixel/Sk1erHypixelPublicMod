package club.sk1er.mods.publicmod.commands;

import club.sk1er.mods.publicmod.Multithreading;
import club.sk1er.mods.publicmod.Sk1erCommand;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.gui.DisplayGuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by mitchellkatz on 7/21/17.
 */
public class CommandTest extends Sk1erCommand{
    @Override
    public String getCommandName() {
        return "test";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "Null";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        Multithreading.runAsync(() -> {
            try {
                Thread.sleep(50L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Minecraft.getMinecraft().displayGuiScreen(new DisplayGuiConfig(Sk1erPublicMod.getInstance()));
        });
    }
}
