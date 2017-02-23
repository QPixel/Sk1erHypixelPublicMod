package club.sk1er.mods.hypixel.handlers.chat;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.Sk1erConfig;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public abstract  class Sk1erChatHandler {
    private Sk1erPublicMod mod;
    public Sk1erPublicMod getMod() {
        return mod;
    }
    public abstract void handle(ClientChatReceivedEvent event);
    public abstract boolean containsTrigger(ClientChatReceivedEvent event);
    public Sk1erChatHandler(Sk1erPublicMod mod) {
        this.mod=mod;
    }
    public Sk1erConfig getConfig() {
        return mod.getConfig();
    }


}
