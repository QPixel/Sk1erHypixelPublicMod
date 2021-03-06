package net.hypixel.api;


public enum GameType {
    QUAKECRAFT("Quakecraft", "Quake", 2),
    WALLS("Walls", "Walls", 3),
    PAINTBALL("Paintball", "Paintball", 4),
    SURVIVAL_GAMES("Blitz Survival Games", "HungerGames", 5),
    TNTGAMES("The TNT Games", "TNTGames", 6),
    VAMPIREZ("VampireZ", "VampireZ", 7),
    WALLS3("Mega Walls", "Walls3", 13),
    ARCADE("Arcade", "Arcade", 14),
    ARENA("Arena Brawl", "Arena", 17),
    MCGO("Cops and Crims", "MCGO", 21),
    UHC("UHC Champions", "UHC", 20),
    BATTLEGROUND("Warlords", "Battleground", 23),
    SUPER_SMASH("Smash Heroes", "SuperSmash", 24),
    GINGERBREAD("Turbo Kart Racers", "GingerBread", 99999),
    TURBO_KART_RACERS("Turbo Kart Racers", "GingerBread", 25),
    SKYWARS("SkyWars", "SkyWars", 51),
    TRUE_COMBAT("Crazy Walls", "TrueCombat", 52),
    SKYCLASH("SkyClash", "SkyClash", 55),
    LEGACY("Classic Games", "Legacy", 56),
    SPEED_UHC("Speed UHC", "SpeedUHC", 54),
    BEDWARS("Bed Wars", "Bedwars", 57),
    UNKNOWN("Unknown", "ERROR", -1);

    private static final GameType[] v = values();

    private final String name;
    private final String dbName;
    private final Integer id;

    GameType(String name, String dbName, Integer id) {
        this.name = name;
        this.dbName = dbName;
        this.id = id;

    }

    /**
     * @param id The internal id
     * @return The GameType associated with that id, or null if there isn't one.
     */
    public static GameType fromId(int id) {
        for (GameType gameType : v) {
            if (gameType.id == id) {
                return gameType;
            }
        }
        return null;
    }

    public static GameType fromRealName(String id) {
        for (GameType gameType : v) {
            if (gameType.name.equalsIgnoreCase(id)) {
                return gameType;
            }
        }
        return null;
    }

    /**
     * @param dbName The key used in the database
     * @return The GameType associated with that key, or null if there isn't one.
     */
    public static GameType fromDatabase(String dbName) {
        for (GameType gameType : v) {
            if (gameType.dbName.equals(dbName)) {
                return gameType;
            }
        }
        return GameType.UNKNOWN;
    }

    public static GameType parse(String mostRecentGameType) {
        try {
            return valueOf(mostRecentGameType);
        } catch (Exception e) {

            GameType type = fromDatabase(mostRecentGameType);
            if (type != null)
                return type;
            type = fromRealName(mostRecentGameType);
            return type;
        }
    }

    public String getDbName() {
        return dbName;
    }

    /**
     * @return The official name of the GameType
     */
    public String getName() {
        return name;
    }

    /**
     * @return The internal ID that is occasionally used in various database schemas
     */
    public int getId() {
        return id;
    }
}