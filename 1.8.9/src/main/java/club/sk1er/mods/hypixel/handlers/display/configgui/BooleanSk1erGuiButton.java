package club.sk1er.mods.hypixel.handlers.display.configgui;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.client.config.GuiCheckBox;

/**
 * Created by mitchellkatz on 2/1/17.
 */
public class BooleanSk1erGuiButton extends GuiCheckBox implements Sk1erConfigGuiValue {
    private CValue value;
    private boolean state;
private int x,y;
    public BooleanSk1erGuiButton(CValue value, int x, int y, ScaledResolution resolution) {
        super(value.ordinal(), x, y, value.getDname(), Sk1erPublicMod.getInstance().getConfig().getBoolean(value));
        this.value = value;
        height = 5 * resolution.getScaleFactor();
        setWidth(10 * resolution.getScaleFactor());
        this.x=x;
        this.y=y;
        state = Sk1erPublicMod.getInstance().getConfig().getBoolean(value);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height) {
            super.setIsChecked(!super.isChecked());
            state=super.isChecked();
            Sk1erPublicMod.getInstance().getConfig().forceValue(value, state);
            return true;
        }
        return false;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        super.drawButton(mc, mouseX, mouseY);
        if(isMouseOver()) {
            FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            String description = value.getDesc();
            super.drawGradientRect(x,y,x+fr.getStringWidth(description), y+50, 0,0);
            super.drawCenteredString(fr, value.getDesc(), x+fr.getStringWidth(description)/2, y-25*res.getScaleFactor(), 16777215);

        }
    }

    @Override
    public void save() {

    }


}
