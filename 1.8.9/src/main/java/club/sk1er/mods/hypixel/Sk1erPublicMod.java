package club.sk1er.mods.hypixel;

import club.sk1er.html.Utils;
import club.sk1er.mods.hypixel.commands.*;
import club.sk1er.mods.hypixel.config.Sk1erConfig;
import club.sk1er.mods.hypixel.config.Sk1erTempDataSaving;
import club.sk1er.mods.hypixel.handlers.Handlers;
import club.sk1er.mods.hypixel.handlers.api.Sk1erApiHandler;
import club.sk1er.mods.hypixel.handlers.display.Sk1erRenderEvent;
import club.sk1er.mods.hypixel.handlers.input.KeyInput;
import club.sk1er.mods.hypixel.handlers.input.MouseInput;
import club.sk1er.mods.hypixel.handlers.quest.HypixelQuest;
import club.sk1er.mods.hypixel.handlers.scrips.HypixelMinuteScrips;
import club.sk1er.mods.hypixel.handlers.server.Sk1erPlayerLogIntoServerEvent;
import club.sk1er.mods.hypixel.listeners.ChatListener;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.hypixel.api.GameType;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Mod(modid = Sk1erPublicMod.MODID, version = Sk1erPublicMod.VERSION)
public class Sk1erPublicMod {

    /*
    ####Hello####
        Any code used in this mod may be decompiled and inspected for EDUCATIONAL PURPOSES ONLY.
        Any attempt to recreate the circumstances by which this mod can access the api will lead to full blacklisted from all services I publish including autotip, sk1er.club and this mod.
        Your UUID and all other accounts associated with it will be at full blacklist.
        If you have any questions feed free to send me a Dm on twitter (@Sk1er_) Or email me sk1er@lythrim.net

     */
    //Since I am a non at coding:
    //Examples of ingame things
    //xd
         /*
         Quest: http://i.imgur.com/tGIXJAX.png
          */
    public static final String MODID = "Sk1er-Public";
    public static final String VERSION = "1.0-Beta1";
    public static final String NAME = "Sk1er Public Mod";
    public boolean ALLOW_AUTO_GG = false;
    private Handlers handlers;
    public boolean isMovingCustomDisplay = false;
    private Sk1erApiHandler apiHandler;
    public boolean isHypixel = false;
    private Sk1erConfig config;
    private GameType currentGameType;
    public HashMap<String, Sk1erErrorReport> getErrors() {
        return errors;
    }
    private HashMap<String, Sk1erErrorReport> errors;
    public static Sk1erPublicMod getInstance() {
        return instance;
    }
    private static Sk1erPublicMod instance;
    public Sk1erTempDataSaving getDataSaving() {
        return dataSaving;
    }
    private Sk1erTempDataSaving dataSaving;
    private HypixelMinuteScrips scrips;
    private String currentGame = "";

    public String getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(String currentGame) {
        this.currentGame = currentGame;
    }


    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        System.out.println("Starting Sk1er Public Mod V " + VERSION);
        this.instance = this;
        regesterEvents();
        setupConfig(event.getSuggestedConfigurationFile());
        getInfo();
        registerHanders();
        registerItems();
        errors = new HashMap<>();
        System.out.println("Finished Sk1er Public Mod V " + VERSION);
    }

    public void newError(Sk1erErrorReport report) {
        if (Minecraft.getMinecraft().thePlayer.getName().equals("Sk1er")) {
            for (String s : report.getStackTrace()) {
                ChatUtils.sendMessage(s);
            }
        }
        for (String tmp : errors.keySet()) {
            if (errors.get(tmp).getStackTrace().equals(report.getStackTrace())) {
                return;
            }
        }
        int L = errors.size();
        L++;
        errors.put(L + "", report);
        ChatUtils.sendMessage("An error occurred. Please type /errorreport " + L + " to help report it");
    }

    public void newError(Exception e) {
        e.printStackTrace();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(baos);
        e.printStackTrace(stream);
        Sk1erErrorReport report = new Sk1erErrorReport();
        List<String> trace = new ArrayList<String>();
        for (String tmp : baos.toString().split("\n")) {
            trace.add(tmp);
        }
        report.setStackTrace(trace);
        newError(report);
    }

    private void registerItems() {
    }

    public Handlers getHandlers() {
        return handlers;
    }

    public void setupConfig(File file) {
        config = new Sk1erConfig(file);
        config.init();
    }

    public void registerHanders() {
        handlers = new Handlers(this);
        apiHandler = new Sk1erApiHandler(this);
        dataSaving = new Sk1erTempDataSaving();
        Utils.init();
        Multithreading.runAsync(() -> {
            getApiHandler().genQuests();
            JSONObject quests = getApiHandler().getQUEST_INFO();
            for (String s : quests.getNames()) {
                HypixelQuest.fromBackend(s);
            }
            for(HypixelQuest quest : HypixelQuest.getQuestForGame(GameType.GINGERBREAD)) {
                System.out.println(quest.toString());
            }

        });
            }

    public Sk1erApiHandler getApiHandler() {
        return apiHandler;
    }

    public void getInfo() {
    }

    public void regesterEvents() {
        MinecraftForge.EVENT_BUS.register(new ChatListener(this));
        MinecraftForge.EVENT_BUS.register(new MouseInput(this));
        MinecraftForge.EVENT_BUS.register(new Sk1erRenderEvent(this));
        MinecraftForge.EVENT_BUS.register(new Sk1erPlayerLogIntoServerEvent(this));
        MinecraftForge.EVENT_BUS.register(new KeyInput());
        ClientCommandHandler.instance.registerCommand(new CommandSetConfigValue(this));
        ClientCommandHandler.instance.registerCommand(new CommandGuildChat(this));
        ClientCommandHandler.instance.registerCommand(new CommandStats(this));
        ClientCommandHandler.instance.registerCommand(new CommandErrorReport(this));
        ClientCommandHandler.instance.registerCommand(new CommandGuild(this));
        ClientCommandHandler.instance.registerCommand(new CommandFriends(this));
        ClientCommandHandler.instance.registerCommand(new CommandToday(this));
        ClientCommandHandler.instance.registerCommand(new CommandWhatRank(this));
        ClientCommandHandler.instance.registerCommand(new CommandPartyChat(this));
        ClientCommandHandler.instance.registerCommand(new CommandDebug(this));
    }

    public Sk1erConfig getConfig() {
        return config;
    }

    public void startHypixelScripts() {
        Multithreading.runAsync(() -> {
            if (scrips != null) {
                scrips.stop();
            }
            scrips = new HypixelMinuteScrips(this);
            scrips.run();

        });
    }

    public GameType getCurrentGameType() {
        return currentGameType;
    }

    public void setCurrentGameType(GameType currentGameType) {
        this.currentGameType = currentGameType;
    }
}
