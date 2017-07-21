package club.sk1er.mods.publicmod.config;

import net.minecraft.client.Minecraft;

import java.io.File;

/**
 * Created by mitchellkatz on 7/20/17.
 */
public enum DataSaveType {

    RUNTIME(null),
    DAILY("/sk1ermod/daily/"),
    WEEKLY("/sk1ermod/weekly/"),
    PERM("/sk1ermod");

    private String mcDataDir = Minecraft.getMinecraft().mcDataDir.getAbsolutePath();
    private String path;

    DataSaveType(String path) {
        this.path = mcDataDir + "/" + path;
        if (path != null)
            new File(this.path).mkdirs();
    }

    public File getPathFile() {
        return new File(path);
    }

    public String getPath() {
        return path;
    }
}
