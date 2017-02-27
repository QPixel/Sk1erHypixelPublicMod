package club.sk1er.mods.hypixel.handlers.chat;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public class GuildChatHandler extends Sk1erChatHandler{

    public GuildChatHandler(Sk1erPublicMod mod){
     super(mod);
    }
    public boolean containsTrigger(ClientChatReceivedEvent e) {
        String raw = e.message.getUnformattedText();
        if(raw.startsWith("Guild > ")) {
            return true;
        } else {
            return false;
        }
    }
    private String getHeader() {
        String tmp = getConfig().getString(CValue.GUILD_CHAT_CUSTOM_PREFIX);
        if(tmp.equalsIgnoreCase("default")) {
            return   C.GREEN +"G" + C.GOLD+ "u" +C.RED + "i" + C.AQUA + "l" + C.GREEN+"d" + C.WHITE + " " + C.DARK_GREEN +"> ";
        } else {
            return tmp.replace("&",C.COLOR_CODE_SYMBOL).replace("~",C.COLOR_CODE_SYMBOL);
        }
    }
    public void handle(ClientChatReceivedEvent e) {
        if(getConfig().getBoolean(CValue.SHOW_GUILD_CHAT)) {
            IChatComponent message = new ChatComponentText("");
            IChatComponent old = e.message;
            if (getConfig().getString(CValue.GUILD_CHAT_START).equalsIgnoreCase("custom")) {
                message.appendSibling(new ChatComponentText(getHeader()));
            } else if (getConfig().getString(CValue.GUILD_CHAT_START).equalsIgnoreCase("SIMPLE")) {
                message.appendSibling(new ChatComponentText(EnumChatFormatting.GREEN + "G > "));
            } else {
                message.appendSibling(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "Guild > "));
            }
            String PLAYER_NAME = "";
            if (old.getUnformattedText().split(" ")[2].contains("[")) {
                PLAYER_NAME = old.getUnformattedText().split(" ")[3];
            } else {
                PLAYER_NAME = old.getUnformattedText().split(" ")[2];
            }
            if (getConfig().getBoolean(CValue.GUILD_CHAT_SHOW_RANK)) {
                message.appendSibling(new ChatComponentText(getMod().getApiHandler().getStatus(PLAYER_NAME).replace("&",C.COLOR_CODE_SYMBOL) + " "));
            }
            for (IChatComponent es : e.message.getSiblings()) {
                if (es.getUnformattedText().contains("Guild")) {
                    message.appendSibling(new ChatComponentText(es.getFormattedText().replace("~", C.COLOR_CODE_SYMBOL)));
                    continue;
                }
                if (!es.getChatStyle().isEmpty()) {
                    ClickEvent tmp = es.getChatStyle().getChatClickEvent();
                    IChatComponent component = new ChatComponentText("");
                    component.appendText(es.getFormattedText().replace("~",C.COLOR_CODE_SYMBOL));
                    component.getChatStyle().setChatClickEvent(tmp);
                    message.appendSibling(component);
                } else {
                    message.appendSibling(new ChatComponentText(es.getFormattedText().replace("~", C.COLOR_CODE_SYMBOL)));
                }
            }
            e.message=message;
        }else e.setCanceled(true);
    }
}
