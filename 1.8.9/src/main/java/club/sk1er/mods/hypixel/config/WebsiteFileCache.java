package club.sk1er.mods.hypixel.config;

import club.sk1er.mods.hypixel.Sk1erPublicMod;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;

/**
 * Created by mitchellkatz on 4/13/17.
 */
public class WebsiteFileCache {

    private HashMap<String, JSONObject> cache = new HashMap<>();
    private File dir;
    private Sk1erPublicMod mod;

    public WebsiteFileCache(Sk1erPublicMod mod) {
        this.mod = mod;
        dir = new File(mod.getDataSaving().getDir().getPath() + "/cache");
        if (!dir.exists())
            dir.mkdirs();
        refresh();
    }

    public boolean hasCache(String key) {
        return cache.containsKey(key);
    }

    public void clear() {
        cache.clear();
        saveCache();
    }

    public void refresh() {
        cache.clear();
        String[] names = dir.list();
        if (names != null) {
            for (String name : names) {
                File tmp = new File(dir + "/" + name);
                try {
                    FileReader fr = new FileReader(tmp);
                    BufferedReader br = new BufferedReader(fr);
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = br.readLine()) != null)
                        builder.append(line);

                    String done = builder.toString();
                    cache.put(name.split("\\.")[0], new JSONObject(done));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public HashMap<String, JSONObject> getCache() {
        return cache;
    }

    public void addItemAndSave(String name, JSONObject object) {
        cache.put(name, object);
        saveCache();
    }

    public void saveCache() {
        for (String names : cache.keySet()) {
            File tmp = new File(dir.getPath() + "/" + names + ".json");
            try {
                FileWriter fw = new FileWriter(tmp);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(cache.get(names).toString());
                bw.close();
                fw.close();
            } catch (Exception e) {
                mod.newError(e);
            }
        }
    }

}
