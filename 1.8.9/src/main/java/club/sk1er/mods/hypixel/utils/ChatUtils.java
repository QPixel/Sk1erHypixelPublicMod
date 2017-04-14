package club.sk1er.mods.hypixel.utils;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.commands.CommandDebug;
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

    public static void sendMessage(String test) {
        sendRawMessage(new ChatComponentText(prefix + test));
    }

    public static void sendMesssageToServer(String message) {
        Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
    }

    public static void sendDebug(String s) {
        if (CommandDebug.chatOn) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(C.AQUA + "[DEBUG]" + C.WHITE + s));
        }
    }
}
