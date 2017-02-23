package club.sk1er.mods.hypixel.handlers.stats;

import org.json.JSONObject;

/**
 * Created by mitchellkatz on 1/25/17.
 */
public abstract class ApiGameStatsHandler {
    public abstract void handle(JSONObject player);
}
