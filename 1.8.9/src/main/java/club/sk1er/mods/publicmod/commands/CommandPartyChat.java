package club.sk1er.mods.publicmod.commands;

import club.sk1er.mods.publicmod.Sk1erCommand;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by mitchellkatz on 8/7/17.
 */
public class CommandPartyChat extends Sk1erCommand {

    @Override
    public String getCommandName() {
        return "pc";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            ChatUtils.sendMessage(getCommandUsage(sender));
        } else {
            StringBuilder builder = new StringBuilder();
            for (String arg : args) {
                builder.append(arg).append(" ");
            }
            String trim = builder.toString().trim();
            if (trim.equalsIgnoreCase("toggle") || trim.equalsIgnoreCase("t")) {
                Sk1erPublicMod.getInstance().getChatHandler().setShowPartyChat(!Sk1erPublicMod.getInstance().getChatHandler().isShowPartyChat());
                ChatUtils.sendMessage("Toggled party chat " + (Sk1erPublicMod.getInstance().getChatHandler().isShowPartyChat() ? "on" : "off") + "! ");
            } else {
                if (!Sk1erPublicMod.getInstance().getChatHandler().isShowPartyChat()) {
                    Sk1erPublicMod.getInstance().getChatHandler().setShowPartyChat(true);
                    ChatUtils.sendMessage("Toggled party chat  on!");
                }
                ChatUtils.sendMesssageToServer("/pc " + trim);
            }
        }

    }
}
