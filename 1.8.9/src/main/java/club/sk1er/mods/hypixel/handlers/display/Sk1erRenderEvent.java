package club.sk1er.mods.hypixel.handlers.display;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.listeners.Sk1erListener;
import net.hypixel.api.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by mitchellkatz on 12/1/16.
 */
public class Sk1erRenderEvent extends Sk1erListener {

    public Sk1erRenderEvent(Sk1erPublicMod mod) {
        super(mod);
    }

    private double line = .1;
    public static List<
            RenderObject> renderObjects = new ArrayList<>();
    private String pColor;
    private String sColor;

    @SubscribeEvent
    public void onRenderEvent(TickEvent.RenderTickEvent e) {
        try {
            ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
            for (RenderObject object : renderObjects) {
                Minecraft.getMinecraft().fontRendererObj.drawString(object.text, object.x / res.getScaleFactor(), object.y / res.getScaleFactor(), 16777215);
            }
            if (getMod().isHypixel) {
                line = .1;
                pColor = getConfig().getString(CValue.DISPLAY_PRIMARY_COLOR).replace("&", C.COLOR_CODE_SYMBOL);
                sColor = getConfig().getString(CValue.DISPLAY_SECONDARY_COLOR).replace("&", C.COLOR_CODE_SYMBOL);
                if (Minecraft.getMinecraft().ingameGUI.getChatGUI().getChatOpen()) {
                    if (getMod().isMovingCustomDisplay) {
                        render(pColor + Sk1erPublicMod.NAME + " V." + Sk1erPublicMod.VERSION);
                        spacer();
                    } else {
                        render("Drag to relocate");
                        spacer();
                    }
                } else {
                    if (getConfigBoolean(CValue.DISPLAY_WATERMARK))
                        render(pColor + Sk1erPublicMod.NAME + " V." + Sk1erPublicMod.VERSION);
                    spacer();
                }
                JSONObject wdstats = getMod().getApiHandler().getWatchdogStats();
                if (getConfigBoolean(CValue.DISPLAY_ONLINE_PLAYERS)) {

                    render(pColor + "Online players: " + sColor + wdstats.optString("players", "Disabled from Webserver"));
                }
                if (getConfigBoolean(CValue.DISPLAY_WATCHDOG_BANS)) {
                    spacer();
                    if (getConfigBoolean(CValue.WATCHDOG_BANS_MIN)) {
                        render(pColor + "Watchdog bans last min: " + sColor + wdstats.optInt("watchdog_lastMinute", -1));
                    }
                    if (getConfigBoolean(CValue.WATCHDOG_BANS_DAY)) {
                        render(pColor + "Watchdog bans last day: " + sColor + wdstats.optInt("watchdog_rollingDaily", -1));
                    }
                    if (getConfigBoolean(CValue.WATCHDOG_BANS_TOTAL)) {
                        render(pColor + "Watchdog total bans: " + sColor + wdstats.optInt("watchdog_total", -1));
                    }
                }
                boolean didXpUnit = false;
                if (getConfigBoolean(CValue.XP_EARNED_DAY)) {
                    spacer();
                    didXpUnit = true;
                    render(pColor + "XP earned today: " + sColor + getMod().getDataSaving().getXp());
                }
                if (getConfigBoolean(CValue.COINS_EARNED_DAY)) {
                    if (!didXpUnit)
                        spacer();
                    render(pColor + "Coins earned today: " + sColor + getMod().getDataSaving().getCoins());
                }
                boolean didPer = false;
                if (getConfigBoolean(CValue.PERCENT_TO_NEXT_LEVEL)) {
                    spacer();
                    didPer = true;
                    render(pColor + "Percent to next: " + sColor + getMod().getApiHandler().getPercentToNext() + "%");
                }
                if (getConfigBoolean(CValue.PERCENT_TO_NEXT_GOAL)) {
                    if (!didPer)
                        spacer();
                    render(pColor + "Percent to goal: " + sColor + getMod().getApiHandler().getPrecentToGoal() + "%");
                }
                if (getConfigBoolean(CValue.DISPLAY_BOOSTERS_DAY)) {
                    JSONObject tmp = getMod().getApiHandler().getSpecialBoosterCache();
                    String[] names = JSONObject.getNames(tmp);
                    if (names != null && names.length > 0) {
                        spacer();
                        for (String name : names) {
                            try {
                                JSONObject tmp1 = tmp.getJSONObject(name);
                                String nice_time = tmp1.getString("nice_time");
                                if (nice_time.equalsIgnoreCase("60:00")) {
                                    render(pColor + name + " " + sColor + "starts at " + pColor + (new SimpleDateFormat("hh:mm:ss").format(new Date(tmp1.getLong("activate_time")))));
                                } else {
                                    render(pColor + name + " " + sColor + nice_time);
                                }
                            } catch (Exception e2) {

                            }
                        }
                    }
                }
                try {
                    String GAME_NAME = getMod().getCurrentGame();
                    if (GAME_NAME.equals("")) {
                        throw new Exception(" ");
                    }
                    boolean gs = false;
                    if (getConfigBoolean(CValue.DISPLAY_CURRENT_GAME)) {
                        spacer();
                        gs = true;
                        render(pColor + "Game: " + sColor + GAME_NAME);
                    }
                    if (getConfigBoolean(CValue.DISPLAY_CURRENT_GAME_XP)) {
                        if (!gs) {
                            gs = true;
                            spacer();
                        }
                        render(pColor + "XP in " + GAME_NAME + ": " + sColor + getMod().getDataSaving().getXpForGame(GAME_NAME));
                    }
                    if (getConfigBoolean(CValue.DISPLAY_CURRENT_GAME_COINS)) {
                        if (!gs) {
                            spacer();
                        }
                        render(pColor + "Coins in " + GAME_NAME + ": " + sColor + getMod().getDataSaving().getCoinsForGame(GAME_NAME));
                    }

                } catch (Exception e2) {
                    spacer();
                    render("Game: Unknown");
                }

                if (getConfigBoolean(CValue.DISPLAY_RANKED_RATING)) {
                    spacer();
                    render(pColor + "Ranked rating: " + sColor + getMod().getDataSaving().getRankedRating());
                }
                if (getConfigBoolean(CValue.DISPLAY_QUESTS)) {
                    spacer();
                    try {
                        List<HypixelQuest> quests = HypixelQuest.getQuestForGame(GameType.SKYWARS);
                        if (quests == null) {
                            return;
                        }

                        for (HypixelQuest quest : quests) {
                            if (quest == null) {
                                System.out.println("Quest is null!");
                            } else
                                if(quest.isEnabled())
                                render(quest.getFrontEndName(), quest.isCompleted() ? C.GREEN + "Completed" : C.RED + "Not completed");
                        }


                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            }
        } catch (Exception e2) {
            getMod().newError(e2);
        }
    }

    private void spacer() {
        render("");
    }

    private void render(String main, String sec) {
        render(pColor + main + " " + sColor + sec);
    }

    private void render(String s) {
        //TODO add scale system for text
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        double x = getConfig().getDouble(CValue.CUSTOM_DISPLAY_LOCATION_X)
                * res.getScaledWidth_double();
        double y = getConfig().getDouble(CValue.CUSTOM_DISPLAY_LOCATION_Y) * res.getScaledHeight_double() + (line * 10);

        Minecraft.getMinecraft().fontRendererObj.drawString(s, (int) x - (getConfig().getString(CValue.DISPLAY_LOCATION_ALIGN).equalsIgnoreCase("RIGHT") ? Minecraft.getMinecraft().fontRendererObj.getStringWidth(s) : 0), (int) y, 16777215);
        // System.out.println("Rendering " + s +"X: " + x +" Y: " + y);
        line += 1.0;
    }


}
