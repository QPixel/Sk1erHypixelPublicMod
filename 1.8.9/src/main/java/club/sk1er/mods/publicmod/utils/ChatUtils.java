package club.sk1er.mods.publicmod.utils;

import club.sk1er.mods.publicmod.C;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;


/**
 * Created by Mitchell Katz on 11/29/2016.
 */
public class ChatUtils {
    private final static String prefix = C.BLACK + "[" + C.GREEN + "S" + C.GOLD + "k" + C.RED + "1" + C.AQUA + "e" + C.GREEN + "r" + C.BLACK + "] " + C.WHITE + ": ";

    public static void sendRawMessage(IChatComponent comp) {
        try {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(comp);
        } catch (Exception e) {

        }
    }

    public static void sendMessage(String message) {
        sendRawMessage(new ChatComponentText(prefix + message));
    }

    public static void sendMesssageToServer(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    public static void sendDebug(String s) {
//        if (CommandDebug.chatOn) {
        Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(C.AQUA + "[DEBUG]" + C.WHITE + s));
//        }
    }
}
