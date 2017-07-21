package club.sk1er.mods.publicmod.handlers.api;

import club.sk1er.mods.publicmod.Sk1erMod;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mitchellkatz on 7/20/17.
 */
public class Sk1erApiHandler {

    private Sk1erMod sk1erMod;
    private Sk1erPublicMod mod;
    private JsonObject personalBoosters;
    private JsonObject thePlayerData;
    private Map<String, JsonObject> friendCache = new HashMap<>();
    private Map<String, JsonObject> guildNameCache = new HashMap<>();
    private Map<String, JsonObject> guildPlayerCache = new HashMap<>();

    public Sk1erApiHandler(Sk1erMod sk1erMod, Sk1erPublicMod mod) {
        this.sk1erMod = sk1erMod;
        this.mod = mod;
    }

    public String getKey() {
        return sk1erMod.getApIKey();
    }

    public JsonObject getPlayer(String player) {
        return sk1erMod.getPlayer(player);
    }

    public JsonObject getPersonalBoosters() {
        return personalBoosters;
    }

    public void refreshPersonalBoosters() {
        this.personalBoosters = sk1erMod.getJson("http://sk1er.club/sk1erbooster/" + mod.getUuid());
    }

    public JsonObject getThePlayerData() {
        return thePlayerData;
    }

    public void refreshPlayerData() {
        this.thePlayerData = sk1erMod.getJson("http://sk1er.club/data/" + mod.getUuid() + "/" + sk1erMod.getApIKey() + "/main");
    }

    public JsonObject getFriends(String player) {
        if (friendCache.containsKey(player.toLowerCase())) {
            return friendCache.get(player.toLowerCase());
        }
        JsonObject json = sk1erMod.getJson("http://sk1er.club/modquery/" + sk1erMod.getApIKey() + "/friends/" + player);
        friendCache.put(player.toLowerCase(), json);
        return json;
    }

    public JsonObject getGuildPlayer(String player) {
        if (guildPlayerCache.containsKey(player.toLowerCase())) {
            return guildPlayerCache.get(player.toLowerCase());
        }
        JsonObject json = sk1erMod.getJson("http://sk1er.club/modquery/" + sk1erMod.getApIKey() + "/guild/player/" + player);
        guildPlayerCache.put(player.toLowerCase(), json);
        return json;
    }

    public JsonObject getGuildNmae(String name) {
        if (guildNameCache.containsKey(name.toLowerCase())) {
            return guildNameCache.get(name.toLowerCase());
        }
        JsonObject json = sk1erMod.getJson("http://sk1er.club/modquery/" + sk1erMod.getApIKey() + "/guild/name/" + name);
        guildNameCache.put(name.toLowerCase(), json);
        return json;
    }


}
