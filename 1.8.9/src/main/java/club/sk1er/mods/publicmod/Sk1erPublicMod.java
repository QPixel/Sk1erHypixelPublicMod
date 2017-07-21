package club.sk1er.mods.publicmod;

import club.sk1er.mods.publicmod.config.Sk1erTempDataSaving;
import club.sk1er.mods.publicmod.handlers.KeyInput;
import club.sk1er.mods.publicmod.handlers.api.Sk1erApiHandler;
import club.sk1er.mods.publicmod.handlers.chat.Sk1erChatHandler;
import net.hypixel.api.GameType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Sk1erPublicMod.MODID, version = Sk1erPublicMod.VERSION, name = Sk1erPublicMod.NAME)
public class Sk1erPublicMod {


    /*
    Quest: http://i.imgur.com/tGIXJAX.png
     */
    public static final String MODID = "Sk1er-Public";
    public static final String VERSION = "1.0";
    public static final String NAME = "Sk1er Public Mod";
    private static Sk1erPublicMod instance;
    private Sk1erTempDataSaving dataSaving;
    private Sk1erMod sk1erMod;
    private Sk1erChatHandler chatHandler;
    private String uuid;
    private String name;
    private GameType currentGame = GameType.UNKNOWN;
    private Sk1erApiHandler apiHandler;
    private KeyInput keyInput;

    public static Sk1erPublicMod getInstance() {
        return instance;
    }

    public Sk1erChatHandler getChatHandler() {
        return chatHandler;
    }

    public Sk1erApiHandler getApiHandler() {
        return apiHandler;
    }

    public String getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return name;
    }

    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        instance = this;
        sk1erMod = new Sk1erMod(MODID, VERSION, NAME);
        sk1erMod.checkStatus();
        name = Minecraft.getMinecraft().getSession().getProfile().getName();
        uuid = Minecraft.getMinecraft().getSession().getPlayerID().replace("-", "");
        apiHandler = new Sk1erApiHandler(sk1erMod, this);
        chatHandler = new Sk1erChatHandler(this);

        keyInput = new KeyInput(this);
        //'Register Events
        MinecraftForge.EVENT_BUS.register(chatHandler);
        MinecraftForge.EVENT_BUS.register(keyInput);
    }

    public void isHypixel() {

    }


    public GameType getCurrentGame() {
        return currentGame;
    }
}
