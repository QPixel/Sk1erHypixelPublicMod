package club.sk1er.mods.publicmod;

/**
 * Created by mitchellkatz on 6/9/17.
 */

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitchell Katz on 6/8/2017.
 */
public class Sk1erMod {
    private static Sk1erMod instance;
    private List<String> updateMessage = new ArrayList<>();
    private String modid;
    private String version;
    private boolean enabled = true;
    private boolean hasUpdate = false;
    private String name;
    private String apIKey;
    private String prefix;

    public Sk1erMod(String modid, String version, String name) {
        this.modid = modid;
        this.version = version;
        this.name = name;
        instance = this;
        prefix = EnumChatFormatting.RED + "[" + EnumChatFormatting.AQUA + "" + this.name + EnumChatFormatting.RED + "]" + EnumChatFormatting.YELLOW + ": ";
    }

    public static Sk1erMod getInstance() {
        return instance;
    }

    public boolean hasUpdate() {
        return hasUpdate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public List<String> getUpdateMessage() {
        return updateMessage;
    }

    public String getApIKey() {
        return apIKey;
    }

    public void sendMessage(String message) {
        EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
        if (thePlayer != null)
            thePlayer.addChatComponentMessage(new ChatComponentText(prefix + message));
    }

    public JsonObject getPlayer(String name) {
        return new JsonParser().parse(rawWithAgent("http://sk1er.club/data/" + name + "/" + getApIKey())).getAsJsonObject();
    }

    public void checkStatus() {
        Multithreading.runAsync(() -> {
            JsonObject en = new JsonParser().parse(rawWithAgent("http://sk1er.club/genkey?name=" + Minecraft.getMinecraft().getSession().getProfile().getName()
                    + "&uuid=" + Minecraft.getMinecraft().getSession().getPlayerID().replace("-", "")
                    + "&mcver=" + Minecraft.getMinecraft().getVersion()
                    + "&modver=" + version
                    + "&mod=" + modid
            )).getAsJsonObject();
            updateMessage.clear();
            enabled = en.get("enabled").getAsBoolean();
            hasUpdate = en.get("update").getAsBoolean();
            apIKey = en.get("key").getAsString();
            if (hasUpdate) {
                updateMessage.add(prefix + "----------------------------------");
                updateMessage.add(prefix + " ");
                updateMessage.add(prefix + "            " + name + " is out of date!");
                updateMessage.add(prefix + "Update level: " + en.get("level").getAsString());
                updateMessage.add(prefix + "Update URL: " + en.get("url").getAsString());
                updateMessage.add(prefix + "Message from Sk1er: ");
                updateMessage.add(prefix + "----------------------------------");
                updateMessage.add(prefix + "  ");
                updateMessage.add(prefix + "----------------------------------");
            }
        });
    }

    @SubscribeEvent
    public void onLoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (hasUpdate()) {
            Multithreading.runAsync(() -> {
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (Minecraft.getMinecraft().thePlayer == null) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                for (String s : getUpdateMessage()) {
                    Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(s));
                }
            });
        }
    }

    public JsonObject getJson(String url) {
        return new JsonParser().parse(rawWithAgent(url)).getAsJsonObject();
    }

    public String rawWithAgent(String url) {
        System.out.println("Fetching: " + url);
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(true);
            connection.addRequestProperty("User-Agent", "Mozilla/4.76 (" + modid + " V" + version + ")");
            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);
            InputStream is = connection.getInputStream();
            Charset encoding = Charset.defaultCharset();
            String s = IOUtils.toString(is, encoding);
            if (s != null)
                return s;

        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonObject object = new JsonObject();
        object.addProperty("success", false);
        object.addProperty("cause", "Exception");
        return object.toString();
    }
}
