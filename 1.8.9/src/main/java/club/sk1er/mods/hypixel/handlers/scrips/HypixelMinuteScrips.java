package club.sk1er.mods.hypixel.handlers.scrips;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.api.Sk1erApiHandler;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.hypixel.api.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

import java.util.concurrent.TimeUnit;

/**
 * Created by mitchellkatz on 1/30/17.
 */
public class HypixelMinuteScrips {
    public boolean run = false;
    int time = 0;
    private Sk1erPublicMod mod;

    public HypixelMinuteScrips(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    public void run() {
        Multithreading.schedule(() -> {
            if (mod.isHypixel) {
                time++;
                try {
                    try {
                        Scoreboard scoreboard = Minecraft.getMinecraft().thePlayer.getWorldScoreboard();
                        String GAME_NAME = scoreboard.getObjectiveInDisplaySlot(1).getDisplayName();
                        GAME_NAME = EnumChatFormatting.getTextWithoutFormattingCodes(GAME_NAME);
                        try {
                            GameType type = GameType.fromRealPersonName(GAME_NAME);
                            try {
                                mod.setCurrentGame(type.getName());
                                mod.setCurrentGameType(type);
                                ChatUtils.sendDebug("Setting game = " + type);

                            } catch (NullPointerException e) {
                            }
                        } catch (Exception e3) {
                            mod.setCurrentGame(parse(GAME_NAME));
                            mod.setCurrentGameType(GameType.UNKNOWN);
                        }
                    } catch (Exception e) {
                    }
                    if (Minecraft.getMinecraft().inGameHasFocus) {
                        Sk1erApiHandler handler = mod.getApiHandler();
                        boolean boost = false;
                        if (time % handler.getTiming("boosters_live") == 0) {
                            if (mod.getApiHandler().hasBoostrs()) {
                                mod.getApiHandler().pullSpecialBoosters();
                                boost = true;
                            }
                        }
                        if (time % handler.getTiming("watchdog_players") == 0) {
                            mod.getApiHandler().refreshWatchogAndPlayers();
                        }
                        if (time % handler.getTiming("player_profile") == 0) {
                            mod.getApiHandler().pullPlayerProfile();
                        }
                        if (time % handler.getTiming("coin") == 0) {
                            mod.getDataSaving().addCoins(0);
                        }
                        if (time % handler.getTiming("boosters_check") == 0) {
                            if (!boost)
                                mod.getApiHandler().pullSpecialBoosters();
                        }
                        if (time % handler.getTiming("guild") == 0) {
                            mod.getApiHandler().pullGuild();
                        }
                    }


                } catch (Exception e) {
                    mod.newError(e);

                }
            }
        }, 0, 1, TimeUnit.SECONDS);
    }


    public String parse(String name) {
        if (name.equalsIgnoreCase("hypixel")) {
            return mod.getCurrentGame();
        }
        name = name.toLowerCase();
        switch (name) {
            case "mini walls":
                return "Arcade";

        }

        return "Unknown";
    }
}
