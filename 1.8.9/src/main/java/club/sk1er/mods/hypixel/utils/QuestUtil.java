package club.sk1er.mods.hypixel.utils;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by mitchellkatz on 2/9/17.
 */
public class QuestUtil {

    public static String parseFromChat(boolean daily, String name) {
        JSONObject allQuests = Sk1erPublicMod.getInstance().getApiHandler().getQuests();
        for (String GAME_NAME : allQuests.getNamess()) {
            JSONArray game = allQuests.getJSONArray(GAME_NAME);
            for (int i = 0; i < game.length(); i++) {
                JSONObject ins = game.getJSONObject(i);
                if (ins.getString("type").equalsIgnoreCase("daily") && daily) {
                    if (name.toLowerCase().contains(ins.getString("desc").toLowerCase())) {
                        return ins.getString("backend");
                    }
                } else {

                }
            }
        }
        return "Undefined";
    }
}
