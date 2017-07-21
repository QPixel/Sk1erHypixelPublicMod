package club.sk1er.mods.publicmod.handlers.chat;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by mitchellkatz on 7/1/17.
 */
public class Sk1erChatHandler {

    public Sk1erChatHandler(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    private Sk1erPublicMod mod;

    @SubscribeEvent
    public void onRecieve(ClientChatReceivedEvent event) {

    }
}
