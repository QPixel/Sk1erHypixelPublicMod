package club.sk1er.mods.publicmod.display.gui.screens;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.DisplayElement;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import club.sk1er.mods.publicmod.display.ResolutionUtil;
import club.sk1er.mods.publicmod.display.displayitems.IDisplayItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by mitchellkatz on 5/31/17.
 */
public class EditSubElementsGui extends GuiScreen {

    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");

    private IDisplayItem current = null;
    private DisplayElement element;
    private Sk1erPublicMod mod;

    private boolean addingElement = false;

    private GuiButton deleteButton;
    private GuiButton moveUpButton;
    private GuiButton moveDownButton;
    private GuiButton addItemButton;

    public EditSubElementsGui(DisplayElement element, Sk1erPublicMod mod) {
        this.element = element;
        this.mod = mod;
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        mod.saveDisplayConfig();
    }

    @Override
    public void initGui() {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        int stringWidth = Math.max(this.mc.fontRendererObj.getStringWidth("New Coords Display") + 10, resolution.getScaledWidth() / 9);

        this.buttonList.add(new GuiButton(1, resolution.getScaledWidth() - resolution.getScaledWidth() / 10, resolution.getScaledHeight() - 20, resolution.getScaledWidth() / 10, 20, "Back"));
        this.buttonList.add(this.deleteButton = new GuiButton(2, 40, resolution.getScaledHeight() - 60, resolution.getScaledWidth() / 10, 20, "Delete"));
        this.buttonList.add(this.moveUpButton = new GuiButton(3, resolution.getScaledWidth() / 2 + 10, 50, resolution.getScaledWidth() / 10, 20, "Move Up"));
        this.buttonList.add(new GuiButton(4, 5, 5, mc.fontRendererObj.getStringWidth("Delete element") + 10, 20, "Delete element"));
        this.buttonList.add(this.moveDownButton = new GuiButton(5, resolution.getScaledWidth() / 2 + 10, 72, resolution.getScaledWidth() / 10, 20, "Move Down"));
        this.buttonList.add(this.addItemButton = new GuiButton(6, 40 + stringWidth, resolution.getScaledHeight() - 60, stringWidth, 20, "Add item"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        this.deleteButton.enabled = this.current != null;
        this.moveUpButton.enabled = this.current != null && current.getOrdinal() != 0;
        this.moveDownButton.enabled = this.current != null && current.getOrdinal() != this.element.getDisplayItems().size() - 1;
        this.addItemButton.enabled = this.element.getDisplayItems().size() < 8;

        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution resolution = ResolutionUtil.current();
        drawHorizontalLine(40, resolution.getScaledWidth() / 2, 40, ElementRenderer.getColor(6));
        drawHorizontalLine(40, resolution.getScaledWidth() / 2, resolution.getScaledHeight() - 70, ElementRenderer.getColor(6));
        drawVerticalLine(40, 40, resolution.getScaledHeight() - 70, ElementRenderer.getColor(6));
        drawVerticalLine(resolution.getScaledWidth() / 2, 40, resolution.getScaledHeight() - 70, ElementRenderer.getColor(6));

        int line = 0;
        int startX = 50;
        int startY = 50;

        for (IDisplayItem iDisplayItem : element.getDisplayItems()) {
            int twith = resolution.getScaledWidth() / 2 - 60;
            mc.getTextureManager().bindTexture(buttonTextures);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            boolean hovered = mouseX >= startX && mouseX < startX + twith && mouseY > startY + line * 30 && mouseY < startY + line * 30 + 15;
            if (iDisplayItem.equals(current)) {
                hovered = true;
            }
            int i = hovered ? 2 : 1;

            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            this.drawTexturedModalRect(startX, startY + line * 30, 0, 46 + i * 20, twith / 2, 20);
            this.drawTexturedModalRect(startX + twith / 2, startY + line * 30, 200 - twith / 2, 46 + i * 20, twith / 2, 20);
            drawCenteredString(Minecraft.getMinecraft().fontRendererObj, iDisplayItem.getState().getName(), startX + twith / 2, startY + line * 30 + 5, ElementRenderer.getColor(1));
            line++;
        }
        element.renderEditView();
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                if (element.getDisplayItems().isEmpty()) {
                    mod.getDisplayElements().remove(element);
                    Minecraft.getMinecraft().displayGuiScreen(mod.getConfigGuiInstance());
                } else {
                    Minecraft.getMinecraft().displayGuiScreen(new EditElementScreen(mod, element));
                }
                break;
            case 2:
                if (current != null) {
                    element.removeDisplayItem(current.getOrdinal());
                    current = null;
                }
                break;
            case 3:
                if (current != null && current.getOrdinal() != 0) {
                    Collections.swap(element.getDisplayItems(), current.getOrdinal(), current.getOrdinal() - 1);
                    element.adjustOrdinal();
                }
                break;
            case 4:
                mod.getDisplayElements().remove(element);
                Minecraft.getMinecraft().displayGuiScreen(mod.getConfigGuiInstance());
                break;
            case 5:
                if (current != null && current.getOrdinal() != element.getDisplayItems().size() - 1) {
                    Collections.swap(element.getDisplayItems(), current.getOrdinal(), current.getOrdinal() + 1);
                    element.adjustOrdinal();
                }
                break;
            case 6:
                Minecraft.getMinecraft().displayGuiScreen(new AddItemScreen(element, mod));
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        int line = 0;
        int startX = 50;
        int startY = 50;
        boolean found = false;
        ScaledResolution resolution = ResolutionUtil.current();
        for (GuiButton button : this.buttonList) {
            if (button.isMouseOver()) return;
        }

        for (IDisplayItem iDisplayItem : element.getDisplayItems()) {
            int twith = resolution.getScaledWidth() / 2 - 60;
            boolean hovered = mouseX >= startX && mouseX < startX + twith && mouseY > startY + line * 30 && mouseY < startY + line * 30 + 15;
//            System.out.println("mouseX = " + mouseX);

            if (hovered) {
                current = iDisplayItem;
                found = true;
//                System.out.println("Current: " + iDisplayItem);
            }
            line++;
        }
        if (!found) current = null;
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditSubElementsGui that = (EditSubElementsGui) o;

        return addingElement == that.addingElement && (current != null ? current.equals(that.current) : that.current == null) && (element != null ? element.equals(that.element) : that.element == null) && (mod != null ? mod.equals(that.mod) : that.mod == null);
    }

    @Override
    public int hashCode() {
        int result = current != null ? current.hashCode() : 0;
        result = 31 * result + (element != null ? element.hashCode() : 0);
        result = 31 * result + (mod != null ? mod.hashCode() : 0);
        result = 31 * result + (addingElement ? 1 : 0);
        return result;
    }
}
