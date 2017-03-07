package club.sk1er.mods.hypixel.handlers.server;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.listeners.Sk1erListener;
import club.sk1er.mods.hypixel.utils.ChatUtils;
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
                        Thread.sleep(100);
                        if (Minecraft.getMinecraft().thePlayer.getName() != null) {
                            break;
                        }
                    } catch (Exception e) {
                    }
                }
                getMod().getApiHandler().genKey();
                getMod().getApiHandler().refreshWatchogAndLiveCoins();
                getMod().getApiHandler().pullPlayerProfile();
                getMod().getApiHandler().pullGuild();
                getMod().getApiHandler().genQuests();
                getMod().startHypixelScripts();
                if (getMod().ALLOW_AUTO_GG)
                    ChatUtils.sendMessage(C.GREEN + "AutoGG is currently " + C.BOLD + "allowed." + C.GREEN + " Approved by Plancke. If confronted by staff direct them to Sk1er or Plancke");
                else
                    ChatUtils.sendMessage(C.RED + "AutoGG has been remotely " + C.BOLD + " disabled" + C.RED + " all functionality is now off.");
            } catch (Exception e) {
                getMod().newError(e);
            }
        });
    }

    public void handleLogOffHypixel() {
        getMod().isHypixel = false;
    }
}
