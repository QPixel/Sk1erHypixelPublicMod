package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

/**
 * Created by Mitchell Katz on 11/29/2016.
 */
public class CommandSetConfigValue extends Sk1erCommand {


    public CommandSetConfigValue(Sk1erPublicMod mod) {
        super(mod, "setvalue", "/setvalue <value> <value>");

    }


    @Override
    public void processCommand(ICommandSender sender, final String[] args) throws CommandException {
        try {
            if (args.length <= 1) {
                ChatUtils.sendMessage(getCommandUsage(sender));
                return;
            }
            String a = "";
            for (int i = 1; i < args.length; i++) {
                a += " " + args[i];
            }
            a = a.trim();
            args[0] = args[0].toUpperCase();
            CValue value;
            try {
                value = CValue.valueOf(args[0]);
            } catch (Exception e) {
                System.out.println(args[0] + " is not a known config value!");
                return;
            }
            if (value.isValidState(a) || (args.length == 3 && args[2].equalsIgnoreCase("force"))) {
                a = a.replace("force", "").trim();
                if (CValue.valueOf(args[0]).getAllvals().equalsIgnoreCase("double")) {
                    try {
                        getMod().getConfig().forceValue(CValue.valueOf(args[0]), Double.parseDouble(a));
                        ChatUtils.sendMessage("Updated");
                    } catch (Exception e) {
                        ChatUtils.sendMessage("That value could not be found!");
                    }
                    return;
                }
                if (CValue.valueOf(args[0]).getAllvals().equalsIgnoreCase("integer")) {
                    getMod().getConfig().setValue(args[0], Integer.parseInt(a));
                    return;
                }


                getMod().getConfig().setValue(args[0], a);
                ChatUtils.sendMessage("Updated");
            } else {
                try {
                    CValue v = CValue.valueOf(args[0]);
                    ChatUtils.sendMessage("Value: " + a + " is not applicable to " + args[0]);
                    ChatUtils.sendMessage("The possible values are: " + v.getAllvals().replace(",", ", ").replace("*", "*anything"));
                } catch (Exception e) {
                    ChatUtils.sendMessage("Value '"+a+"' was not found!");
                }
            }
        } catch (Exception e) {
            getMod().newError(e);
        }
    }

}
