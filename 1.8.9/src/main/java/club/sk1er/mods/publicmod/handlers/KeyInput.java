package club.sk1er.mods.publicmod.handlers;

import club.sk1er.mods.publicmod.Sk1erPublicMod;
import club.sk1er.mods.publicmod.utils.ChatUtils;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

/**
 * Created by mitchellkatz on 7/20/17.
 */
public class KeyInput {

    private Sk1erPublicMod mod;

    public KeyInput(Sk1erPublicMod mod) {
        this.mod = mod;
    }

    @SubscribeEvent
    public void onKeyPress(InputEvent.KeyInputEvent ignored) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LMENU)) {
            //Modifier key down
            if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
                if (System.currentTimeMillis() - mod.getChatHandler().getRecentPartyTime() < 1000 * 60) {
                    ChatUtils.sendMesssageToServer("/p accept " + mod.getChatHandler().getRecentPartyInvite());
                    mod.getChatHandler().setRecentPartyTime(0L);
                }
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
                if (System.currentTimeMillis() - mod.getChatHandler().getRecentFriendTime() < 1000 * 60) {
                    ChatUtils.sendMesssageToServer("/f accept " + mod.getChatHandler().getRecentFriend());
                    mod.getChatHandler().setRecentFriendTime(0L);
                }
            }
        }
    }
}
