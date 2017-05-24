package club.sk1er.mods.hypixel.config;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 5/24/17.
 */
public class Sk1erConfigurationHandler {
    private static Configuration config;
    private Sk1erPublicMod mod;
    private List<Sk1erConfigElement> elements = new ArrayList<>();

    public Sk1erConfigurationHandler(Sk1erPublicMod mod) {
        this.mod = mod;
        config = new Configuration(mod.getConfig().configFile);
        config.load();
        registerValues();

    }

    public static Configuration getConfig() {
        return config;
    }

    private void registerValues() {
        elements.add(new Sk1erConfigElement<>("chat", "Auto GG", true));
        elements.add(new Sk1erConfigElement<>("chat", "Show Guild Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Show Party Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Show Private Messages", true));
        elements.add(new Sk1erConfigElement<>("chat", "Show Join/Leave Messages", true));
        elements.add(new Sk1erConfigElement<>("chat", "Broadcast Level-Ups in Guild Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Broadcast Boosters in Guild Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Broadcast Achievements in Guild Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Broadcast Legendary Weapons in Guild Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Broadcast Prestiges in Guild Chat", true));
        elements.add(new Sk1erConfigElement<>("chat", "Broadcast Level X Purchases in Guild Chat", true));




    }

}
