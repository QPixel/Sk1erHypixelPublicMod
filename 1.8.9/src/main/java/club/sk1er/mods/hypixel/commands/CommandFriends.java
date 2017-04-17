package club.sk1er.mods.hypixel.commands;

import club.sk1er.html.Utils;
import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import org.json.JSONObject;

public class CommandFriends extends Sk1erCommand {


    public CommandFriends(Sk1erPublicMod mod) {
        super(mod, "getfriends","/getfriends [username]");
    }


    @Override
    public void processCommand(ICommandSender sender, final String[] args) throws CommandException {
        Multithreading.runAsync(() -> {
            try {
                int page;
                try {
                    page = args.length >= 2 ? Integer.parseInt(args[1]) : 1;
                } catch (Exception e) {
                    ChatUtils.sendMessage("Could not parse page number '" + args[1] + "'");
                    return;
                }
                if (args.length == 0) {
                    ChatUtils.sendMessage("/friends + [username]");
                    return;
                }
                JSONObject returned = getMod().getApiHandler().pullPlayerProfile(args[0]);
                if (returned == null || returned.optString("cause").equalsIgnoreCase("non-player")) {
                    ChatUtils.sendMessage("Unable to load player: " + args[0] + ". Player does not exist!");
                    return;
                }
                JSONObject friends = getMod().getApiHandler().getFriendsForPlayer(args[0]);
                String[] names = JSONObject.getNames(friends);
                if (page > names.length / 10) {
                    ChatUtils.sendMessage("Targeted player: " + Utils.getFormatedName(returned) + C.WHITE + " does not have " + page + " pages of friends! They have a max of  " + names.length / 10);

                }
                ChatUtils.sendMessage("Targeted player: " + Utils.getFormatedName(returned) + C.WHITE + " Page (" + page + ")");
                int left = page * 10 - 10;
                int right = page * 10;
                for (int i = left; i < right; i++) {
                    if (i >= 0 && i <= names.length - 1) {
                        String name = names[i];
                        JSONObject tmp = friends.optJSONObject(name);
                        ChatUtils.sendMessage("  " + ++i + " " + tmp.getString("display"));
                        i--;
                    }
                }

            } catch (Exception e) {

                getMod().newError(e);
            }
        });


    }


}
