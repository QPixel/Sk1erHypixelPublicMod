package club.sk1er.mods.hypixel.listeners;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.Handlers;
import club.sk1er.mods.hypixel.handlers.chat.Sk1erChatHandler;
import com.google.common.eventbus.Subscribe;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public class ChatListener extends Sk1erListener{

    public ChatListener(Sk1erPublicMod mod) {
        super(mod);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onChatReceiveEvent(ClientChatReceivedEvent e) {
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
