package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.display.Sk1erRenderEvent;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.server.Sk1erPlayerLogIntoServerEvent;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by Mitchell Katz on 12/4/2016.
 */
public class CommandDebug extends Sk1erCommand {
    public static boolean chatOn = false;
    private Sk1erPublicMod mod;

    public CommandDebug(Sk1erPublicMod mod) {
        super(mod, "sk1erdebug", "/debug [args]");
        this.mod = mod;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        if (!Minecraft.getMinecraft().thePlayer.getName().equalsIgnoreCase("sk1er")) {
            ChatUtils.sendMessage("This is for general debug purposes. You may break the mod by using this command incorrectly.");
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
            } else if (a[0].equals("quests")) {
                for (HypixelQuest quest : HypixelQuest.allQuests) {
                    ChatUtils.sendMessage(quest.getFrontEndName() + " => " + quest.getGameType() + " " + quest.getBackendName() + " " + quest.isCompleted());
                }
            } else if (a[0].equals("chat")) {
                chatOn = !chatOn;
                ChatUtils.sendMessage("Debug chat enabled");
            }


        }

    }
}
