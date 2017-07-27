package club.sk1er.mods.publicmod.display;

import club.sk1er.mods.publicmod.display.displayitems.IDisplayItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 5/25/2017.
 */
public class DisplayElement {
    private double xloc, yloc;
    private List<IDisplayItem> displayItems = new ArrayList<>();
    private double scale = 1;
    private int color;
    private double prevX, prevY;

    public DisplayElement(double xloc, double yloc, double scale, int color, List<IDisplayItem> items) {
        this.xloc = xloc;
        this.yloc = yloc;
        this.scale = scale;
        this.color = color;
        this.displayItems = items;
    }

    @Override
    public String toString() {
        return "DisplayElement{" +
                "xloc=" + xloc +
                ", yloc=" + yloc +
                ", displayItems=" + displayItems +
                ", scale=" + scale +
                ", color=" + color +
                '}';
    }

    private void render(double x, double y, String string) {
        GL11.glScaled(scale, scale, 0);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(string, (float) ((float) x / scale), (float) y, ElementRenderer.getColor(color));
        GL11.glScaled(1.0 / scale, 1.0 / scale, 0);
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getXloc() {
        return xloc;
    }

    public void setXloc(double xloc) {
        this.xloc = xloc;
    }

    public void removeDisplayItem(int ordinal) {
        displayItems.remove(ordinal);
        adjustOrdinal();
    }

    public double getYloc() {
        return yloc;
    }

    public void setYloc(double yloc) {
        this.yloc = yloc;
    }

    public List<IDisplayItem> getDisplayItems() {
        return displayItems;
    }

    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public Dimension getDimensions() {
        return new Dimension((int) prevX, (int) prevY);
    }

    public void drawForConfig() {
        GlStateManager.scale(getScale(), getScale(), 0);
        ElementRenderer.setCurrentScale(getScale());
        ElementRenderer.setColor(getColor());
        this.prevX = 0;
        this.prevY = 0;
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        double addy = 0;
        int x = (int) (xloc * resolution.getScaledWidth_double());
        double y = (int) (yloc * resolution.getScaledHeight_double());
        for (IDisplayItem iDisplayItem : displayItems) {
            Dimension d = iDisplayItem.draw(x, (int) y, true);
            y += d.getHeight() * scale;
            addy += d.getHeight() * scale;
            prevX = (int) Math.max(d.getWidth(), prevX);
        }
        this.prevY = addy;
        GlStateManager.scale(1.0 / getScale(), 1.0 / getScale(), 0);
//        System.out.println("Prev X:" + prevX + " Prev Y: " + prevY);

    }

    public void draw() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int x = (int) (xloc * resolution.getScaledWidth_double());
        double y = (int) (yloc * resolution.getScaledHeight_double());
        for (IDisplayItem iDisplayItem : displayItems) {
            Dimension d = iDisplayItem.draw(x, (int) y, false);
            y += d.getHeight() * scale;
        }
//        System.out.println("Prev X:" + prevX + " Prev Y: " + prevY);


    }

    public void renderEditView() {
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        ElementRenderer.setCurrentScale(1.0);
        ElementRenderer.setColor(getColor());
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int x = (int) (.8 * resolution.getScaledWidth_double());
        int y = (int) (.2 * resolution.getScaledHeight_double());
        for (IDisplayItem iDisplayItem : displayItems) {
            Dimension d = iDisplayItem.draw(x, y, false);
            y += d.getHeight();
        }

    }

    public void adjustOrdinal() {
        for (int ord = 0; ord < displayItems.size(); ord++) {
            displayItems.get(ord).setOrdinal(ord);
        }
    }
}
