package club.sk1er.mods.hypixel.listeners;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.config.Sk1erConfig;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public abstract class Sk1erListener {

    private Sk1erPublicMod mod;

    public Sk1erListener(Sk1erPublicMod mod) {
        this.mod = mod;

    }

    public Sk1erPublicMod getMod() {
        return mod;
    }

    public boolean getConfigBoolean(CValue value) {
        return getConfig().getBoolean(value);
    }

    public Sk1erConfig getConfig() {
        return mod.getConfig();
    }
}
