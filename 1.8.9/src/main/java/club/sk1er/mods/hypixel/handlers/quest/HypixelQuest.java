package club.sk1er.mods.hypixel.handlers.quest;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import club.sk1er.mods.hypixel.utils.Sk1erDateUtil;
import net.hypixel.api.GameType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 2/10/17.
 */
public class HypixelQuest {


    public static List<HypixelQuest> allQuests = new ArrayList<>();

    public static HypixelQuest fromBackend(String name) {
        for (HypixelQuest quest : allQuests) {
            if (quest.backendName.equalsIgnoreCase(name)) {
                return quest;
            }
        }
        HypixelQuest quest = new HypixelQuest(name);
        allQuests.add(quest);
        return quest;
    }

    @Deprecated
    public static HypixelQuest fromDisplayName(String displayName) {
        for (HypixelQuest quest : allQuests) {
            if (quest.getFrontEndName().equalsIgnoreCase(displayName))
                return quest;
        }
        ChatUtils.sendMessage("Please report this to Sk1er: Error type = Quest.NOT_LOADED. Id: " + displayName);
        return null;
    }

    public static HypixelQuest fromDisplayName(String displayName, GameType type) {
        for (HypixelQuest quest : allQuests) {
            if (quest.getFrontEndName().equalsIgnoreCase(displayName) && quest.getGameType().equals(type))
                return quest;
        }
        ChatUtils.sendMessage("Please report this to Sk1er: Error type =  Quest.NOT_LOADED. Id: " + displayName
                + " current parsed GameType: " + Sk1erPublicMod.getInstance().getCurrentGameType().getName()
                + " Display: " + Sk1erPublicMod.getInstance().getCurrentGame());
        return fromDisplayName(displayName);
    }


    public static List<HypixelQuest> getQuestForGame(GameType type) {
        return type.getGameHandler().getQuests();
    }

    private QuestType type;

    public String getBackendName() {
        return backendName;
    }

    private String backendName;
    private long lastCompleted;
    private JSONObject quest_data;
    private GameType gameType;

    public boolean isCompleted() {
        return completed;
    }

    public void complete() {
        ChatUtils.sendMessage("You completed the " + getFrontEndName() + " quest!");
        completed = true;
        lastCompleted = System.currentTimeMillis();
        Sk1erPublicMod.getInstance().getDataSaving().applyQuestStatus(backendName, 1);

    }

    private boolean completed = false;

    public boolean isDaily() {
        return type.equals(QuestType.DAILY);
    }

    public String getFrontEndName() {
        return quest_data.getString("displayName");
    }

    public HypixelQuest(String backend) {
        this.backendName = backend;
        JSONObject quests = Sk1erPublicMod.getInstance().getApiHandler().getQUEST_INFO();
        quest_data = quests.getJSONObject(backend);
        int status = Sk1erPublicMod.getInstance().getDataSaving().getQuestStatus(backend);
        completed = status == 1;
        type = QuestType.valueOf(quest_data.getString("type"));
        gameType = GameType.fromDatabase(quest_data.getString("gameType"));

    }

    public boolean isActive() {
        JSONObject player = Sk1erPublicMod.getInstance().getApiHandler().getUser();
        if (player.getJSONObject("player").has("quests")) {
            JSONObject quests = player.getJSONObject("player").getJSONObject("quests");
            if (quests.has(backendName)) {
                JSONObject questInPlayer = quests.getJSONObject(backendName);
                if (questInPlayer.has("active")) {
                    long started = questInPlayer.getLong("started");
                    return Sk1erDateUtil.isToday(started);
                }
            }
        }
        return false;
    }

    public QuestType getType() {
        return type;
    }

    public GameType getGameType() {
        return gameType;
    }
}
