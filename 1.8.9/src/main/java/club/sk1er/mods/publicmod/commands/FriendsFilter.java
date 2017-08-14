package club.sk1er.mods.publicmod.commands;

import java.util.List;

/**
 * Created by Mitchell Katz on 8/11/2017.
 */
public enum FriendsFilter {
    NONE(new CommandGetFriends.MultiStringHolder("No rank", "None"), new CommandGetFriends.MultiStringHolder("NONE")),
    VIP(new CommandGetFriends.MultiStringHolder("Vip"), new CommandGetFriends.MultiStringHolder("VIP")),
    VIP_PLUS(new CommandGetFriends.MultiStringHolder("VIP+", "VIP_PLUS"), new CommandGetFriends.MultiStringHolder("VIP_PLUS")),
    MVP(new CommandGetFriends.MultiStringHolder("MVP"), new CommandGetFriends.MultiStringHolder("MVP")),
    MVP_PLUS(new CommandGetFriends.MultiStringHolder("MVP+", "MVP_PLUS"), new CommandGetFriends.MultiStringHolder("MVP_PLUS")),
    DONOR(new CommandGetFriends.MultiStringHolder("Donor", "Donator"), VIP, VIP_PLUS, MVP, MVP_PLUS),
    HELPER(new CommandGetFriends.MultiStringHolder("Helper"), new CommandGetFriends.MultiStringHolder("HELPER")),
    MOD(new CommandGetFriends.MultiStringHolder("Mod", "Moderator"), new CommandGetFriends.MultiStringHolder("MODERATOR")),
    ADMIN(new CommandGetFriends.MultiStringHolder("Admin"), new CommandGetFriends.MultiStringHolder("ADMIN")),
    STAFF(new CommandGetFriends.MultiStringHolder("Staff"), HELPER, MOD, ADMIN);;

    private CommandGetFriends.MultiStringHolder keys;
    private CommandGetFriends.MultiStringHolder matches;

    FriendsFilter(CommandGetFriends.MultiStringHolder key, FriendsFilter... multiple) {
        keys = key;
        matches = new CommandGetFriends.MultiStringHolder();
        for (FriendsFilter friendFilter : multiple) {
            List<String> keys = friendFilter.getMatches().getItems();
            for (String s : keys) {
                matches.addKey(s);
            }
        }

    }

    FriendsFilter(CommandGetFriends.MultiStringHolder keys, CommandGetFriends.MultiStringHolder matchers) {
        this.keys = keys;
        this.matches = matchers;
    }

    public static FriendsFilter parse(String input) {
        for (FriendsFilter friendFilter : values()) {
            if (friendFilter.keys.contains(input))
                return friendFilter;
        }
        return null;
    }

    public CommandGetFriends.MultiStringHolder getKeys() {

        return keys;
    }

    public CommandGetFriends.MultiStringHolder getMatches() {
        return matches;
    }
}