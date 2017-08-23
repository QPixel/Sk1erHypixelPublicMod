package club.sk1er.mods.publicmod.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 7/1/17.
 */
public class PublicModConfig {

    private JsonObject config = new JsonObject();
    private List<Object> configObjects = new ArrayList<>();
    private File file;

    public PublicModConfig(File configFile) {
        this.file = configFile;
        try {
            if (configFile.exists()) {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null)
                    builder.append(line);

                String done = builder.toString();
                config = new JsonParser().parse(done).getAsJsonObject();
            } else {
                config = new JsonObject();
                saveFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(config.toString());
            bw.close();
            fw.close();
        } catch (Exception e) {
        }
    }

    public void save() {
        for (Object o : configObjects)
            saveToJsonFromRamObject(o);
        saveFile();
    }

    public void register(Object object) {
        configObjects.add(object);
        loadToClass(object);
    }

    public void loadToClass(Object o) {
        try {
            loadToClassObject(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void loadToClassObject(Object object) throws IllegalAccessException {
        Class<?> aClass = object.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(ConfigOpt.class)) {
                if (config.has(aClass.getName())) {
                    JsonObject tmp = config.get(aClass.getName()).getAsJsonObject();
                    if (tmp.has(field.getName())) {
                        JsonElement jsonElement = tmp.get(field.getName());
                        if (field.getType().isAssignableFrom(int.class)) {
                            field.set(object, jsonElement.getAsInt());
                        } else if (field.getType().isAssignableFrom(String.class)) {
                            field.set(object, jsonElement.getAsString());
                        } else if (field.getType().isAssignableFrom(boolean.class)) {
                            field.set(object, jsonElement.getAsBoolean());
                        } else if (field.getType().isAssignableFrom(double.class)) {
                            field.set(object, jsonElement.getAsDouble());
                        }
                    }
                }
            }
        }
    }

    public void saveToJsonFromRamObject(Object o) {
        try {
            loadToJson(o);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void loadToJson(Object object) throws IllegalAccessException {
        Class<?> aClass = object.getClass();
        for (Field field : aClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(ConfigOpt.class) != null) {
                if (!config.has(aClass.getName())) {
                    config.add(aClass.getName(), new JsonObject());
                }
                JsonObject classObject = config.get(aClass.getName()).getAsJsonObject();
                if (field.getType().isAssignableFrom(int.class)) {
                    classObject.addProperty(field.getName(), field.getInt(object));
                } else if (field.getType().isAssignableFrom(String.class)) {
                    config.addProperty(field.getName(), (String) field.get(object));
                } else if (field.getType().isAssignableFrom(boolean.class)) {
                    config.addProperty(field.getName(), field.getBoolean(object));
                } else if (field.getType().isAssignableFrom(double.class)) {
                    config.addProperty(field.getName(), field.getDouble(object));
                }
            }
        }
    }

    public JsonObject getConfig() {
        return config;
    }
}
