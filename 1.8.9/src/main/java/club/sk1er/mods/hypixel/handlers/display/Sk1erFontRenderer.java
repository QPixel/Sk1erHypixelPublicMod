package club.sk1er.mods.hypixel.handlers.display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * Created by mitchellkatz on 4/13/17.
 */
public class Sk1erFontRenderer extends FontRenderer {
    public Sk1erFontRenderer() {
        super(Minecraft.getMinecraft().gameSettings,
                new ResourceLocation("textures/font/ascii_sga.png"),
                Minecraft.getMinecraft().getTextureManager(),
                Minecraft.getMinecraft().isUnicode());
        FONT_HEIGHT = 4;
    }
}
