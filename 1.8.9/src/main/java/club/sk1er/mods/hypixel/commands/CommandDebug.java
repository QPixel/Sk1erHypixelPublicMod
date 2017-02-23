package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.display.Sk1erRenderEvent;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.server.Sk1erPlayerLogIntoServerEvent;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by Mitchell Katz on 12/4/2016.
 */
public class CommandDebug extends CommandBase {
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "debug";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/debug [args]";
    }

    private Sk1erPublicMod mod;

    public CommandDebug(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        if (!Minecraft.getMinecraft().thePlayer.getName().equalsIgnoreCase("sk1er")) {
            ChatUtils.sendMessage("Unauthorized to use this command. It is here for Sk1er's debug of beta version of the mod");
            ChatUtils.sendMessage("The version you are using is a beta version.");
        }
        if (a.length == 0) {
            ChatUtils.sendMessage(getCommandUsage(iCommandSender));
        } else {
            if (a[0].equalsIgnoreCase("hypixel")) {
                mod.isHypixel = !mod.isHypixel;
                ChatUtils.sendMessage("Set Hypixel status to: " + !mod.isHypixel);
            } else if (a[0].equals("clear")) {
                Sk1erRenderEvent.renderObjects.clear();
            } else if (a[0].equals("simulatelogin")) {
                new Sk1erPlayerLogIntoServerEvent(mod).handleHypixelLogin();
            } else if(a[0].equals("quests")) {
                for(HypixelQuest quest : HypixelQuest.allQuests) {
                    ChatUtils.sendMessage(quest.getFrontEndName() + " => " + quest.getGameType() + " " + quest.getBackendName() + " " + quest.isCompleted());
                }
            }
        }

    }
}
