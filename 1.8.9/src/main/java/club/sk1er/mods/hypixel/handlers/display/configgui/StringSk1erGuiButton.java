package club.sk1er.mods.hypixel.handlers.display.configgui;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Keyboard;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitchellkatz on 2/1/17.
 */
public class StringSk1erGuiButton extends GuiButton implements Sk1erConfigGuiValue {
    private CValue value;
    private String text;
    private boolean typing = false;
    public StringSk1erGuiButton(CValue value, int x, int y, ScaledResolution resolution,String va) {
        super(value.ordinal(), x, y, 100 * resolution.getScaleFactor(), 10 * resolution.getScaleFactor(), va);
        this.value = value;
        text=va;
        displayString=value.getDname();

    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        boolean onBox = super.mousePressed(mc, mouseX, mouseY);
        typing = onBox;
        return onBox;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        int sec = Integer.parseInt(new SimpleDateFormat("ss").format(new Date(System.currentTimeMillis())));
        boolean blinkyDude  = false;
        if(sec %2==0 && typing)
            blinkyDude=true;
        FontRenderer fontrenderer = mc.fontRendererObj;
        mc.getTextureManager().bindTexture(buttonTextures);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height || typing;
        int i = this.getHoverState(this.hovered);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.blendFunc(770, 771);
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
        //this.drawTexturedModalRect(this.xPosition+ resolution.getScaledWidth()/100, this.yPosition+ resolution.getScaledHeight()/100, 0, 46 + i * 20, (this.width- resolution.getScaledHeight()/50) / 2, this.height-resolution.getScaledHeight()/50);
        this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
        this.drawGradientRect(this.xPosition+ resolution.getScaledWidth()/100,
                this.yPosition+ resolution.getScaledHeight()/100,this.xPosition+(this.width)-resolution.getScaledWidth()/100, this.yPosition+this.height- resolution.getScaledHeight()/100, -16777216,-16777216);
        this.mouseDragged(mc, mouseX, mouseY);
        int j = 14737632;

        if (packedFGColour != 0)
        {
            j = packedFGColour;
        }
        else
        if (!this.enabled)
        {
            j = 10526880;
        }
        else if (this.hovered)
        {
            j = 16777120;
        }
        String tempText = text;
        if(blinkyDude)
            tempText+="|";
        this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition - (this.height - 8) , j);
        this.drawString(fontrenderer, tempText, this.xPosition+ resolution.getScaledWidth()/100, this.yPosition+ (this.height-8)/2 ,j);
    }
    private final String VALID = "1234567890abcdefghijklmnopqrstuvwxyz.!@#$%^&*()-=;':\",.<>/?`~[]{}\\| ";
    public void textInput(int key, char c) {
        if (typing) {
            if (key == Keyboard.KEY_BACK) {
                if (text != null && text.length() > 1) {
                    text = text.substring(0, text.length() - 1);
                } else {
                    text="";
                }
                displayString=value.getDname();
            } else {
                char name =c;
                String name1 = name+"";
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    name1=name1.toUpperCase();
                }
                if(!VALID.contains(name1.toLowerCase()))
                    return;
                text+=name1;
                displayString=value.getDname();
            }
        }
        Sk1erPublicMod.getInstance().getConfig().forceValue(value, text);
    }

    public void save() {
        Sk1erPublicMod.getInstance().getConfig().forceValue(value, text);
    }

}
