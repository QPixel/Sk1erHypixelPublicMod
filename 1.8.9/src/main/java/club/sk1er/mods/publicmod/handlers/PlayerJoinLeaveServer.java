package club.sk1er.mods.publicmod.handlers;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

/**
 * Created by mitchellkatz on 7/21/17.
 */
public class PlayerJoinLeaveServer {
    private Sk1erPublicMod mod;

    public PlayerJoinLeaveServer(Sk1erPublicMod mod) {
        this.mod = mod;

    }

    @SubscribeEvent
    public void onJoin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        if (!FMLClientHandler.instance().getClient().isSingleplayer())
            if (FMLClientHandler.instance().getClient().getCurrentServerData().serverIP.contains(".publicmod.net") || FMLClientHandler.instance().getClient().getCurrentServerData().serverName.equalsIgnoreCase("HYPIXEL")) {
                mod.joinedHypixel();
            }
    }
}
