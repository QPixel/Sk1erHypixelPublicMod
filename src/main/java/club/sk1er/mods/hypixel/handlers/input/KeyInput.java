package club.sk1er.mods.hypixel.handlers.input;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.display.configgui.GuiDisplayConfigScreen;
import club.sk1er.mods.hypixel.handlers.display.configgui.Sk1erDisplayMasterScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * Created by mitchellkatz on 11/30/16.
 */
public class KeyInput {
    public static KeyBinding config = new KeyBinding("Open Sk1er Public Mod Conifg", Keyboard.KEY_Y, Sk1erPublicMod.NAME);




    public KeyInput() {
        ClientRegistry.registerKeyBinding(config);
    }
    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if(config.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new Sk1erDisplayMasterScreen());
        }
    }
}
