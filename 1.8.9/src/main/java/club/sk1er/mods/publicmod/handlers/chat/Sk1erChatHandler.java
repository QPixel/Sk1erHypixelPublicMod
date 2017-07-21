package club.sk1er.mods.publicmod.handlers.chat;

import club.sk1er.mods.publicmod.C;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.config.ConfigOpt;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mitchellkatz on 7/1/17.
 */
public class Sk1erChatHandler {

    public Pattern guildChatParrern = Pattern.compile("Guild > (?<rank>\\[.+] )?(?<player>\\w{1,16}): (?<message>.*)");
    public Pattern partyInvitePattern = Pattern.compile("(\\[.*\\] )?(?<player>\\w+) has invited you to join their party!");
    public Pattern partyPattern = Pattern.compile("Party > ?(?<rank>.+?(?=])])? ?(?<player>.+?(?=:))?: (?<message>.+)");
    public Pattern friendPattern = Pattern.compile("Friend request from ?(?<rank>.+?(?=])])? (?<player>.+)?");
    public Pattern friendButtonPattern = Pattern.compile("Click one: ?(?<accept>.+?(?=])]) - ?(?<deny>.+?(?=])]) - ?(?<block>.+?(?=])])");
    @ConfigOpt
    private String guildPrefix = C.GREEN + "G" + C.GOLD + "u" + C.RED + "i" + C.AQUA + "l" + C.GREEN + "d" + C.WHITE + " " + C.DARK_GREEN + "> ";
    @ConfigOpt
    private boolean showGuildPrefix = true;

    private Sk1erPublicMod mod;
    @ConfigOpt
    private String partyPrefix = "Party > ";
    @ConfigOpt
    private String recentFriend = "";
    private String recentPartyInvite = "";
    private long recentFriendTime = 0L;
    private long recentPartyTime = 0L;

    public Sk1erChatHandler(Sk1erPublicMod mod) {
        this.mod = mod;
    }



    @SubscribeEvent
    public void onRecieve(ClientChatReceivedEvent event) {
        IChatComponent message = event.message;
        String text = message.getUnformattedText();
        applyForGuildOrParty(event, guildChatParrern, true);
        applyForGuildOrParty(event, partyPattern, false);
        Matcher friendMatcher = friendPattern.matcher(text);
        if (friendMatcher.matches()) {
            event.message = new ChatComponentText(C.GREEN + C.OBFUSCATED + "@" + C.RESET + C.BLUE + ">>>"
                    + C.YELLOW + " Friend request from " + friendMatcher.group("player"));
            this.recentFriend = friendMatcher.group("player");
            this.recentFriendTime = System.currentTimeMillis();

        }
        if (friendButtonPattern.matcher(text).matches()) {
            event.message.appendText("\n" + C.GREEN + "Shift + F to accept " + C.DARK_GRAY + "- " + C.RED + "Shift + D to Deny " + C.DARK_GRAY + "- " + C.GRAY + "Shift + I to ignore friend request from " + recentFriend);
        }
        Matcher inviteMatcher = partyInvitePattern.matcher(text);
        if (inviteMatcher.matches()) {
            this.recentPartyInvite = inviteMatcher.group("player");
            this.recentPartyTime = System.currentTimeMillis();
        }

    }


    public void applyForGuildOrParty(ClientChatReceivedEvent message, Pattern pattern, boolean guild) {
        String text = message.message.getUnformattedText();
        Matcher matcher = pattern.matcher(text);
        Matcher colorMatcher = pattern.matcher(message.message.getFormattedText());
        if (matcher.matches()) {
            String player = matcher.group("player");
            String rank = matcher.group("rank");
            String textmessage = matcher.group("message");
            String prefix = mod.getApiHandler().getPrefixForUser(player);
            IChatComponent newComponent = new ChatComponentText(guild ? guildPrefix : partyPrefix);
            if (showGuildPrefix && guild) {
                newComponent.appendText(prefix);
            }
            if (rank != null)
                newComponent.appendText(" " + colorMatcher.group("rank"));
            newComponent.appendText(" " + colorMatcher.group("player"));
            newComponent.appendText(C.WHITE + ":");
            for (String s : textmessage.split(" ")) {
                if (s.contains("\\.") && !s.endsWith(".")) {
                    ChatComponentText tmpText = new ChatComponentText(" " + s.replace("~",C.COLOR_CODE_SYMBOL));
                    ChatStyle style = new ChatStyle();
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, (s.startsWith("http") ? "" : "http://" + s));
                    style.setChatClickEvent(clickEvent);
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL: " + (s.startsWith("http") ? "" : "http://" + s)));
                    style.setChatHoverEvent(hoverEvent);
                    style.setColor(EnumChatFormatting.AQUA);
                    tmpText.setChatStyle(style);
                    newComponent.appendSibling(tmpText);

                } else newComponent.appendText(" " + s.replace("~",C.COLOR_CODE_SYMBOL));
            }
            message.message = newComponent;

        }
    }

    public String getGuildPrefix() {
        return guildPrefix;
    }

    public void setGuildPrefix(String guildPrefix) {
        this.guildPrefix = guildPrefix;
    }

    public boolean isShowGuildPrefix() {
        return showGuildPrefix;
    }

    public void setShowGuildPrefix(boolean showGuildPrefix) {
        this.showGuildPrefix = showGuildPrefix;
    }

    public String getPartyPrefix() {
        return partyPrefix;
    }

    public void setPartyPrefix(String partyPrefix) {
        this.partyPrefix = partyPrefix;
    }

    public String getRecentFriend() {
        return recentFriend;
    }

    public void setRecentFriend(String recentFriend) {
        this.recentFriend = recentFriend;
    }
    public String getRecentPartyInvite() {
        return recentPartyInvite;
    }

    public long getRecentFriendTime() {
        return recentFriendTime;
    }

    public long getRecentPartyTime() {
        return recentPartyTime;
    }

    public void setRecentPartyTime(long recentPartyTime) {
        this.recentPartyTime = recentPartyTime;
    }

    public void setRecentFriendTime(long recentFriendTime) {
        this.recentFriendTime = recentFriendTime;
    }
}