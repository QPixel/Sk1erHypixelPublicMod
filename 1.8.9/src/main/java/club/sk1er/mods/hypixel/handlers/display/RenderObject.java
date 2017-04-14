package club.sk1er.mods.hypixel.handlers.display;

/**
 * Created by mitchellkatz on 1/25/17.
 */
public class RenderObject {
    String text = "";
    int x = 0;
    int y = 0;

    public RenderObject(String tex, int x, int y) {
        this.text = tex;
        this.x = x / 2;
        this.y = y;
    }
}
