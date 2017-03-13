package club.sk1er.mods.hypixel.handlers.scrips;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.hypixel.api.GameType;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.EnumChatFormatting;

/**
 * Created by mitchellkatz on 1/30/17.
 */
public class HypixelMinuteScrips {
    private Sk1erPublicMod mod;

    public HypixelMinuteScrips(Sk1erPublicMod mod) {
        this.mod = mod;
    }
    public boolean run = false;
    int time = 0;

    public void run() {

        run = true;
        while (mod.isHypixel && run) {

            time++;
            long l = System.currentTimeMillis();
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
                    e.printStackTrace();
                }
                if (Minecraft.getMinecraft().inGameHasFocus) {
                    boolean boost = false;
                    if (time % 5 == 0) {
                        mod.getApiHandler().refreshWatchogAndLiveCoins();
                        if(mod.getApiHandler().hasBoostrs()) {
                            mod.getApiHandler().pullSpecialBoosters();
                                boost=true;
                        }
                    }
                    if (time % 60 == 0) {
                        mod.getApiHandler().pullPlayerProfile();
                        mod.getDataSaving().addCoins(0);
                    }
                    if (time % 60*5 == 0) {
                        mod.getApiHandler().pullGuild();
                        if(!boost)
                            mod.getApiHandler().pullSpecialBoosters();
                    }
                }
                int t = (int) Math.max(0, 1000 - (System.currentTimeMillis() - l));
                if (t > 0) {
                    Thread.sleep(t);
                }


            } catch (Exception e) {
                mod.newError(e);
            }
        }
    }

    public void stop() {
        run = false;
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
