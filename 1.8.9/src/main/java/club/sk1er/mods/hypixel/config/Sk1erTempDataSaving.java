package club.sk1er.mods.hypixel.config;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import net.minecraft.client.Minecraft;
import org.json.JSONObject;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by mitchellkatz on 12/2/16.
 */
public class Sk1erTempDataSaving {

    private int xp = 0, coins = 0;

    public int getXp() {
        return xp;
    }

    public int getCoins() {
        return coins;
    }

    public HashMap<String, Integer> getXpForGame() {
        return xpForGame;
    }

    public HashMap<String, Integer> getCoinsForGame() {
        return coinsForGame;
    }

    private HashMap<String, Integer> xpForGame = new HashMap<>();
    private HashMap<String, Integer> coinsForGame = new HashMap<>();
    private HashMap<String, Integer> QUEST_STATUS = new HashMap<>();

    public int getRankedRating() {
        return rankedRating;
    }

    public void setRankedRating(int rankedRating) {
        this.rankedRating = rankedRating;
    }

    private int rankedRating = 0;
    String lastDate = date();

    public int getXpForGame(String game) {
        if (xpForGame.containsKey(game)) {
            return xpForGame.get(game);

        }
        return 0;
    }

    public int getQuestStatus(String quest) {
        if (QUEST_STATUS.containsKey(quest)) {
            return QUEST_STATUS.get(quest);
        } else {
            return 0;
        }
    }

    public void applyQuestStatus(String quest, int status) {
        QUEST_STATUS.put(quest, status);
        refreshCoinsAndXp(0, 0);
    }

    public int getCoinsForGame(String game) {
        if (coinsForGame.containsKey(game)) {
            return coinsForGame.get(game);
        }
        return 0;
    }

    public String getLastDate() {
        return lastDate;
    }

    public Sk1erTempDataSaving() {
        File dir = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/sk1ermod");
        if (!dir.exists()) {

            dir.mkdirs();
        } else {
            File f = new File(dir.getAbsolutePath() + "/" + date() + ".txt");
            if (f.exists()) {
                try {
                    FileReader fr = new FileReader(f);
                    BufferedReader br = new BufferedReader(fr);
                    String tmp = br.readLine();
                    try {
                        JSONObject object = new JSONObject(tmp);
                        xp = object.getInt("xp");
                        coins = object.getInt("coins");
                        String[] xp_nmes = JSONObject.getNames(object.getJSONObject("game_xp"));
                        if (xp_nmes != null) {
                            for (String name : xp_nmes) {
                                xpForGame.put(name, object.getJSONObject("game_xp").getInt(name));
                            }
                        }
                        for (String s : JSONObject.getNames(object.getJSONObject("quests"))) {
                            QUEST_STATUS.put(s, object.getJSONObject("quests").getInt(s));
                        }
                        String[] coin_nmes = JSONObject.getNames(object.getJSONObject("game_coin"));
                        if (coin_nmes != null) {
                            for (String name : coin_nmes) {
                                coinsForGame.put(name, object.getJSONObject("game_coin").getInt(name));
                            }
                        }
                        br.close();
                        fr.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                }
            }
            File f2 = new File(dir.getAbsolutePath() + "/" + dateMM() + ".json");
            if (f2.exists()) {
                try {
                    FileReader fr = new FileReader(f2);
                    BufferedReader br = new BufferedReader(fr);
                    JSONObject object = new JSONObject(br.readLine());
                    rankedRating = object.optInt("rating");
                    br.close();
                    fr.close();
                } catch (Exception e) {

                }
            }
        }

    }

    public String dateMM() {
        return new SimpleDateFormat("MM-YY").format(new Date(System.currentTimeMillis()));
    }

    public String date() {
        return new SimpleDateFormat("dd-MM-yy").format(new Date(System.currentTimeMillis()));
    }

    public void addXp(int amount) {
        if (!xpForGame.containsKey(Sk1erPublicMod.getInstance().getCurrentGame())) {
            xpForGame.put(Sk1erPublicMod.getInstance().getCurrentGame(), 0);
        }
        xpForGame.put(Sk1erPublicMod.getInstance().getCurrentGame(), xpForGame.get(Sk1erPublicMod.getInstance().getCurrentGame()) + amount);
        refreshCoinsAndXp(0, amount);
    }

    private void refreshCoinsAndXp(int coins, int xp) {
        if (date().equalsIgnoreCase(lastDate)) {
            this.coins += coins;
            this.xp += xp;
            File f = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/sk1ermod/" + date() + ".txt");
            try {
                FileWriter fw = new FileWriter(f);
                BufferedWriter bw = new BufferedWriter(fw);
                JSONObject object = new JSONObject();
                object.put("xp", this.xp).put("coins", this.coins);
                JSONObject xpThing = new JSONObject();
                for (String string : xpForGame.keySet()) {
                    xpThing.put(string, xpForGame.get(string));
                }
                object.put("game_xp", xpThing);
                JSONObject coinThing = new JSONObject();
                for (String string : coinsForGame.keySet()) {
                    coinThing.put(string, coinsForGame.get(string));
                }
                JSONObject quest = new JSONObject();
                for (String quests : QUEST_STATUS.keySet()) {
                    quest.put(quests, QUEST_STATUS.get(quests));
                }
                object.put("quests", quest);
                object.put("game_coin", coinThing);
                bw.write(object.toString());
                bw.close();
                fw.close();
            } catch (IOException e) {
            }
            File f2 = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/sk1ermod/" + dateMM() + ".json");
            try {
                if (!f2.exists()) {
                    f2.createNewFile();
                }
                FileWriter fw = new FileWriter(f2);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(new JSONObject().put("rating", rankedRating).toString());
                bw.close();
                fw.close();
            } catch (Exception e) {

            }
        } else {
            this.coins = coins;
            this.xp = xp;
            lastDate = date();
            xpForGame.clear();
            coinsForGame.clear();
            refreshCoinsAndXp(coins, xp);
            for(String s : QUEST_STATUS.keySet()) {
                QUEST_STATUS.put(s,0);
            }
            for(HypixelQuest quest : HypixelQuest.allQuests) {
                quest.setCompleted(false);
            }
        }
    }

    public void addCoins(int amount) {
        if (!coinsForGame.containsKey(Sk1erPublicMod.getInstance().getCurrentGame())) {
            coinsForGame.put(Sk1erPublicMod.getInstance().getCurrentGame(), 0);
        }
        coinsForGame.put(Sk1erPublicMod.getInstance().getCurrentGame(), coinsForGame.get(Sk1erPublicMod.getInstance().getCurrentGame()) + amount);
        refreshCoinsAndXp(amount, 0);
    }
}
