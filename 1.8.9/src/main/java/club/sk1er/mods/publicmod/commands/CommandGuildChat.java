package club.sk1er.mods.publicmod.commands;

import club.sk1er.mods.publicmod.Sk1erCommand;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by mitchellkatz on 8/7/17.
 */
public class CommandGuildChat extends Sk1erCommand {

    @Override
    public String getCommandName() {
        return "gc";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            ChatUtils.sendMessage(getCommandUsage(sender));
        } else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                builder.append(args[i]).append(" ");
            }
            String trim = builder.toString().trim();
            if (trim.equalsIgnoreCase("toggle")) {
                Sk1erPublicMod.getInstance().getChatHandler().setShowGuildChat(!Sk1erPublicMod.getInstance().getChatHandler().isShowGuildPrefix());
                ChatUtils.sendMessage("Toggled guild chat " + (Sk1erPublicMod.getInstance().getChatHandler().isShowGuildChat() ? "on" : "off")+"! ");
            } else {
                if(!Sk1erPublicMod.getInstance().getChatHandler().isShowGuildChat()) {
                    Sk1erPublicMod.getInstance().getChatHandler().setShowGuildChat(true);
                    ChatUtils.sendMessage("Toggled guild chat on!");
                }
                ChatUtils.sendMesssageToServer("/gc " + trim);
            }
        }

    }
}
