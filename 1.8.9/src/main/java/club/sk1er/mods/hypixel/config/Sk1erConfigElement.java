package club.sk1er.mods.hypixel.config;

/**
 * Created by mitchellkatz on 5/24/17.
 */
public class Sk1erConfigElement<E> {
    private E defaultValue;
    private String category;
    private String name;

    public Sk1erConfigElement(String category, String name, E defaultValue) {
        this.category = category;
        this.name = name;
        this.defaultValue = defaultValue;

    }

    public E getDefaultValue() {
        return defaultValue;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

}
