package club.sk1er.mods.hypixel.handlers.display.configgui;


import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import net.minecraftforge.fml.client.config.GuiSlider;

/**
 * Created by Mitchell Katz on 2/8/2017.
 */
public class Sk1erDisplaySizeSlider extends GuiSlider implements Sk1erConfigGuiValue {
    private CValue value = CValue.DISPLAY_SIZE;

    public Sk1erDisplaySizeSlider(int id, int xPos, int yPos, ISlider par) {
        super(id, xPos, yPos, "", 50, 150, 5, par);
        setValue(Sk1erPublicMod.getInstance().getConfig().getInt(value));
        displayString = getConfigValue();
    }

    private String getConfigValue() {
        return value.getDname() + ": " + Sk1erPublicMod.getInstance().getConfig().getInt(value);
    }

    @Override
    public void updateSlider() {
        super.updateSlider();
        Sk1erPublicMod.getInstance().getConfig().forceValue(value, getValueInt());
//        ChatUtils.sendMessage("Setting value to " + getValueInt());
        displayString = getConfigValue();
    }

    @Override
    public void save() {

    }
}
