package club.sk1er.mods.publicmod.display.gui.screens;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.DisplayElement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiSlider;

import org.lwjgl.input.Mouse;

import java.io.IOException;

/**
 * Created by mitchellkatz on 5/30/17.
 */
public class EditElementScreen extends GuiScreen {

    private DisplayElement currentElement;
    private Sk1erPublicMod mod;

    private GuiSlider slider;

    private int oldX = 0;
    private int oldY;

    private boolean allow = false;
    private boolean mouseDown;

    public EditElementScreen(Sk1erPublicMod mod, DisplayElement element) {
        this.mod = mod;
        this.currentElement = element;
    }

    @Override
    public void initGui() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        buttonList.add(new GuiButton(1, resolution.getScaledWidth() - 50, resolution.getScaledHeight() - 40, 50, 20, "Edit"));
        buttonList.add(new GuiButton(2, resolution.getScaledWidth() - 50, resolution.getScaledHeight() - 20, 50, 20, "Back"));
        buttonList.add(new GuiButton(4, resolution.getScaledWidth() - 50, resolution.getScaledHeight() - 60, 50, 20, "Reset"));
        buttonList.add(new GuiButton(8, 1, resolution.getScaledHeight() - 40, 80, 20, "Rotate color"));

        buttonList.add(slider = new GuiSlider(3, 1, resolution.getScaledHeight() - 20, 150, 20, "Size: ", "", 10, 200, currentElement.getScale() * 210.0 / 2.0, false, true, null) {
            @Override
            public void updateSlider() {
                super.updateSlider();
                currentElement.setScale(getValue() / 100.0);
            }
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        // This renders all the buttons
        super.drawScreen(mouseX, mouseY, partialTicks);

        // This renders all the text. Buttons will be overlayed over the text
        currentElement.drawForConfig();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new EditSubElementsGui(currentElement, mod));
                break;
            case 2:
                Minecraft.getMinecraft().displayGuiScreen(mod.getConfigGuiInstance());
                break;
            case 4:
                currentElement.setScale(1);
                slider.setValue(100);
                currentElement.setXloc(.5);
                currentElement.setYloc(.5);
                break;
            case 8:
                int color = currentElement.getColor();
                if (color < 6) {
                    currentElement.setColor(color + 1);
                } else {
                    currentElement.setColor(0);
                }
                break;
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        mod.saveDisplayConfig();
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        if (mouseDown && Mouse.isButtonDown(0) && allow) {
            if (currentElement != null) {
                for (GuiButton button : buttonList) {
                    if (button.isMouseOver())
                        return;
                }
//                Dimension dimension = currentElement.getDimensions();
//                double d1 = resolution.getScaledWidth_double() * currentElement.getXloc();
//                double d2 = resolution.getScaledHeight_double() * currentElement.getYloc();
//                int x = Mouse.getX();
//                int y = Mouse.getY();
//                if (x > d1 && x < x + dimension.getWidth() && y < d2 && y > d2 + dimension.getHeight()) {
//                    System.out.println("Inside close box");
//                }
                double xloc = resolution.getScaledWidth_double() * currentElement.getXloc() + (double) (Mouse.getX() - oldX) / (double) resolution.getScaleFactor();
                double yloc = resolution.getScaledHeight_double() * currentElement.getYloc() - (double) (Mouse.getY() - oldY) / (double) resolution.getScaleFactor();
                double newX = xloc / resolution.getScaledWidth_double();
                double newY = yloc / resolution.getScaledHeight_double();
                currentElement.setXloc(newX);
                currentElement.setYloc(newY);
            }
        }
        if (!mouseDown && Mouse.isButtonDown(0)) {
            // Clicked
            allow = true;
            for (GuiButton button : buttonList) {
                if (button.isMouseOver())
                    allow = false;
            }
        }
        mouseDown = Mouse.isButtonDown(0);
        if (mouseDown) {
            oldX = Mouse.getX();
            oldY = Mouse.getY();
        }
    }
}
