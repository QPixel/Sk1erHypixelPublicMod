package club.sk1er.mods.publicmod.handlers.quest;

/**
 * Created by mitchellkatz on 2/10/17.
 */
public enum QuestType {
    DAILY("DAILY"),
    WEEKLY("WEEKLY");

    private String typeName;

    QuestType(String typeName) {
        this.typeName = typeName;
    }
}
