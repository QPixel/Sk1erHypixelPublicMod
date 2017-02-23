package club.sk1er.mods.hypixel.handlers;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.chat.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public class Handlers {
    private Sk1erPublicMod mod;
    List<Sk1erChatHandler> chatHandlers = new ArrayList<Sk1erChatHandler>();
    public Handlers(Sk1erPublicMod mod) {
        this.mod=mod;
        chatHandlers.add(new GuildChatHandler(mod));
        chatHandlers.add(new Sk1erChatParser(mod));
        chatHandlers.add(new PartyChatHandler(mod));
        chatHandlers.add(new FriendsChatHandler(mod));
    }

    public List<Sk1erChatHandler> getChatHandlers() {
        return chatHandlers;
    }


}
