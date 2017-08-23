package club.sk1er.mods.publicmod.display.gui;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.DisplayElement;
import club.sk1er.mods.publicmod.display.displayitems.IDisplayItem;
import club.sk1er.mods.publicmod.display.gui.buttons.EditElement;
import club.sk1er.mods.publicmod.display.gui.screens.EditElementScreen;
import club.sk1er.mods.publicmod.display.gui.screens.EditSubElementsGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 5/30/17.
 */
public class DisplayGuiConfig extends GuiScreen {

    private Sk1erPublicMod mod;
    private DisplayElement currentElement;

    private boolean mouseDown;

    private DisplayElement lastClicked;

    public DisplayGuiConfig(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @Override
    public void initGui() {
        buttonList.add(new GuiButton(1, 20, height - 40, 75, 20, "New element"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        List<DisplayElement> elementList = mod.getDisplayElements();
        for (DisplayElement element : elementList) {
            try {

                element.drawForConfig();
            } catch (Exception e) {

            }
        }
        if (currentElement != null) {
            ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
            double x1 = currentElement.getXloc() * resolution.getScaledWidth_double();
            double x2 = currentElement.getXloc() * resolution.getScaledWidth_double() + currentElement.getDimensions().getWidth();
            double y1 = currentElement.getYloc() * resolution.getScaledHeight_double();
            double y2 = currentElement.getYloc() * resolution.getScaledHeight_double() + currentElement.getDimensions().getHeight();
            // TODO outline the object. Cords of the cords are above. Ideally, it is expanded by 5 in all directions
            // Left top right bottom

            currentElement.drawForConfig();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                List<IDisplayItem> iDisplayItems = new ArrayList<>();
                DisplayElement element = new DisplayElement(.5, .5, 1.0, 1, iDisplayItems);
                mod.getDisplayElements().add(element);
                mc.displayGuiScreen(new EditSubElementsGui(element, mod));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        boolean isOver = false;
        for (GuiButton button : buttonList) {
            if (button.isMouseOver())
                isOver = true;
        }
        if (!mouseDown && Mouse.isButtonDown(0) && !isOver) {
            //Mouse pushed down. Calculate current element
            int clickX = Mouse.getX() / resolution.getScaleFactor();
            int clickY = Mouse.getY() / resolution.getScaleFactor();
            boolean found = false;
            for (DisplayElement element : mod.getDisplayElements()) {
                Dimension dimension = element.getDimensions();
                double displayXLoc = resolution.getScaledWidth_double() * element.getXloc();
                double displayYLoc = resolution.getScaledHeight_double() - resolution.getScaledHeight_double() * element.getYloc();
                if (clickX > displayXLoc && clickX < displayXLoc + dimension.getWidth() && clickY < displayYLoc && clickY > displayYLoc - dimension.getHeight()) {
                    if (lastClicked == element) {
                        Minecraft.getMinecraft().displayGuiScreen(new EditElementScreen(mod, getCurrentElement()));
                        lastClicked = null;
                        return;
                    }
                    this.currentElement = element;
                    this.lastClicked = element;
                    found = true;
                    // System.out.println("Current element: " + currentElement);
                } else {
                    if (lastClicked == element) {
                        lastClicked = null;
                    }
                }
            }
            if (!found)
                currentElement = null;
        }
        mouseDown = Mouse.isButtonDown(0);
    }

    public Sk1erPublicMod getMod() {
        return mod;
    }

    public boolean isMouseDown() {
        return mouseDown;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        mod.saveDisplayConfig();
    }

    public DisplayElement getCurrentElement() {
        return currentElement;
    }
}
