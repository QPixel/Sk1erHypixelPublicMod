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

    public Pattern guildChatParrern = Pattern.compile("Guild > ?(?<rank>.+?(?=])])? ?(?<player>.+?(?=:))?: (?<message>.+)");

    private String guildPrefix = C.GREEN + "G" + C.GOLD + "u" + C.RED + "i" + C.AQUA + "l" + C.GREEN + "d" + C.WHITE + " " + C.DARK_GREEN + "> ";
    @ConfigOpt
    private boolean showGuildPrefix = true;

    private Sk1erPublicMod mod;

    public Sk1erChatHandler(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @SubscribeEvent
    public void onRecieve(ClientChatReceivedEvent event) {
        IChatComponent message = event.message;
        String text = message.getUnformattedText();
        Matcher matcher = guildChatParrern.matcher(text);
        Matcher colorMatcher = guildChatParrern.matcher(message.getFormattedText());
        if (matcher.matches()) {
            String player = matcher.group("player");
            String rank = matcher.group("rank");
            String textmessage = matcher.group("message");
            String prefix = mod.getApiHandler().getPrefixForUser(player);
            IChatComponent newComponent = new ChatComponentText(guildPrefix);
            if (showGuildPrefix) {
                newComponent.appendText(prefix);
            }
            if (rank != null)
                newComponent.appendText(" " + colorMatcher.group("rank"));
            newComponent.appendText(" " + colorMatcher.group("player"));
            newComponent.appendText(C.WHITE + ":");
            for (String s : textmessage.split(" ")) {
                if (s.contains("\\.") && !s.endsWith(".")) {
                    ChatComponentText tmpText = new ChatComponentText(" " + s);
                    ChatStyle style = new ChatStyle();
                    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, (s.startsWith("http") ? "" : "http://" + s));
                    style.setChatClickEvent(clickEvent);
                    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Open URL: " + (s.startsWith("http") ? "" : "http://" + s)));
                    style.setChatHoverEvent(hoverEvent);
                    style.setColor(EnumChatFormatting.AQUA);
                    tmpText.setChatStyle(style);
                    newComponent.appendSibling(tmpText);

                } else newComponent.appendText(" " + s);
            }


        }
    }

}
