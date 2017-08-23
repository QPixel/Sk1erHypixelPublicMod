package club.sk1er.mods.publicmod.display.gui.screens;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.DisplayElement;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import club.sk1er.mods.publicmod.display.ResolutionUtil;
import club.sk1er.mods.publicmod.display.displayitems.DisplayItemType;
import club.sk1er.mods.publicmod.display.displayitems.IDisplayItem;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 6/24/17.
 */
public class AddItemScreen extends GuiScreen {

    public List<GuiButton> allItems = new ArrayList<>();

    private DisplayElement element;
    private GuiTextField textField;
    private Sk1erPublicMod mod;

    public AddItemScreen(DisplayElement element, Sk1erPublicMod mod) {
        this.element = element;
        this.mod = mod;
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        textField = new GuiTextField(11, Minecraft.getMinecraft().fontRendererObj, resolution.getScaledWidth() / 2 - 50, 25, 100, 20) {

            @Override
            public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
                boolean typed = super.textboxKeyTyped(p_146201_1_, p_146201_2_);
                if (typed) {
                    generateButtons(textField.getText());
                }
                return typed;
            }
        };

        textField.setEnabled(true);
        textField.setFocused(true);
    }

    @Override
    public void initGui() {
        generateButtons("");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        textField.drawTextBox();
        ScaledResolution current = ResolutionUtil.current();
        double startX = current.getScaledWidth_double() / 6.0;
        int y = 50;
        ElementRenderer.draw((int) startX, y - 10, "All display items");
        for (DisplayItemType type : DisplayItemType.values()) {
            ElementRenderer.draw((int) startX, y, type.getName());
            y += 10;
        }
    }

    private void generateButtons(String param) {
        int y = 100;
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());

        buttonList.clear();
        buttonList.add(new GuiButton(1, 5, 25, 50, 20, "Back"));

        int width = Math.max(Minecraft.getMinecraft().fontRendererObj.getStringWidth("New Coords Display") + 10, resolution.getScaledWidth() / 9);
        for (DisplayItemType type : DisplayItemType.values()) {
            if (type.getName().toLowerCase().contains(param.trim().toLowerCase()) || param.isEmpty()) {
                buttonList.add(new GuiButton(type.ordinal() + 1, resolution.getScaledWidth() / 2 - width / 2, y, 40 + width, 20, "New " + type.getName()) {
                    @Override
                    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
                        boolean b = super.mousePressed(mc, mouseX, mouseY);
                        if (b) {
                            generateNew(type);
                        }
                        return b;
                    }
                });
                y += 30;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                Minecraft.getMinecraft().displayGuiScreen(new EditSubElementsGui(element, mod));
                break;
        }
    }

    public void generateNew(DisplayItemType type) {
        element.getDisplayItems().add(IDisplayItem.parse(type, element.getDisplayItems().size(), new JsonObject()));
        Minecraft.getMinecraft().displayGuiScreen(new EditSubElementsGui(element, mod));
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        textField.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        textField.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        textField.updateCursorCounter();
    }
}
