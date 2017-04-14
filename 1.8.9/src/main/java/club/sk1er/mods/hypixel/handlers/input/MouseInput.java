package club.sk1er.mods.hypixel.handlers.input;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

/**
 * Created by mitchellkatz on 12/7/16.
 */
public class MouseInput {
    private boolean isHoldingDown = false;
    private boolean moving = false;
    private Sk1erPublicMod mod;
    private int lastX = 0;
    private int lastY = 0;
    private int x_change = 0;
    private int y_change = 0;

    public MouseInput(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @SubscribeEvent
    public void onMouseInput(GuiScreenEvent.MouseInputEvent e) {
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        y_change = Mouse.getY() - lastY;
        x_change = Mouse.getX() - lastX;
        x_change /= res.getScaleFactor();
        y_change /= (res.getScaleFactor() * 2);
        if (isOverText(res) && Mouse.isButtonDown(0) && Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen() && (x_change != 0 || y_change != 0)) {
            moving = true;
            mod.isMovingCustomDisplay = true;
        }
        if (moving && isHoldingDown) {
            double xpres = mod.getConfig().getDouble(CValue.CUSTOM_DISPLAY_LOCATION_X) + (double) x_change / res.getScaledWidth_double();
            double yVariant = 0;
            if (res.getScaleFactor() == 1) {
                yVariant = 2;
            } else if (res.getScaleFactor() == 2) {
                yVariant = 1;
            } else {
                yVariant = .75;
            }
            double ypres = mod.getConfig().getDouble(CValue.CUSTOM_DISPLAY_LOCATION_Y) - (double) y_change / res.getScaledHeight_double() * (double) res.getScaleFactor() * (yVariant);
            mod.getConfig().forceValue(CValue.CUSTOM_DISPLAY_LOCATION_X, xpres);
            mod.getConfig().forceValue(CValue.CUSTOM_DISPLAY_LOCATION_Y, ypres);
        }
        if (moving && !isHoldingDown) {
            mod.getConfig().save();
            moving = false;
            mod.isMovingCustomDisplay = false;
        }
        isHoldingDown = Mouse.isButtonDown(0);
        lastX = Mouse.getX();
        lastY = Mouse.getY();
    }

    public boolean isOverText(ScaledResolution res) {
        int edit_width = Minecraft.getMinecraft().fontRendererObj.getStringWidth("Drag to relocate") / 2;
        int x = Math.abs(Mouse.getX());
        int y = Math.abs(Mouse.getY() - (res.getScaledHeight() * res.getScaleFactor()));
        double sx = mod.getConfig().getDouble(CValue.CUSTOM_DISPLAY_LOCATION_X)
                * res.getScaledWidth_double() * res.getScaleFactor() + edit_width * res.getScaleFactor();
        double sy = mod.getConfig().getDouble(CValue.CUSTOM_DISPLAY_LOCATION_Y) * res.getScaleFactor() * res.getScaledHeight() + (5 * res.getScaleFactor());
        if (Math.abs(sy - y) < 10 * res.getScaleFactor() && Math.abs(sx - x) < edit_width * res.getScaleFactor()) {
            return true;
        } else {
            return false;
        }

    }
}
