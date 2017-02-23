package club.sk1er.mods.hypixel.handlers.chat;

import club.sk1er.mods.hypixel.C;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

import java.lang.reflect.Array;

/**
 * Created by mitchellkatz on 12/7/16.
 */
public class FriendsChatHandler extends Sk1erChatHandler{


    private long time = 0l;
    @Override
    public void handle(ClientChatReceivedEvent e) {
        String wrk = e.message.getUnformattedText();
        if ((System.currentTimeMillis() - time) < 2000) {
            if ((wrk.contains("is in a") && (wrk.endsWith("game") || wrk.endsWith("Lobby"))) || wrk.endsWith("is idle in Limbo") || wrk.endsWith("is in an unknown realm") || wrk.endsWith("is in Housing") || wrk.endsWith("the Main Lobby")) {
                String[] tmp = wrk.split(" ");
                String playername = tmp[0];
                tmp.toString().startsWith("helo");
                String[] t = e.message.getFormattedText().split(" ");
                IChatComponent newMessage = new ChatComponentText(t[0]);
                if (wrk.contains("Mega Walls")) {
                    t[4] = "MW";
                }
                if (wrk.contains("Crazy Walls")) {
                    t[4] = "CW";
                }
                if (wrk.contains("Cops")) {
                    t[4] = "CVC";
                }
                if (wrk.contains("game")) {
                    newMessage.appendSibling(new ChatComponentText(" : " + C.YELLOW + t[4] + " game"));
                }
                if (wrk.contains("Lobby")) {
                    newMessage.appendSibling(new ChatComponentText(" : " + C.YELLOW + t[4] + " lobby"));
                }
                if (wrk.contains("Limbo")) {
                    newMessage.appendSibling(new ChatComponentText(" : " + C.YELLOW + "Limbo"));
                }
                if (wrk.contains("Housing")) {
                    newMessage.appendSibling(new ChatComponentText(" : " + C.YELLOW + "Housing"));
                }
                if (wrk.contains("unknown")) {
                    newMessage.appendSibling(new ChatComponentText(" : " + C.YELLOW + "Unknown"));
                }
                newMessage.appendSibling(message(playername));
                newMessage.appendSibling(party(playername));
                newMessage.appendSibling(remove(playername));

                e.setCanceled(true);
               ChatUtils.sendRawMessage(newMessage);


            } else if (wrk.endsWith("is currently offline")) {
                String[] tmp = wrk.split(" ");
                String playername = tmp[0];
                e.message.appendSibling(remove(playername));
            }
        }
    }
    @Override
    public boolean containsTrigger(ClientChatReceivedEvent event) {
        String wrk = event.message.getUnformattedText();
        if(System.currentTimeMillis() - time < 2000) {
            return true;
        }
        if(wrk.contains("Friends (Page")) {
            time = System.currentTimeMillis();
            return true;
        }
        return false;

    }
    public  IChatComponent message(String playername) {

        IChatComponent comp = new ChatComponentText(EnumChatFormatting.GREEN + " [Message] ");
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/msg " + playername) {
            @Override
            public Action getAction() {

                return Action.RUN_COMMAND;
            }
        });
        comp.setChatStyle(style);
        return comp;
    }
    public  IChatComponent party(String playername) {

        IChatComponent comp = new ChatComponentText(EnumChatFormatting.AQUA + "[Party]");
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party " + playername) {
            @Override
            public Action getAction() {
                return Action.RUN_COMMAND;
            }
        });
        comp.setChatStyle(style);
        return comp;
    }
    public  IChatComponent remove(String playername) {

        IChatComponent comp = new ChatComponentText(EnumChatFormatting.RED + " [Remove]");
        ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend remove " + playername) {
            @Override
            public Action getAction() {
                return Action.SUGGEST_COMMAND;
            }
        });
        comp.setChatStyle(style);
        return comp;
    }
    public FriendsChatHandler(Sk1erPublicMod mod) {
            super(mod);
        }

}
