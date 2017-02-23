package club.sk1er.mods.hypixel.config;

import club.sk1er.mods.hypixel.Multithreading;
import net.minecraft.client.Minecraft;
import org.json.JSONObject;

import java.io.*;
import java.util.EnumMap;

/**
 * Created by Mitchell Katz on 11/28/2016.
 */
public class Sk1erConfig {

    private JSONObject config;
    File configFile;
    private EnumMap<CValue, Object> map;
    public Sk1erConfig(File f) {
        this.configFile=f;
    }
    public void init() {
        map=new EnumMap<>(CValue.class);
        config = new JSONObject();
        if (!configFile.exists()) {
            System.out.println("Generating new config file! - " + configFile.getAbsolutePath());
            for (CValue v : CValue.values()) {
                config.put(v.getName(), v.getDefaultvalue());
            }
            save();
        } else {
            try {
                FileReader fr = new FileReader(configFile);
                BufferedReader br = new BufferedReader(fr);
                config = new JSONObject(br.readLine());
            } catch (IOException e) {
                System.out.println("Broken config!?");
                e.printStackTrace();
            }
        }
        for (CValue v : CValue.values()) {
            if (!config.has(v.getName())) {
                config.put(v.getName(), v.getDefaultvalue());
                for(int i = 0;i<100;i++) {
                    System.out.println("No saved value for: " + v.getName());
                }
            }
            map.put(v, config.get(v.getName()));
        }

    }

    public boolean setValue(String value, Object newValue) {
        if (config.has(value)) {
            CValue value1 = CValue.valueOf(value);
            if (value1.isValidState(newValue.toString())) {
                config.put(value.toUpperCase(), newValue);
                map.put(value1, newValue);
                save();
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }

    }
    public void forceValue(CValue value, Object value1) {
        config.put(value.getName(),value1);
        map.put(value,value1);
        save();
    }
    public boolean getBoolean(CValue value) {
        if(map.get(value) instanceof Boolean)
            return (boolean)map.get(value);
        else {
            forceValue(value, value.getDefaultvalue());
            return (boolean) value.getDefaultvalue();
        }
    }
    public double getDouble(CValue value) {
        return (double)map.get(value);
    }
    public String getString(CValue value) {
        return ((String) map.get(value));
    }
    public void save() {
        Multithreading.runAsync(() -> {
            try {

                configFile.createNewFile();
                FileWriter fw = new FileWriter(configFile);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(config.toString());
                bw.close();
                fw.close();
            } catch (IOException e) {
                System.out.println("Unable to write to config!");
            }
        });

    }

    public JSONObject getConfig() {
        return config;
    }

    public int getInt(CValue val) {
        return ((int) map.get(val));
    }
}
