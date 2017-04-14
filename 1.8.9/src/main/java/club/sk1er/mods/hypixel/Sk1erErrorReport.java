package club.sk1er.mods.hypixel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 1/30/17.
 */
public class Sk1erErrorReport {
    private int id;
    private List<String> stackTrace = new ArrayList<>();
    private List<String> details = new ArrayList<String>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }

    public void setLine(int line, String value) {
        if (line < details.size())
            details.set(line + 1, value);
    }

    public List<String> getDetails() {
        return details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public void addLine(String name) {
        details.add(name);
    }


}
