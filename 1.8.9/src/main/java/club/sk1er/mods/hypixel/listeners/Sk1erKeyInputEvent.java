package club.sk1er.mods.hypixel.listeners;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

/**
 * Created by mitchellkatz on 11/30/16.
 */
public class Sk1erKeyInputEvent extends Sk1erListener {

    public String CHAT_CHANNEL = "ALL";

    public Sk1erKeyInputEvent(Sk1erPublicMod mod) {
        super(mod);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {


    }

}
