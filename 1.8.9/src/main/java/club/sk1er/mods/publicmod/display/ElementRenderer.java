package club.sk1er.mods.publicmod.display;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Mitchell Katz on 5/25/2017.
 */
public class ElementRenderer {

    private static double currentScale = 1.0;
    private static int color;
    private static int[] COLORS = new int[]{16777215, 16711680, 65280, 255, 16776960, 11141290};
    private static boolean display = false;
    private static List<Long> clicks = new ArrayList<>();
    boolean last = false;
    private Sk1erPublicMod mod;
    private Minecraft minecraft;

    public ElementRenderer(Sk1erPublicMod mod) {
        this.mod = mod;
        minecraft = Minecraft.getMinecraft();
    }

    public static double getCurrentScale() {
        return currentScale;
    }

    public static int getColor(int index) {
        if (index == 6) {
            return Color.HSBtoRGB(System.currentTimeMillis() % 1000L / 1000.0f, 0.8f, 0.8f);
        }
        return COLORS[index];
    }

    public static void display() {
        display = true;
    }

    public static void draw(int x, int y, String string) {
        List<String> tmp = new ArrayList<>();
        tmp.add(string);
        draw(x, y, tmp);
    }

    public static void draw(int x, int y, List<String> list) {
        int tx = x;
        int ty = y;
        for (String string : list) {
            Minecraft.getMinecraft().fontRendererObj.drawString(string, (int) (tx / getCurrentScale()), (int) (ty / getCurrentScale()), getColor(color));
            ty += 10;
        }
    }

    public static int maxWidth(List<String> list) {
        int max = 0;
        for (String s : list) {
            max = Math.max(max, Minecraft.getMinecraft().fontRendererObj.getStringWidth(s));
        }
        return max;
    }

    public static int getColor() {
        return color;
    }

    public static int getCPS() {
        Iterator<Long> iterator = clicks.iterator();
        while (iterator.hasNext())
            if (System.currentTimeMillis() - iterator.next() > 1000L)
                iterator.remove();
        return clicks.size();
    }

    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (display) {
            Minecraft.getMinecraft().displayGuiScreen(mod.getConfigGuiInstance());
            display = false;
            int t = 24;
        }
    }

    @SubscribeEvent
    public void onRenderTick(final TickEvent.RenderTickEvent event) {
        if (!this.minecraft.inGameHasFocus || this.minecraft.gameSettings.showDebugInfo) {
            return;
        }

        if (mod.isHypixel())
            renderElements();
    }

    private void renderElements() {
        boolean m = Mouse.isButtonDown(0);
        if (m != last) {
            last = m;
            if (m) {
                clicks.add(System.currentTimeMillis());
            }
        }

        EntityRenderer entityRenderer = Minecraft.getMinecraft().entityRenderer;

        try {
            List<DisplayElement> elementList = mod.getDisplayElements();
            for (DisplayElement element : elementList) {
                GlStateManager.scale(element.getScale(), element.getScale(), 0);
                currentScale = element.getScale();
                color = element.getColor();
                element.draw();
                GlStateManager.scale(1.0 / element.getScale(), 1.0 / element.getScale(), 0);
            }
        } catch (Exception e) {

        }
    }

    public static int width(String string) {
        return Minecraft.getMinecraft().fontRendererObj.getStringWidth(string);
    }
}
