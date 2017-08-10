package club.sk1er.mods.publicmod.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by mitchellkatz on 12/2/16.
 */
public class DataSaving {

    private HashMap<String, Integer> dailyInt = new HashMap<>();

    public DataSaving() {
        JsonObject daily = new JsonParser().parse(readRawFile(new File(DataSaveType.DAILY.getPath() + date() + "-int.txt"))).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entries = daily.entrySet();
        for (Map.Entry<String, JsonElement> entry : entries) {
            try {
                dailyInt.put(entry.getKey(), entry.getValue().getAsInt());
            } catch (Exception ignored) {
            }
        }


    }

    public void saveData() {
        JsonObject object = new JsonObject();
        for (String key : dailyInt.keySet()) {
            object.addProperty(key, dailyInt.get(key));
        }
        save(new File(DataSaveType.DAILY.getPath() + date() + "-int.txt"), object.toString());
    }

    public void incrimentDailyInt(String key, int value) {
        dailyInt.put(key, dailyInt.get(key) + value);
    }

    public int getDailyInt(String key) {
        return dailyInt.get(key);
    }

    public void save(File f, String cont) {
        try {
            if (!f.exists())
                f.createNewFile();
            FileWriter fw = new FileWriter(f);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(cont);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String readRawFile(File file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
                builder.append(line);
            br.close();
            fr.close();
            return builder.toString();
        } catch (Exception e) {

        }
        return "{}";
    }


    private String osc() {
        return "Week-" + weeklyOsc();
    }


    public String date() {
        return new SimpleDateFormat("dd-MM-yy").format(new Date(System.currentTimeMillis()));
    }

    public String weeklyOsc() {
        long delta = Math.abs(System.currentTimeMillis() - 1417237200000L);
        long oscillation = delta / 604800000L;

        return oscillation % 2 == 0 ? "a" : "b";
    }

}
