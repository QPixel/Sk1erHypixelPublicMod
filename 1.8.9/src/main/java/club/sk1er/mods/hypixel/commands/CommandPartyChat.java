package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by Mitchell Katz on 12/4/2016.
 */
public class CommandPartyChat extends Sk1erCommand {

    public CommandPartyChat(Sk1erPublicMod mod) {
        super(mod, "pc", "/pc <message,toggle>");
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender p_canCommandSenderUseCommand_1_) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "pc";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender) {
        return "/pc <message,toggle>";
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        try {
            if (a.length == 0) {
                ChatUtils.sendMessage(getCommandUsage(iCommandSender));
            } else {
                if (a[0].equalsIgnoreCase("toggle")) {
                    //Toggle off guild chat
                    boolean gchat = getMod().getConfig().getBoolean(CValue.SHOW_PARTY_CHAT);
                    getMod().getConfig().forceValue(CValue.SHOW_PARTY_CHAT, !gchat);
                    getMod().getConfig().save();
                    ChatUtils.sendMessage("Party chat is now " + (gchat ? "hidden" : "shown"));
                } else {
                    String s = "";
                    for (String m : a) {
                        s += " " + m;
                    }
                    ChatUtils.sendMesssageToServer("/pchat " + s.trim());
                }
            }
        } catch (Exception e) {
            getMod().newError(e);
        }

    }
}
