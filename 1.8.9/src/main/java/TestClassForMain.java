import org.json.JSONObject;

/**
 * Created by Mitchell Katz on 2/21/2017.
 */
public class TestClassForMain {
    public static void main(String[] args) {
        JSONObject tmp = new JSONObject();
        tmp.put("boosters_live", 5);
        tmp.put("boosters_check", 60);
        tmp.put("watchdog_players",15);
        tmp.put("player_profile",60);
        tmp.put("coin",60);
        tmp.put("guild",300);
        System.out.println(tmp.toString());
    }
}
