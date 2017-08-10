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
        if (args.length == 1) {
            ChatUtils.sendMessage(getCommandUsage());
        } else {
            Multithreading.runAsync(() -> {
                ChatUtils.sendMessage("Loading....");
                JsonObject friends = Sk1erPublicMod.getInstance().getApiHandler().getFriends(args[0]);
                if (friends.has("success") && friends.get("success").getAsBoolean()) {
                    if (args.length == 2 && StringUtils.isNumeric(args[1])) {
                        //Loaing friends for page args[1]
                        int page = Integer.parseInt(args[1]);
                        ChatUtils.sendMessage("Friend page: " + page);
                        Set<Map.Entry<String, JsonElement>> entries = friends.entrySet();
                        Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
                        if (page * 20 > entries.size()) {
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
                        FriendFilter filter = FriendFilter.parse(args[2]);
                        if (filter != null) {
                            ChatUtils.sendMessage("Applying Filter: " + filter.keys.keys.get(0));
                            Iterator<Map.Entry<String, JsonElement>> iterator = friends.entrySet().iterator();
                            while (iterator.hasNext()) {
                                JsonObject value = iterator.next().getValue().getAsJsonObject();
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

    enum FriendFilter {
        NONE(new MultiStringHolder("No rank", "None"), new MultiStringHolder("NONE")),
        VIP(new MultiStringHolder("Vip"), new MultiStringHolder("VIP")),
        VIP_PLUS(new MultiStringHolder("VIP+", "VIP_PLUS"), new MultiStringHolder("VIP_PLUS")),
        MVP(new MultiStringHolder("MVP"), new MultiStringHolder("MVP")),
        MVP_PLUS(new MultiStringHolder("MVP+", "MVP_PLUS"), new MultiStringHolder("MVP_PLUS")),
        DONOR(new MultiStringHolder("Donor", "Donator"), VIP, VIP_PLUS, MVP, MVP_PLUS),
        HELPER(new MultiStringHolder("Helper"), new MultiStringHolder("HELPER")),
        MOD(new MultiStringHolder("Mod", "Moderator"), new MultiStringHolder("MODERATOR")),
        ADMIN(new MultiStringHolder("Admin"), new MultiStringHolder("ADMIN")),
        STAFF(new MultiStringHolder("Staff"), HELPER, MOD, ADMIN);;

        private MultiStringHolder keys;
        private MultiStringHolder matches;

        FriendFilter(MultiStringHolder key, FriendFilter... multiple) {
            keys = key;
            matches = new MultiStringHolder();
            for (FriendFilter friendFilter : multiple) {
                matches.addAll(friendFilter.getMatches().keys);
            }

        }

        FriendFilter(MultiStringHolder keys, MultiStringHolder matchers) {
            this.keys = keys;
            this.matches = matchers;
        }

        public static FriendFilter parse(String input) {
            for (FriendFilter friendFilter : values()) {
                if (friendFilter.keys.contains(input))
                    return friendFilter;
            }
            return null;
        }

        public MultiStringHolder getKeys() {

            return keys;
        }

        public MultiStringHolder getMatches() {
            return matches;
        }
    }

    static class MultiStringHolder {

        private List<String> keys = new ArrayList<>();

        public MultiStringHolder(String... keys) {
            this.keys = Arrays.asList(keys);
        }

        public void addKey(String key) {
            this.keys.add(key);
        }

        public void removeKey(String key) {
            this.keys.remove(key);
        }

        public boolean contains(String otherKey) {
            for (String string : keys)
                if (string.equalsIgnoreCase(otherKey))
                    return true;
            return false;
        }

        public void addAll(Collection<? extends String> add) {
            keys.addAll(add);
        }
    }
}
