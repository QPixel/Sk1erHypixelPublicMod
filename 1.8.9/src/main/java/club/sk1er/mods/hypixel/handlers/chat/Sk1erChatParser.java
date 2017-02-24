package club.sk1er.mods.hypixel.handlers.chat;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.config.CValue;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

/**
 * Created by mitchellkatz on 12/1/16.
 */
public class Sk1erChatParser extends Sk1erChatHandler{

    public Sk1erChatParser(Sk1erPublicMod mod ) {
        super(mod);
    }
    private long lastSeenLevelUp=0;
    @Override
    public boolean containsTrigger(ClientChatReceivedEvent event) {
        String raw = event.message.getUnformattedText().replace("\n","").replace("\r","");
        if(raw.contains("+") && raw.contains("coins!"))
            return true;
        if(raw.contains("+") && raw.contains("Hypixel Experience"))
            return true;
        if(raw.contains("Rating") && raw.contains("(") && raw.contains(")"))
            return true;
        if(raw.contains("LEVEL UP")) {
            lastSeenLevelUp = System.currentTimeMillis();
        }
        if(System.currentTimeMillis()-lastSeenLevelUp<1000)
            return true;
        if(raw.contains("Achievement Unlocked: ") && !raw.contains("Guild"))
            return true;
        if(raw.startsWith("Your") && raw.contains("Triple Coins Booster"))
            return true;
        if(raw.contains("got lucky and found a " ) && raw.contains(Minecraft.getMinecraft().thePlayer.getName()))
            return true;
        if(raw.startsWith("Daily Quest:") || raw.startsWith("Weekly Quest:"))
            //TODO this does not parse weekly quesats
            return true;

        return false;
    }
    private final String nums = "0123456789";
    @Override
    public void handle(ClientChatReceivedEvent e) {
        boolean color=  getConfig().getBoolean(CValue.COLORED_GUILD_CHAT);
        String wrk =e.message.getUnformattedText().replace('\u00A7'+"", "").replace("\r", "").replace("\n", "");
        if(wrk.contains("Rating") && wrk.contains("(") && wrk.contains(")")) {
            String split = wrk.split("\\(")[1];
            String tmp ="";
            for(char c : split.toCharArray()) {
                if(nums.contains(c+"")) {
                    tmp+=c;
                }
            }
            try {
               getMod().getDataSaving().setRankedRating(Integer.parseInt(tmp));
            } catch(Exception a) {
            getMod().newError(a);
            }
        }
        if(wrk.contains("Achievement Unlocked: ") && !wrk.contains("Guild") && !wrk.contains(Minecraft.getMinecraft().thePlayer.getName()) && Sk1erPublicMod.getInstance().getConfig().getBoolean(CValue.DISPLAY_GUILD_CHAT_ACHIEVEMENTS)) {
            //TODO fix this from reboardcasting
            ChatUtils.sendMesssageToServer("/gchat " + (color ? e.message.getFormattedText().replace(C.COLOR_CODE_SYMBOL,"~") : EnumChatFormatting.getTextWithoutFormattingCodes(e.message.getUnformattedText())));
        }
        if(wrk.contains("Daily Quest: ") || wrk.startsWith("Weekly Quest: ") && wrk.contains("Completed!") && wrk.contains("+")) {
            String raw = StringUtils.stripControlCodes(e.message.getUnformattedText().replace('\u00A7'+"", "").replace("\r", "").replace("\n", ""));
            String quest_line = raw.split("!")[0];
            String name = quest_line.split(":")[1].toLowerCase().replace("completed","").trim();
            HypixelQuest quest = HypixelQuest.fromDisplayName(name);
            try {
                if(quest !=null)
                quest.complete();
                else ChatUtils.sendMessage("Quest '" + name + "' was not parsed correctly!");
            } catch (Exception a) {
                getMod().newError(a);
            }

        }
        if(wrk.startsWith("Your") && wrk.contains("Triple Coins Booster")) {
            String smp = wrk.split(" ")[1];
            String tmp = "";
            for(String s : wrk.split(" ")) {
                if(s.equals("Your")) {
                    continue;
                }
                if(s.equalsIgnoreCase("Triple")) {
                    break;
                }
                tmp = tmp + " "+ s;
            }
            ChatUtils.sendMesssageToServer("/gchat " + (color ? "~c[P2W ALERT]" + "~f I queued " + (tmp.startsWith("an") ? "an" : "a") + "~b" + tmp + "~f booster!" : "[P2W ALERT]" + " I queued " + (tmp.startsWith("an") ? "an" : "a") + "" + tmp + " booster!"));

        }
        if(wrk.contains("You are now Hypixel Level") || wrk.contains("You are now level")) {
            boolean next = false;
            String level = "";
            for(String s : wrk.split(" ")) {
                if(next) {
                    level = s;
                }
                if(s.equalsIgnoreCase("level")) {
                    next = true;
                }
            }
            if(color) {
                ChatUtils.sendMesssageToServer("/gchat " + "~a~kP~r ~6Level Up! ~a~kP~r~7 I am now ~3Hypixel Level " + level);
            } else {
                ChatUtils.sendMesssageToServer("/gchat " + "Level Up! I am now Hypixel Level " + level);
            }

        }
        if(wrk.contains("got lucky and found a " ) && wrk.contains(Minecraft.getMinecraft().thePlayer.getName())) {
            String tmp = wrk.split("got lucky and found a")[1];
            ChatUtils.sendMesssageToServer("/gchat " + (color? "/gchat " + "~a~kP~r ~dWarlords Legendary! ~a~kP~r~6 " +tmp : "/gchat " + "Warlords Legendary!  " +tmp) );
        }
        handleXP(e);

    }
    public void handleXP(ClientChatReceivedEvent e) {
        String wrk = StringUtils.stripControlCodes(e.message.getUnformattedText().replace("\u00A7.", "").replace("+", "PLUS").replace("\r", "").replace("\n", ""));
        if (wrk.contains("PLUS") && wrk.contains("coins")) {
            String iso = wrk.replace("PLUS", "").replace("coins", "");
            iso = iso.trim();
            if (iso.contains(" ")) {
                iso = iso.split(" ")[0];
            }
            try {
                addCoins(Integer.parseInt(iso.replace(",", "").trim()));
            } catch (NumberFormatException en) {
            }
        }
        if (wrk.contains("PLUS") && wrk.contains("Hypixel Experience")) {
            String last = "";
            String now = "";
            if (wrk.contains("Challenge Completed")) {
                addXp(2400);
                return;
            }
            for (int i = 0; i < wrk.split("PLUS").length; i++) {
                String s = wrk.split("PLUS")[i];
                if (i > 0) {
                    last = wrk.split("PLUS")[i - 1];
                }
                if (s.contains("Hypixel Experience")) {
                    String iso = "";
                    try {
                        iso = s.replace("Hypixel Experience", "");
                        iso = iso.trim();
                        try {
                            addXp(Integer.parseInt(iso.replace(",", "").trim()));
                        } catch (NumberFormatException en) {
                            en.printStackTrace();
                        }
                    } catch (Exception es) {
                        String[] tmp = new String[es.getStackTrace().length + 1];
                        int in = 0;
                        for (StackTraceElement sn : es.getStackTrace()) {
                            tmp[in] = "ERROR: " + sn.getClassName() + " AT LINE: " + sn.getLineNumber();
                            in++;
                        }
             }
                }
                if (s.contains("Coins")) {
                    String iso = "";
                    try {
                        if (s.contains("PLUS")) {
                            iso = s.split("PLUS")[1].split(" ")[0];
                        } else {
                            iso = s.split(" ")[0];
                        }
                        iso = iso.trim();
                        try {
                            addCoins(Integer.parseInt(iso.replace(",", "").trim()));
                        } catch (NumberFormatException en) {
                           }
                        // +2400 Hypixel Experience

                    } catch (Exception es) {

                     }
                }
            }
        }
        }

    public void addCoins(int coins) {
       getMod().getDataSaving().addCoins(coins);
    }
    public void addXp(int xp) {
        getMod().getDataSaving().addXp(xp);
    }

}