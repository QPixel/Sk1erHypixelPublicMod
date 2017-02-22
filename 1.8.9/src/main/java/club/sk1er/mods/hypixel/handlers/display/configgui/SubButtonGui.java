package club.sk1er.mods.hypixel.handlers.display.configgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by mitchellkatz on 2/6/17.
 */
public class SubButtonGui extends GuiButton{

    private GuiScreen screen;
    public SubButtonGui(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, GuiScreen screen) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.screen=screen;

    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean pres = super.mousePressed(mc, mouseX, mouseY);
        if(pres) {
            super.playPressSound(Minecraft.getMinecraft().getSoundHandler());
            Minecraft.getMinecraft().displayGuiScreen(screen);
        }
        return pres;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
    }
}
