package club.sk1er.mods.hypixel.handlers.display.configgui;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static club.sk1er.mods.hypixel.config.CValue.*;

/**
 * Created by mitchellkatz on 2/1/17.
 */
public class GuiDisplayConfigScreen extends GuiScreen {
    List<CValue> v;
    List<Sk1erConfigGuiValue> buttons;
    private ScaledResolution resolution;

    public GuiDisplayConfigScreen() {
        v = new ArrayList<>();
        buttons = new ArrayList<>();
        v.add(DISPLAY_CURRENT_GAME);
        v.add(DISPLAY_CURRENT_GAME_XP);
        v.add(DISPLAY_CURRENT_GAME_COINS);
        v.add(DISPLAY_BOOSTERS_DAY);
        v.add(DISPLAY_ONLINE_PLAYERS);
        v.add(DISPLAY_WATCHDOG_BANS);
        v.add(WATCHDOG_BANS_MIN);
        v.add(WATCHDOG_BANS_DAY);
        v.add(WATCHDOG_BANS_TOTAL);
        v.add(PERCENT_TO_NEXT_LEVEL);
        v.add(PERCENT_TO_NEXT_GOAL);
        v.add(XP_EARNED_DAY);
        v.add(COINS_EARNED_DAY);
        v.add(DISPLAY_RANKED_RATING);
        v.add(DISPLAY_QUESTS);
        v.add(DISPLAY_WATERMARK);
        v.add(DISPLAY_PRIMARY_COLOR);
        v.add(DISPLAY_SECONDARY_COLOR);
    }

    @Override
    public void initGui() {
        int rad = 30;
        boolean left = true;
        resolution = new ScaledResolution(Minecraft.getMinecraft());
        for (CValue value : v) {
            if (value.getDefaultvalue() instanceof Boolean) {
                BooleanSk1erGuiButton theButton = new BooleanSk1erGuiButton(value, left ? (int) (.10 * resolution.getScaledWidth_double()) : (int) (.55 * resolution.getScaledWidth_double()), rad, resolution);
                buttonList.add(theButton);
                buttons.add(theButton);
            } else if (value.getDefaultvalue() instanceof String) {
                String s = Sk1erPublicMod.getInstance().getConfig().getString(value);
                Sk1erColorSliderGui theButton = new Sk1erColorSliderGui(value, value.ordinal(), left ? (int) (.05 * resolution.getScaledWidth_double()) : (int) (.60 * resolution.getScaledWidth_double()), rad, s, guiSlider -> {
                });
                buttonList.add(theButton);
                buttons.add(theButton);
            } else
                throw new IllegalArgumentException(value.getName() + " is not able to run in gui " + "Gui Display Screen");
            left = !left;
            if (left)
                rad += 8 * resolution.getScaleFactor();
        }
        Sk1erDisplaySizeSlider slider = new Sk1erDisplaySizeSlider(v.size()+1, left ? (int) (.05 * resolution.getScaledWidth_double()) : (int) (.60 * resolution.getScaledWidth_double()), rad+10, guiSlider -> {});
        buttons.add(slider);
        buttonList.add(slider);


    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        //TODO is this needed?
    }

    @Override
    public void drawDefaultBackground() {
        super.drawDefaultBackground();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

    }
}
