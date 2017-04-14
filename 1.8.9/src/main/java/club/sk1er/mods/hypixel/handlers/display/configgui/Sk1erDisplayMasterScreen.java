package club.sk1er.mods.hypixel.handlers.display.configgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;


/**
 * Created by mitchellkatz on 2/3/17.
 */
public class Sk1erDisplayMasterScreen extends GuiScreen {

    @Override
    public void initGui() {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        int BUTTON_WIDTH = 200;
        int BUTTON_HEIGHT = 20;
        buttonList.add(new SubButtonGui(1, res.getScaledWidth() / 7, res.getScaledHeight() / 2, BUTTON_WIDTH, BUTTON_HEIGHT, "Display settings", new GuiDisplayConfigScreen()));
        buttonList.add(new SubButtonGui(1, res.getScaledWidth() / 7 * 4, res.getScaledHeight() / 2, BUTTON_WIDTH, BUTTON_HEIGHT, "Chat settings", new GuiChatConfigScreen()));
    }


}
