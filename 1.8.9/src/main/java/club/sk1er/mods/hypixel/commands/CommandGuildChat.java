package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by Mitchell Katz on 12/4/2016.
 */
public class CommandGuildChat extends CommandBase {
    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "gc";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/gc <message,toggle>";
    }

    private Sk1erPublicMod mod;

    public CommandGuildChat(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        try {
            if (a.length == 0) {
                ChatUtils.sendMessage(getCommandUsage(iCommandSender));
            } else {
                if (a[0].equalsIgnoreCase("toggle")) {
                    //Toggle off guild chat
                    boolean gchat = mod.getConfig().getBoolean(CValue.SHOW_GUILD_CHAT);
                    mod.getConfig().forceValue(CValue.SHOW_GUILD_CHAT, !gchat);
                    ChatUtils.sendMessage("Guild chat is now " + (gchat ? "hidden" : "shown"));
                } else {
                    String s = "";
                    for (String m : a) {
                        s += " " + m;
                    }
                    ChatUtils.sendMesssageToServer("/gchat " + s.trim());
                }
            }
        } catch (Exception e) {
            mod.newError(e);
        }

    }
}
