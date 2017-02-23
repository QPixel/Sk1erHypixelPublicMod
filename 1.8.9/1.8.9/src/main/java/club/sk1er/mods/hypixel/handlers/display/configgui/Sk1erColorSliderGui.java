package club.sk1er.mods.hypixel.handlers.display.configgui;


import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraftforge.fml.client.config.GuiSlider;

/**
 * Created by Mitchell Katz on 2/8/2017.
 */
public class Sk1erColorSliderGui extends GuiSlider implements Sk1erConfigGuiValue{
    final String POS ="0123456789abcdef";
    private CValue value;
    public Sk1erColorSliderGui(CValue value, int id, int xPos, int yPos, String displayStr, ISlider par) {
        super(id, xPos, yPos, displayStr, 0,  15,5,par);
        setValue(POS.indexOf(Sk1erPublicMod.getInstance().getConfig().getString(value).replace("&","")));
        this.value=value;
        displayString=getConfigValue();
    }
    private String getConfigValue() {
        return value.getDname()+": " + Sk1erPublicMod.getInstance().getConfig().getString(value).replace("&", C.COLOR_CODE_SYMBOL) +"@";
    }
    @Override
    public void updateSlider() {
        super.updateSlider();
        Sk1erPublicMod.getInstance().getConfig().forceValue(value, "&"+POS.charAt(getValueInt()));
        displayString=getConfigValue();

    }

    @Override
    public void save() {

    }
}
