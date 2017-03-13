package club.sk1er.mods.hypixel.listeners;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.handlers.chat.Sk1erChatHandler;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public class ChatListener extends Sk1erListener{

    public ChatListener(Sk1erPublicMod mod) {
        super(mod);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onChatReceiveEvent(ClientChatReceivedEvent e) {
        String wrk = e.message.getUnformattedText();
        if (wrk.startsWith(" ") && wrk.replace(" ", "").toLowerCase().startsWith("win") && getConfig().getBoolean(CValue.AUTO_GG) && getMod().ALLOW_AUTO_GG) {
            Multithreading.runAsync(() -> {
                try {
                    Thread.sleep(2500L);
                    ChatUtils.sendMesssageToServer("/achat gg");
                } catch (InterruptedException e1) {

                }
            });
        }
        List<Sk1erChatHandler> chatHandlers = getMod().getHandlers().getChatHandlers();
            for(Sk1erChatHandler handler : chatHandlers) {
            if(handler.containsTrigger(e)) {
                try {
                    System.out.println("Running chat listener: " + handler.getClass().getName());
                    handler.handle(e);
                } catch (Exception e1) {
                    getMod().newError(e1);
                }
            }
        }
    }
}
