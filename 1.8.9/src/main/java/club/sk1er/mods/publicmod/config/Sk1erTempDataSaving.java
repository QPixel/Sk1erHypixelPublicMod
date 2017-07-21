package club.sk1er.mods.publicmod.config;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitchellkatz on 12/2/16.
 */
public class Sk1erTempDataSaving {

    String lastDate = date();
    private File dir;

    public Sk1erTempDataSaving() {
        dir = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "/sk1ermod");
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            File f = new File(dir.getAbsolutePath() + "/" + date() + ".txt");
            if (f.exists()) {

            }

        }

    }

    public File getDir() {
        return dir;
    }

    private String osc() {
        return "Week-" + weeklyOsc();
    }

    public String getLastDate() {
        return lastDate;
    }

    public String dateMM() {
        return new SimpleDateFormat("MM-YY").format(new Date(System.currentTimeMillis()));
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
