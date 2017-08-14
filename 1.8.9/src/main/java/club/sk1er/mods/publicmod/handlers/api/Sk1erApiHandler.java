package club.sk1er.mods.publicmod.handlers.api;

import club.sk1er.mods.publicmod.C;
import club.sk1er.mods.publicmod.Sk1erMod;
import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.config.ConfigOpt;
import club.sk1er.mods.publicmod.handlers.quest.HypixelQuest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by mitchellkatz on 7/20/17.
 */
public class Sk1erApiHandler {

    private Sk1erMod sk1erMod;
    private Sk1erPublicMod mod;
    private JsonObject personalBoosters = new JsonObject();
    private JsonObject thePlayerData = new JsonObject();
    private Map<String, JsonObject> friendCache = new HashMap<>();
    private Map<String, JsonObject> guildNameCache = new HashMap<>();
    private Map<String, JsonObject> guildPlayerCache = new HashMap<>();
    private JsonObject playerGuild;
    private HashMap<String, String> guildPlayerPrefixes = new HashMap<>();
    @ConfigOpt
    private String guildMemberPrefix = C.GREEN + "[M]";
    @ConfigOpt
    private String guildOfficerPrefix = C.RED + "[O]";
    @ConfigOpt
    private String guildMasterPrefix = C.GOLD + "[GM]";
    private JsonObject quests = new JsonObject();
    private JsonObject timings = new JsonObject();
    private JsonObject watchdogStats = new JsonObject();

    public Sk1erApiHandler(Sk1erMod sk1erMod, Sk1erPublicMod mod) {
        this.sk1erMod = sk1erMod;
        this.mod = mod;
    }

    public void fetchQuests() {
        quests = sk1erMod.getJson("http://sk1er.club/css/Quest_info.json");
        Set<Map.Entry<String, JsonElement>> entries = quests.entrySet();
        Iterator<Map.Entry<String, JsonElement>> iterator = entries.iterator();
        HypixelQuest.allQuests.clear();
        while (iterator.hasNext()) {
            Map.Entry<String, JsonElement> next = iterator.next();
            HypixelQuest.allQuests.add(new HypixelQuest(next.getKey()));
        }
    }

    public void fetchTimings() {
        timings = sk1erMod.getJson("http://sk1er.club/newtiming.json");
    }

    public void refreshWatchdogStats() {
        //No need for .php anymore.
        watchdogStats = sk1erMod.getJson("http://sk1er.club/staff/");
    }

    public void fetchPlayerGuild() {
        playerGuild = getGuildPlayer(mod.getPlayerName());
        guildPlayerPrefixes.clear();
        if (playerGuild.has("guild") && playerGuild.get("guild").getAsJsonObject().has("members")) {
            JsonArray members = playerGuild.get("guild").getAsJsonObject().get("members").getAsJsonArray();
            for (int i = 0; i < members.size(); i++) {
                JsonObject object = members.get(i).getAsJsonObject();
                if (object.has("rank") && !object.get("rank").getAsString().equalsIgnoreCase("member")) {
                    if (object.get("rank").getAsString().equalsIgnoreCase("officer")) {
                        guildPlayerPrefixes.put(object.get("name").getAsString().toLowerCase(), guildOfficerPrefix);
                    } else {
                        guildPlayerPrefixes.put(object.get("name").getAsString().toLowerCase(), guildMasterPrefix);
                    }
                }
            }
        }
    }

    public String getPrefixForUser(String player) {
        return guildPlayerPrefixes.getOrDefault(player.toLowerCase(), guildMemberPrefix);
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

    public int getTiming(String key) {
        return timings.has(key) ? timings.get(key).getAsInt() : 60;
    }

    public void refreshPersonalBoosters() {
        this.personalBoosters = sk1erMod.getJson("https://sk1er.club/sk1erbooster/" + mod.getUuid());
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

    public JsonObject getGuildByName(String name) {
        if (guildNameCache.containsKey(name.toLowerCase())) {
            return guildNameCache.get(name.toLowerCase());
        }
        JsonObject json = sk1erMod.getJson("http://sk1er.club/modquery/" + sk1erMod.getApIKey() + "/guild/name/" + name);
        guildNameCache.put(name.toLowerCase(), json);
        return json;
    }


    public JsonObject getQuests() {
        return quests;
    }

    public boolean hasBoostrs() {
        return personalBoosters.entrySet().size() != 0;
    }

    public JsonObject getWatchdogStats() {
        return watchdogStats;
    }
}
