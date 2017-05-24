package club.sk1er.mods.hypixel.config;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

/**
 * Created by mitchellkatz on 5/24/17.
 */
public class Sk1erConfigGui extends GuiConfig {
    public Sk1erConfigGui(GuiScreen parentScreen, Sk1erPublicMod mod, String path) {
        super(parentScreen,
                new ConfigElement(Sk1erConfigurationHandler.getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Sk1erPublicMod.MODID, false, false,
                "",
                path);

    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();

    }
}
