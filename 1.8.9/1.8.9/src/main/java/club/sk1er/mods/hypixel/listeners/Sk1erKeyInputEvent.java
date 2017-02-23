package club.sk1er.mods.hypixel.listeners;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.input.KeyInput;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.java.games.input.Keyboard;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Created by mitchellkatz on 11/30/16.
 */
public class Sk1erKeyInputEvent extends Sk1erListener {

    public Sk1erKeyInputEvent(Sk1erPublicMod mod) {
        super(mod);
    }

    public String CHAT_CHANNEL = "ALL";

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {


    }

}
