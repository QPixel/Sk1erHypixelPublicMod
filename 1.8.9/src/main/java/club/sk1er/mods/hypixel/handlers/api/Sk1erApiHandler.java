package club.sk1er.mods.hypixel.handlers.api;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public class Sk1erApiHandler {
    public String SK1ER_API_KEY = "";
    public boolean sentOutOFdate = false;
    private Sk1erPublicMod mod;
    private List<String> officers = new ArrayList<String>();
    private String guildmaster = "";
    private JSONObject user = new JSONObject();
    private JSONObject watchdogStats = new JSONObject();
    private JSONObject quests;
    private JSONObject timings = new JSONObject();
    private JSONObject questInfo;
    private HashMap<String, JSONObject> cache;
    private JSONObject specialBoosterCache = new JSONObject();

    public Sk1erApiHandler(Sk1erPublicMod mod) {
        this.mod = mod;
        cache = new HashMap<>();
    }

    public JSONObject getQuestInfo() {
        return questInfo;
    }

    public int getTiming(String key) {
        return timings.optInt(key, 60);
    }

    public JSONObject getSpecialBoosterCache() {
        return specialBoosterCache;
    }

    public void genQuests() {
        if (questInfo == null) {
            if (!mod.getFileCache().hasCache("quests")) {
                quests = new JSONObject(rawExpectJson("http://sk1er.club/css/Quests.txt"));
                mod.getFileCache().addItemAndSave("quests", quests);
            }
            if (!mod.getFileCache().hasCache("quest_info")) {
                questInfo = new JSONObject(rawExpectJson("http://sk1er.club/css/Quest_info.json"));
                mod.getFileCache().addItemAndSave("quest_info", questInfo);
            }
        }
    }

    public boolean hasBoostrs() {
        try {
            if (specialBoosterCache != null)
                return JSONObject.getNames(specialBoosterCache).length != 0;
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public JSONObject getGuildByPlayer(String player) {
        player = player.toLowerCase();
        if (cache.containsKey("guild.player." + player)) {
            return cache.get("guild.player." + player);
        } else {
            JSONObject raw = new JSONObject(rawExpectJson("http://sk1er.club/modquery/" + SK1ER_API_KEY + "/guild/player/" + player));
            cache.put("guild.player." + player, raw);
            return raw;
        }
    }

    public JSONObject getGuildByName(String player) {
        player = player.toLowerCase();
        if (cache.containsKey("guild.name." + player)) {
            return cache.get("guild.name." + player);
        } else {
            JSONObject raw = new JSONObject(rawExpectJson("http://sk1er.club/modquery/" + SK1ER_API_KEY + "/guild/name/" + player));
            cache.put("guild.name." + player, raw);
            return raw;
        }
    }

    public JSONObject getFriendsForPlayer(String player) {
        player = player.toLowerCase();
        if (cache.containsKey("friends.player." + player)) {
            return cache.get("friends.player." + player);
        } else {
            JSONObject raw = new JSONObject(rawExpectJson("http://sk1er.club/modquery/" + SK1ER_API_KEY + "/friends/" + player));
            cache.put("friends.player." + player, raw);
            return raw;
        }
    }

    public void genKey() {
        timings = new JSONObject(rawExpectJson("http://sk1er.club/css/timing.json"));
        JSONObject gen_key = new JSONObject(rawExpectJson("http://sk1er.club/genkey?name=" + Minecraft.getMinecraft().thePlayer.getName()
                + "&uuid=" + Minecraft.getMinecraft().thePlayer.getUniqueID().toString()
                + "&mcver=" + Minecraft.getMinecraft().getVersion()
                + "&modver=" + Sk1erPublicMod.VERSION
                + "&mod=PUBLIC_PRIVATE_MOD"
        ));
        if (gen_key.has("key")) {
            SK1ER_API_KEY = gen_key.getString("key");
            mod.ALLOW_AUTO_GG = gen_key.optBoolean("autogg");
            if(gen_key.optBoolean("refresh")) {
                mod.getFileCache().clear();
                questInfo =null;
                genQuests();
            }
        }
        if (gen_key.optBoolean("update")) {
            if (!sentOutOFdate) {
                ChatUtils.sendMessage("Your version (" + Sk1erPublicMod.VERSION + ") is out of date. The new version is " + gen_key.getString("new_ver"));
                sentOutOFdate = true;
            }
        }

        Multithreading.runAsync(() -> {
            while (true) {
                try {
                    Thread.sleep(1000 * 60);
                    JSONObject tmp = new JSONObject(rawExpectJson("http://sk1er.club/keyupdate/" + SK1ER_API_KEY));
                    if (tmp.optBoolean("success")) {
                        System.out.println("Successfully regained api key");

                    } else {
                        JSONObject gen_key1 = new JSONObject(rawExpectJson("http://sk1er.club/genkey?name=" + Minecraft.getMinecraft().thePlayer.getName()
                                + "&uuid=" + Minecraft.getMinecraft().thePlayer.getUniqueID().toString()
                                + "&mcver=" + Minecraft.getMinecraft().getVersion()
                                + "&modver=" + Sk1erPublicMod.VERSION
                                + "&mod=PUBLIC_PRIVATE_MOD"
                        ));
                        SK1ER_API_KEY = gen_key1.getString("key");
                        mod.ALLOW_AUTO_GG = gen_key1.optBoolean("autogg");
                        if (gen_key1.has("update") && gen_key.getBoolean("update")) {
                            if (!sentOutOFdate) {
                                ChatUtils.sendMessage("Your version (" + Sk1erPublicMod.VERSION + ") is out of date. The new version is " + gen_key.getString("new_ver"));
                                sentOutOFdate = true;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public JSONObject getGuildLeaders(String player) {
        if (cache.containsKey("guild.leaders." + player)) {
            return cache.get("guild.leaders." + player);
        } else {
            JSONObject tmp = new JSONObject(rawExpectJson("http://sk1er.club/guildmod/" + player));
            cache.put("guild.leaders." + player, tmp);
            return tmp;
        }
    }

    public void pullGuild() {
        JSONObject tmp = new JSONObject(rawExpectJson("http://sk1er.club/guildmod/" + Minecraft.getMinecraft().thePlayer.getName()));
        officers.clear();
        for (int i = 0; i < tmp.getJSONArray("officers").length(); i++) {
            officers.add(tmp.getJSONArray("officers").getString(i));
        }
        guildmaster = tmp.getJSONArray("master").getString(0);
    }

    public String getBoosters() {
        return rawExpectJson("http://sk1er.club/rawboosters/" + SK1ER_API_KEY);
    }

    public void pullSpecialBoosters() {
        specialBoosterCache = new JSONObject(rawExpectJson("https://sk1er.club/sk1erbooster/" + getUser().getJSONObject("player").getString("uuid")));
    }


    public void pullPlayerProfile() {
        JSONObject USER = new JSONObject(rawExpectJson("http://sk1er.club/data/" + Minecraft.getMinecraft().thePlayer.getName() + "/" + SK1ER_API_KEY + "/main"));
        if (USER.has("success") && USER.getBoolean("success")) {
            try {
                this.user = USER;
            } catch (Exception e) {
                mod.newError(e);
            }

        } else {
            if (USER.optString("cause").contains("key")) {
                ChatUtils.sendMessage("Your Key has expired! Attempting to generate a new one!");
            } else
                ChatUtils.sendMessage("An unkown error occurred while loading your profile (" + Minecraft.getMinecraft().thePlayer.getName() + ")");
        }

    }

    public JSONObject pullPlayerProfile(String name) {
        if (cache.containsKey("player.data." + name)) {
            return cache.get("player.data." + name);
        } else {
            JSONObject tmp = new JSONObject(rawExpectJson("http://sk1er.club/data/" + name + "/" + SK1ER_API_KEY));
            cache.put("player.data." + name, tmp);
            return tmp;
        }
    }


    public void refreshWatchogAndPlayers() {
        try {
            JSONObject st = new JSONObject(rawExpectJson("http://sk1er.club/staff/info.php"));
            watchdogStats = st;
        } catch (Exception e) {

        }
    }

    public String getStatus(String player) {
        player = player.replace(":", "");
        if (guildmaster.equalsIgnoreCase(player)) {
            return mod.getConfig().getString(CValue.GUILD_CHAT_MASTER_PREFIX);
        }
        if (officers.contains(player)) {
            return mod.getConfig().getString(CValue.GUILD_CHAT_OFFICER_PREFIX);
        } else {
            return mod.getConfig().getString(CValue.GUILD_CHAT_MEMBER_PREFIX);
        }
    }

    public String getPercentToNext() {
        if (user.has("player")) {
            int cur = user.getJSONObject("player").getInt("networkExp");
            int total = user.getJSONObject("player").getInt("networkLevel") * 2500 + 10000;
            return Math.round((double) cur / (double) total * 100.0) + "";
        } else return "";
    }

    public String getPrecentToGoal() {
        if (user.has("player")) {
            int total = XpToLevel(mod.getConfig().getInt(CValue.XP_LEVEL_GOAL) - 1);
            int cur = user.getJSONObject("player").getInt("networkExp") + XpToLevel(user.getJSONObject("player").getInt("networkLevel"));
            return Math.round((double) cur / (double) total * 10000.0) / 100.0 + "";
        } else return "";
    }

    private int XpToLevel(int level) {
        int total = 0;
        for (int i = 0; i < level; i++) {
            total += 2500 * i + 10000;
        }
        return total;
    }

    public String rawExpectJson(String url) {
        try {

            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "Mozilla/4.76 (SK1ER PUBLIC MOD V" + Sk1erPublicMod.VERSION + ")");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = reader.readLine();
            if (!(line == null)) {
                StringBuilder builder = new StringBuilder(line);
                while (line != null) {
                    line = reader.readLine();
                    if (line != null) {
                        builder.append(line);
                    }
                }
                return builder.toString();
            } else {
                return "{}";
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return "{}";
        }
    }


    public JSONObject getUser() {
        return user;
    }

    public JSONObject getWatchdogStats() {
        return watchdogStats;
    }

    public JSONObject getQuests() {
        return quests;
    }
}
