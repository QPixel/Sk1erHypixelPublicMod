package club.sk1er.mods.hypixel.handlers.chat;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

/**
 * Created by mitchellkatz on 12/5/16.
 */
public class PartyChatHandler extends Sk1erChatHandler {
    public PartyChatHandler(Sk1erPublicMod mod) {
        super(mod);
    }

    @Override
    public boolean containsTrigger(ClientChatReceivedEvent event) {
        return event.message.getUnformattedText().startsWith("Party > ");
    }

    @Override
    public void handle(ClientChatReceivedEvent e) {
        e.setCanceled(true);
        if (getConfig().getBoolean(CValue.SHOW_PARTY_CHAT)) {
            IChatComponent message = new ChatComponentText("");
            IChatComponent old = e.message;
            message.appendText(getConfig().getString(CValue.PARTY_CHAT_HEADER).replace("&", C.COLOR_CODE_SYMBOL));
            for (IChatComponent es : e.message.getSiblings()) {
                if (es.getUnformattedText().contains("Party")) {
                    message.appendSibling(new ChatComponentText(es.getFormattedText().replace("~", C.COLOR_CODE_SYMBOL)));
                    continue;
                }
                if (!es.getChatStyle().isEmpty()) {
                    ClickEvent tmp = es.getChatStyle().getChatClickEvent();
                    IChatComponent component = new ChatComponentText("");
                    component.appendText(es.getFormattedText().replace("~", C.COLOR_CODE_SYMBOL));
                    component.getChatStyle().setChatClickEvent(tmp);
                    message.appendSibling(component);
                } else {
                    message.appendSibling(new ChatComponentText(es.getFormattedText().replace("~", C.COLOR_CODE_SYMBOL)));
                }
            }
            ChatUtils.sendRawMessage(message);
        } else e.setCanceled(true);
    }
}
