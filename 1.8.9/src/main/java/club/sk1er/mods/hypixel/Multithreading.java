package club.sk1er.mods.hypixel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mitchellkatz on 11/30/16.
 */
public class Multithreading {

    public static ExecutorService POOL = Executors.newCachedThreadPool();

    public static void runAsync(Runnable runnable) {
        POOL.execute(runnable);


    }


}
