import org.json.JSONObject;

import java.util.Scanner;

/**
 * Created by Mitchell Katz on 2/21/2017.
 */
public class TestClassForMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        JSONObject master = new JSONObject();
        while(true) {
            String line = sc.nextLine();
            if(line.equals("end")) {
                break;
               }
             String[] split = line.split(" ");
            if(split.length>1) {
                int leng = split.length;
                String game = split[leng-1];
                String type = split[leng-2];
                String backend = split[leng-3];
                String name ="";
                for(int x = leng-4; x>0;x++) {
                    name+=split[x] + " " + name;
                }
                JSONObject tmp = new JSONObject();
                tmp.put("backend",backend);
                tmp.put("gameType",game);
                tmp.put("type",type);
                tmp.put("displayName",name);
                master.put(backend, tmp);

                /*
                Skywars Solo Win	skywars_solo_win	DAILY	SkyWars
Skywars Solo Kills	skywars_solo_kills	DAILY	SkyWars
Skywars Team Win	skywars_team_win	DAILY	SkyWars
Skywars Team Kills	skywars_team_kills	DAILY	SkyWars
Skywars Weekly Kills	skywars_weekly_kills	WEEKLY	SkyWars
                 */
            }


        }
        System.out.println(master.toString());
    }
}
