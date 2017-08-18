package club.sk1er.mods.publicmod.display.displayitems;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.display.ElementRenderer;
import com.google.gson.JsonObject;
import net.hypixel.api.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mitchell Katz on 8/18/2017.
 */
public class BedwarsResourceDisplay implements IDisplayItem {
    private static List<Item> scans = new ArrayList<>();

    static {
        scans.add(Item.getItemById(265)); //Iron
        scans.add(Item.getItemById(266)); //Gold
        scans.add(Item.getItemById(264)); //Diamond
        scans.add(Item.getItemById(388)); //Emerald
    }

    private JsonObject data;
    private int ordinal;

    public BedwarsResourceDisplay(JsonObject data, int ordinal) {
        this.data = data;
        this.ordinal = ordinal;
    }

    public static void render(Map<Item, Integer> map, int x, int y) {
        int line = 0;
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        for (Item item : map.keySet()) {
            renderItem.renderItemAndEffectIntoGUI(new ItemStack(item, 1), (int) (x / ElementRenderer.getCurrentScale()), (int) ((y + (16 * line * ElementRenderer.getCurrentScale())) / ElementRenderer.getCurrentScale()));
            String dur = "x" + map.get(item) + "";
            ElementRenderer.draw((x + 20), y + (16 * line) + 8, dur);
            line++;
        }
    }

    @Override
    public DisplayItemType getState() {
        return DisplayItemType.BEDWARS_RESOUCES;
    }

    @Override
    public Dimension draw(int starX, int startY, boolean isConfig) {
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        if (thePlayer != null) {
            ItemStack[] mainInventory = thePlayer.inventory.mainInventory;
            if (Sk1erPublicMod.getInstance().getCurrentGame().equals(GameType.BEDWARS)) {
                ItemStack stack = mainInventory[8];
                if (stack != null && stack.getUnlocalizedName().equals("item.compass")) {
                    return new Dimension(0, 0);
                }
            }
            HashMap<Item, Integer> amounts = new HashMap<>();
            for (ItemStack stack : mainInventory) {
                String unlocalizedName = stack.getItem().getUnlocalizedName();
                for (Item i : scans) amounts.put(i, 0);
                for (Item i : scans)
                    if (i.getUnlocalizedName().equalsIgnoreCase(unlocalizedName)) amounts.put(i, amounts.get(i) + 1);
            }
            render(amounts, starX, startY);

        }
        return new Dimension(0, 0);
    }

    @Override
    public JsonObject getRaw() {
        return data;
    }

    @Override
    public int getOrdinal() {
        return ordinal;
    }

    @Override
    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
