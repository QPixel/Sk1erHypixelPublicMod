package club.sk1er.mods.publicmod.commands;

import club.sk1er.mods.publicmod.Multithreading;
import club.sk1er.mods.publicmod.Sk1erCommand;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.utils.ChatUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by Mitchell Katz on 8/9/2017.
 */
public class CommandGetFriends extends Sk1erCommand {

    @Override
    public String getCommandName() {
        return "getfriends";
    }

    @Override
    public String getCommandUsage() {
        return "/getfriends <name> [page,sort] [filter]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            ChatUtils.sendMessage(getCommandUsage());
        } else {
            Multithreading.runAsync(() -> {
                ChatUtils.sendMessage("Loading friends for " + args[0] + "....");
                JsonObject friends = Sk1erPublicMod.getInstance().getApiHandler().getFriends(args[0]);
                if (!friends.has("success") || friends.get("success").getAsBoolean()) {
                    if (args.length == 1 || (args.length == 2 && StringUtils.isNumeric(args[1]))) {
                        // Loading friends for page args[1]
                        int page = args.length == 1 ? 1 : (Integer.parseInt(args[1]));
                        ChatUtils.sendMessage("Friend page: " + page);
                        Set<Map.Entry<String, JsonElement>> entries = friends.entrySet();
                        Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
                        if ((page - 1) * 20 > entries.size()) {
                            ChatUtils.sendMessage("Page " + page + " is too large! It must be at most " + entries.size() / 20);
                            return;
                        }

                        for (int i = 0; i < entries.size(); i++) {
                            Map.Entry<String, JsonElement> next = iterator.next();
                            if (i < 20 * (page - 1))
                                continue;
                            JsonObject tmpFriend = next.getValue().getAsJsonObject();
                            ChatUtils.sendMessage(tmpFriend.get("display").getAsString());
                            if (i > 20 * page)
                                break;
                        }
                    } else if (args.length == 2 || args.length == 3 && args[1].equalsIgnoreCase("sort")) {
                        if (args.length == 2) {
                            ChatUtils.sendMessage("/getfriends <name> sort [rank]");
                            ChatUtils.sendMessage("Valid filers are: None, VIP, VIP+, MVP, MVP+, Donor, Helper, Mod, Admin, Staff");
                            return;
                        }
                        FriendsFilter filter = FriendsFilter.parse(args[2]);
                        if (filter != null) {
                            ChatUtils.sendMessage("Applying Filter: " + filter.getKeys().items.get(0));
                            for (Map.Entry<String, JsonElement> stringJsonElementEntry : friends.entrySet()) {
                                JsonObject value = stringJsonElementEntry.getValue().getAsJsonObject();
                                if (filter.getMatches().contains(value.get("rank").getAsString())) {
                                    ChatUtils.sendMessage(value.get("display").getAsString());
                                }
                            }
                        } else {
                            ChatUtils.sendMessage("Unknown filter: '" + args[2] + "!");
                            ChatUtils.sendMessage("Valid filers are: None, VIP, VIP+, MVP, MVP+, Donor, Helper, Mod, Admin, Staff");
                        }
                    }
                } else ChatUtils.sendMessage("Player '" + args[0] + " was not found!");
            });
        }


    }


    static class MultiStringHolder {

        private List<String> items = new ArrayList<>();

        public MultiStringHolder(String... items) {
            Collections.addAll(this.items, items);
        }

        public void addKey(String key) {
            this.items.add(key);
        }

        public void removeKey(String key) {
            this.items.remove(key);
        }

        public boolean contains(String otherKey) {
            for (String string : items)
                if (string.equalsIgnoreCase(otherKey))
                    return true;
            return false;
        }


        public List<String> getItems() {
            return items;
        }
    }
}
