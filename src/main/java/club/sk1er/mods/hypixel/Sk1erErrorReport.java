package club.sk1er.mods.hypixel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mitchellkatz on 1/30/17.
 */
public class Sk1erErrorReport {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    public List<String> getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(List<String> stackTrace) {
        this.stackTrace = stackTrace;
    }

    private List<String> stackTrace = new ArrayList<>();

    public void setDetails(List<String> details) {
        this.details = details;
    }
    public void setLine(int line, String value) {
        if(line<details.size())
        details.set(line+1,value);
    }

    public List<String> getDetails() {
        return details;
    }

    private List<String> details = new ArrayList<String>();
    public void addLine(String name){
        details.add(name);
    }


}
