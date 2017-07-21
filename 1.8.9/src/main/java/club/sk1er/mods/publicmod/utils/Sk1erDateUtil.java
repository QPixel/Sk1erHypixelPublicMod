package club.sk1er.mods.publicmod.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mitchellkatz on 2/10/17.
 */
public class Sk1erDateUtil {

    private static SimpleDateFormat dd_mm_yy = new SimpleDateFormat("dd-mm-yy");

    public static boolean isToday(long date) {
        return getStandardNameForToday().equalsIgnoreCase(dd_mm_yy.format(new Date(date)));
    }

    public static String getStandardNameForToday() {
        return dd_mm_yy.format(new Date(System.currentTimeMillis()));
    }

    public static String getTime(String format, long time) {
        return new SimpleDateFormat(format).format(new Date(time));
    }
}