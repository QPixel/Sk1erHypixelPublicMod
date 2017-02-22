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
public class GuiChatConfigScreen extends GuiScreen {
    List<CValue> v;
    List<Sk1erConfigGuiValue> buttons;

    public GuiChatConfigScreen() {
        v = new ArrayList<>();
        buttons = new ArrayList<>();
        v.add(GUILD_CHAT_CUSTOM_PREFIX);
        v.add(GUILD_CHAT_OFFICER_PREFIX);
        v.add(GUILD_CHAT_MASTER_PREFIX);

    }

    private ScaledResolution resolution;

    @Override
    public void initGui() {
        int rad = 30;
        boolean left = true;
        resolution = new ScaledResolution(Minecraft.getMinecraft());
        int c = 0;
        for (CValue value : v) {
            c++;
            if (left && c < 4)
                rad += 10 * resolution.getScaleFactor();

            if (value.getDefaultvalue() instanceof Boolean) {
                BooleanSk1erGuiButton theButton = new BooleanSk1erGuiButton(value, left ? (int) (.10 * resolution.getScaledWidth_double()) : (int) (.55 * resolution.getScaledWidth_double()), rad, resolution);
                buttonList.add(theButton);
                buttons.add(theButton);
            } else if (value.getDefaultvalue() instanceof String) {
                StringSk1erGuiButton theButton = new StringSk1erGuiButton(value, left ? (int) (.10 * resolution.getScaledWidth_double()) : (int) (.55 * resolution.getScaledWidth_double()), rad, resolution, Sk1erPublicMod.getInstance().getConfig().getString(value));
                buttonList.add(theButton);
                buttons.add(theButton);

            } else
                throw new IllegalArgumentException(value.getName() + " is not able to run in gui " + "Gui Chat Screen");
            left = !left;
            if (left) {
                rad += 8 * resolution.getScaleFactor();

            }

        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Sk1erConfigGuiValue value : buttons) {
            if (value instanceof StringSk1erGuiButton) {
                ((StringSk1erGuiButton) value).textInput(keyCode, typedChar);
            }
        }
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
