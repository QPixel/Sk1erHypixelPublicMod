package club.sk1er.mods.publicmod.handlers.chat;

import club.sk1er.mods.publicmod.C;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.config.ConfigOpt;
import club.sk1er.mods.publicmod.utils.ChatUtils;
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

    public Pattern guildChatParrern = Pattern.compile("Guild > (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");
    public Pattern guildColorPattenr = Pattern.compile("Guild > (?<rank>"+C.COLOR_CODE_SYMBOL+".+] )?(?<player>\\S{1,24}): (?<message>.*)");
    public Pattern partyChatPattern = Pattern.compile("Party > (?<rank>\\[.+] )?(?<player>\\S{1,16}): (?<message>.*)");
    public Pattern partyChatColorPattern = Pattern.compile("Party > (?<rank>"+C.COLOR_CODE_SYMBOL+".+] )?(?<player>\\S{1,24}): (?<message>.*)");
    public Pattern partyInvitePattern = Pattern.compile("(\\[.*\\] )?(?<player>\\w+) has invited you to join their party!");
    public Pattern coinsPatternTwo = Pattern.compile("\\+(?<coins>.+) coins!");
    public Pattern friendPattern = Pattern.compile("--+\\\\nFriend request from ((?<rank>\\[.+] )?(?<player>\\w+)).*");
    public Pattern questPattern = Pattern.compile("(Daily|Weekly)? Quest: (?<name>.+?(?= Completed!))");
    public Pattern expPattern = Pattern.compile(" \\+(?<exp>\\d+) Hypixel Experience");
    public Pattern coinPattern = Pattern.compile(" \\+(?<coin>\\d+) (?<game>.+) Coins");
    //    public Pattern friendButtonPattern = Pattern.compile("(?<accept>.+?(?=])]) - ?(?<deny>.+?(?=])]) - ?(?<block>.+?(?=])])");
    @ConfigOpt
    private String guildPrefix = C.GREEN + "G" + C.GOLD + "u" + C.RED + "i" + C.AQUA + "l" + C.GREEN + "d" + C.WHITE + " " + C.DARK_GREEN + "> ";
    @ConfigOpt
    private String partyChatPrefix = C.BLUE + "P > ";
    @ConfigOpt
    private boolean showGuildPrefix = true;
    @ConfigOpt
    private boolean showGuildChat = true;
    @ConfigOpt
    private boolean showRankPrefix = true;
    @ConfigOpt
    private boolean showPartyChat = true;
    private Sk1erPublicMod mod;
    @ConfigOpt
    private String partyPrefix = C.BLUE+"Party >";
    @ConfigOpt
    private String recentFriend = "";
    private String recentPartyInvite = "";
    private long recentFriendTime = 0L;
    private long recentPartyTime = 0L;

    public Sk1erChatHandler(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    public boolean isShowPartyChat() {
        return showPartyChat;
    }

    public void setShowPartyChat(boolean showPartyChat) {
        this.showPartyChat = showPartyChat;
    }

    public boolean isShowGuildChat() {
        return showGuildChat;
    }

    public void setShowGuildChat(boolean showGuildChat) {
        this.showGuildChat = showGuildChat;
    }

    /*
        Caused by: java.util.ConcurrentModificationException
            at java.util.ArrayList$Itr.checkForComodification(ArrayList.java:901)
            at java.util.ArrayList$Itr.next(ArrayList.java:851)
            at club.sk1er.mods.publicmod.handlers.chat.Sk1erChatHandler.onRecieve(Sk1erChatHandler.java:59)
            at

         */
    @SubscribeEvent
    public void onRecieve(ClientChatReceivedEvent event) {
        event.message = process(event.message);
        if (event.message.getUnformattedText().isEmpty())
            event.setCanceled(true);
//        IChatComponent message = event.message.createCopy();
//        //Make ones per line
//
    }/* -------------------------------------------\n |
        Friend request from: |Sk1er\n
        |[Accept] | - | [DENY] | - | [IGNORE] - | \wn
        -------------------------------------------
    */ /*
    nfo	[CHAT] ----------------------------------------------------
Friend request from MiniiShadyy
[ACCEPT] - [DENY] - [IGNORE]
    */

    public IChatComponent process(IChatComponent component) {
        component = applyGuild(component);
        component = applyParty(component);
        String unformattedText = EnumChatFormatting.getTextWithoutFormattingCodes(component.getUnformattedText());
        Matcher friendMatcher = friendPattern.matcher(unformattedText.replace("\n", "\\n"));
        if (friendMatcher.find()) {
            this.recentFriend = friendMatcher.group("player");
            this.recentFriendTime = System.currentTimeMillis();
            ChatUtils.sendMessage("Friend request from " + recentFriend);
            component.appendText("\n" + C.GREEN + "Alt + F to accept " + C.DARK_GRAY + "- " + C.RED + "Alt + D to Deny " + C.DARK_GRAY + "- " + C.GRAY + "Alt + I to ignore friend request from " + recentFriend);
            return component;
        }

        Matcher inviteMatcher = partyInvitePattern.matcher(unformattedText);
        if (inviteMatcher.find()) {
            this.recentPartyInvite = inviteMatcher.group("player");
            this.recentPartyTime = System.currentTimeMillis();
            ChatUtils.sendMessage("Recent Party: " + recentPartyInvite);
            component.appendText("\n" + C.GREEN + "Alt + P to accept party invite from " + C.RED + recentPartyInvite);
        }
        Matcher expMater = expPattern.matcher(unformattedText);
        if (expMater.find()) {
            Sk1erPublicMod.getInstance().getDatSaving().incrimentDailyInt("exp", Integer.valueOf(expMater.group("exp")));
        }
        Matcher coinMater = coinPattern.matcher(unformattedText);
        if (coinMater.find()) {
            Sk1erPublicMod.getInstance().getDatSaving().incrimentDailyInt("coins", Integer.valueOf(coinMater.group("coin")));
        }
        coinMater = coinsPatternTwo.matcher(unformattedText);
        if (coinMater.find()) {
            Sk1erPublicMod.getInstance().getDatSaving().incrimentDailyInt("coins", Integer.valueOf(coinMater.group("coins")));
        }


        return component;
    }
/*\
sUbScRiBe tO mE Pz!

 */

    public IChatComponent applyGuild(IChatComponent message) {
        String text = EnumChatFormatting.getTextWithoutFormattingCodes(message.getUnformattedText());
        Matcher matcher = guildChatParrern.matcher(text);
        String formattedText = message.getFormattedText();
        Matcher colorMatcher = guildColorPattenr.matcher(formattedText);
        if (matcher.find()) {
            if (!showGuildChat)
                return new ChatComponentText("");
            String player = matcher.group("player");
            String rank = matcher.group("rank");
            String textmessage = matcher.group("message");
            String prefix = mod.getApiHandler().getPrefixForUser(player);
            IChatComponent newComponent = new ChatComponentText(guildPrefix);
            if (showGuildPrefix) {
                newComponent.appendText(prefix);
            }
            //TODO make mater color matcher
//TODO implement color propagation
            boolean matches = colorMatcher.find();
            newComponent.appendText(" " + (rank != null && showRankPrefix ? (matches ? colorMatcher.group("rank") : matcher.group("rank")) : "") + (matches ? colorMatcher.group("player") : matcher.group("player")));
//            else newComponent.appendText(" ");
//            newComponent.appendText("" + (matches ? colorMatcher.group("player") : matcher.group("player")));
            newComponent.appendText(C.WHITE + ":");
            String colorBefore = "r";
            for (String s : textmessage.split(" ")) {
                if (s.contains(".") && !s.endsWith(".")) {
                    ChatComponentText tmpText = new ChatComponentText(C.COLOR_CODE_SYMBOL + colorBefore + " " + s.replace("~", C.COLOR_CODE_SYMBOL));
                    ChatStyle style = new ChatStyle();
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, (s.startsWith("http") ? s : "http://" + s));
                    style.setChatClickEvent(clickEvent);
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL: " + (s.startsWith("http") ? s : "http://" + s)));
                    style.setChatHoverEvent(hoverEvent);
                    style.setColor(EnumChatFormatting.AQUA);
                    tmpText.setChatStyle(style);
                    newComponent.appendSibling(tmpText);
                } else {
                    newComponent.appendText(" " + C.COLOR_CODE_SYMBOL + colorBefore + s.replace("~", C.COLOR_CODE_SYMBOL));
                    if (s.lastIndexOf("~") != -1) {
                        colorBefore = s.charAt(s.lastIndexOf("~") + 1) + "";
                    }

                }
            }
            return newComponent;

        }
        return message;
    }


    public IChatComponent applyParty(IChatComponent message) {
        String text = EnumChatFormatting.getTextWithoutFormattingCodes(message.getUnformattedText());
        Matcher matcher = partyChatPattern.matcher(text);
        String formattedText = message.getFormattedText();
        Matcher colorMatcher = partyChatColorPattern.matcher(formattedText);
        if (matcher.find()) {
            if (!showPartyChat)
                return new ChatComponentText("");
            String player = matcher.group("player");
            String rank = matcher.group("rank");
            String textmessage = matcher.group("message");
            IChatComponent newComponent = new ChatComponentText(partyPrefix);
            //TODO make mater color matcher
//TODO implement color propagation
            boolean matches = colorMatcher.find();
            newComponent.appendText(" " + (rank != null && showRankPrefix ? (matches ? colorMatcher.group("rank") : matcher.group("rank")) : "") + (matches ? colorMatcher.group("player") : matcher.group("player")));
//
//            else newComponent.appendText(" ");
//            newComponent.appendText("" + (matches ? colorMatcher.group("player") : matcher.group("player")));
            newComponent.appendText(C.WHITE + ":");
            String colorBefore = "r";
            for (String s : textmessage.split(" ")) {
                if (s.contains(".") && !s.endsWith(".")) {
                    ChatComponentText tmpText = new ChatComponentText(C.COLOR_CODE_SYMBOL + colorBefore + " " + s.replace("~", C.COLOR_CODE_SYMBOL));
                    ChatStyle style = new ChatStyle();
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, (s.startsWith("http") ? s : "http://" + s));
                    style.setChatClickEvent(clickEvent);
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL: " + (s.startsWith("http") ? s : "http://" + s)));
                    style.setChatHoverEvent(hoverEvent);
                    style.setColor(EnumChatFormatting.AQUA);
                    tmpText.setChatStyle(style);
                    newComponent.appendSibling(tmpText);
                } else {
                    newComponent.appendText(" " + C.COLOR_CODE_SYMBOL + colorBefore + s.replace("~", C.COLOR_CODE_SYMBOL));
                    if (s.lastIndexOf("~") != -1) {
                        colorBefore = s.charAt(s.lastIndexOf("~") + 1) + "";
                    }

                }
            }
            return newComponent;
        }
        return message;
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

    public void setRecentFriendTime(long recentFriendTime) {
        this.recentFriendTime = recentFriendTime;
    }

    public long getRecentPartyTime() {
        return recentPartyTime;
    }

    public void setRecentPartyTime(long recentPartyTime) {
        this.recentPartyTime = recentPartyTime;
    }
}