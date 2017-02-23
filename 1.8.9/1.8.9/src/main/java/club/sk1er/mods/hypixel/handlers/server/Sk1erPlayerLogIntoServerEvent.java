package club.sk1er.mods.hypixel.handlers.server;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.listeners.Sk1erListener;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;


/**
 * Created by mitchellkatz on 12/1/16.
 */
public class Sk1erPlayerLogIntoServerEvent extends Sk1erListener {

    public Sk1erPlayerLogIntoServerEvent(Sk1erPublicMod sk1er) {
        super(sk1er);
    }

    @SubscribeEvent
    public void onPlayerLogIntoServerEvent(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (!FMLClientHandler.instance().getClient().isSingleplayer())
            if (FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains(".hypixel.net") || FMLClientHandler.instance().getClient().getCurrentServerData().serverName.equalsIgnoreCase("HYPIXEL")) {
                handleHypixelLogin();
            }
    }

    @SubscribeEvent
    public void onPlayerLogOutEvent(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        handleLogOffHypixel();
    }

    public void handleHypixelLogin() {
        getMod().isHypixel = true;

        Multithreading.runAsync(() -> {

            try {
                while (true) {
                    try {
                        if (Minecraft.getMinecraft().thePlayer.getName() != null) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                getMod().startHypixelScripts();
                //ChatUtils.sendMessage("Generating Sk1er Api Key");
                getMod().getApiHandler().genKey();
                //ChatUtils.sendMessage("Pulling Watchdog");
                getMod().getApiHandler().refreshWatchogAndLiveCoins();
                //ChatUtils.sendMessage("Pulling Player");
                getMod().getApiHandler().pullPlayerProfile();
                //ChatUtils.sendMessage("Pulling guild");
                getMod().getApiHandler().pullGuild();
                //ChatUtils.sendMessage("Starting scripts");
                //ChatUtils.sendMessage("Done Starting scripts");
                getMod().getApiHandler().genQuests();
            } catch (Exception e) {
                getMod().newError(e);
            }


        });
    }

    public void handleLogOffHypixel() {
        getMod().isHypixel = false;
    }
}
