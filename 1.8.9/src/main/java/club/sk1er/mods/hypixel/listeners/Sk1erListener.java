package club.sk1er.mods.hypixel.listeners;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.config.Sk1erConfig;
import club.sk1er.mods.hypixel.handlers.Handlers;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public abstract  class Sk1erListener {

    public Sk1erPublicMod getMod() {
        return mod;
    }

    private Sk1erPublicMod mod;
    public boolean getConfigBoolean(CValue value) {
        return getConfig().getBoolean(value);
    }
    public Sk1erConfig getConfig(){return mod.getConfig();}
    public Sk1erListener(Sk1erPublicMod mod) {
    this.mod=mod;

    }
}
