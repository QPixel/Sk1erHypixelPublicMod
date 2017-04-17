package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erErrorReport;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Created by Mitchell Katz on 12/4/2016.
 */
public class CommandErrorReport extends Sk1erCommand {

    public CommandErrorReport(Sk1erPublicMod mod) {
        super(mod, "errorreport", "/errorreport <id> <action>");
    }

    @Override
    public void processCommand(ICommandSender iCommandSender, String[] a) throws CommandException {
        if (a.length == 0) {
            ChatUtils.sendMessage(getCommandUsage(iCommandSender));
        } else {
            if (a.length == 1) {
                Sk1erErrorReport report = getMod().getErrors().get(a[0]);
                if (report == null) {
                    ChatUtils.sendMessage("That report could not be found!");
                } else {
                    String mes = "/errorreport " + a[0];

                    ChatUtils.sendMessage(mes + " preview - Previews the report");
                    ChatUtils.sendMessage(mes + " adddesc [desciption] - Descibe how the error happened");
                    ChatUtils.sendMessage(mes + " setdesc [line] [value] - Previews the report");
                    ChatUtils.sendMessage(mes + " publish - Publishes the report and possible suggestion from the webserver");
                }
            } else {
                Sk1erErrorReport report = getMod().getErrors().get(a[0]);
                if (a[1].equalsIgnoreCase("preview")) {

                    ChatUtils.sendMessage("Error id: " + a[0]);
                    ChatUtils.sendMessage("How to reproduce / what you were doing");
                    int line = 0;
                    for (String s : report.getDetails()) {
                        line++;
                        ChatUtils.sendMessage(line + ":" + s);

                    }
                    ChatUtils.sendMessage("Ready to publish? /errorreport " + a[0] + " publish");
                } else if (a[1].equalsIgnoreCase("adddesc")) {
                    if (a.length == 2) {
                        ChatUtils.sendMessage("/errorreport " + a[0] + " adddesc [What you were doing / how to recreate this, blank is OK]");
                    } else {
                        String val = "";
                        for (int i = 2; i < a.length; i++) {
                            val += a[i] + " ";
                        }
                        report.addLine(val);
                        ChatUtils.sendMessage("Added '" + val + "' to the desciption of the bug!");
                        ChatUtils.sendMessage("Ready to publish? /errorreport " + a[0] + " publish");
                    }
                } else if (a[1].equalsIgnoreCase("setdesc")) {
                    if (a.length <= 2) {
                        ChatUtils.sendMessage("/errorreport " + a[0] + " setdesc [line] [What you were doing / how to recreate this, blank is OK]");
                    } else {

                        int line = 0;
                        try {
                            line = Integer.valueOf(a[2]);
                        } catch (Exception e) {
                            ChatUtils.sendMessage("Please enter a valid line number! Unable to parse '" + a[2] + "'");
                            return;
                        }
                        String val = "";
                        for (int i = 2; i < a.length; i++) {
                            val += a[i] + " ";
                        }
                        report.setLine(line, val);
                        ChatUtils.sendMessage("set line " + line + " to '" + val + "'");
                        ChatUtils.sendMessage("Ready to publish? /errorreport " + a[0] + " publish");
                    }
                } else if (a[1].equalsIgnoreCase("publish")) {
                    Multithreading.runAsync(() -> {
                        try {
                            String urlParameters = "";
                            urlParameters += "mod=" + Sk1erPublicMod.NAME.replace(" ", "%23");
                            urlParameters += "&version=" + Sk1erPublicMod.VERSION;
                            int sc = 1;
                            for (String meme : report.getStackTrace()) {
                                urlParameters += "&stack_trace_" + sc + "=" + meme.replace(" ", "%23");
                                sc++;
                            }

                            int d = 1;
                            for (String meme : report.getDetails()) {
                                urlParameters += "&user_rep_" + d + "=" + meme.replace(" ", "%20");
                            }

                            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
                            int postDataLength = postData.length;
                            String request = "http://sk1er.club/moderror/" + getMod().getApiHandler().SK1ER_API_KEY;
                            URL url = new URL(request);
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoOutput(true);
                            conn.setInstanceFollowRedirects(false);
                            conn.setRequestMethod("POST");
                            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                            conn.setRequestProperty("charset", "utf-8");
                            conn.setRequestProperty("User-Agent", "Sk1er Public Mod V." + Sk1erPublicMod.VERSION);
                            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
                            conn.setUseCaches(false);
                            try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                                wr.write(postData);
                            }
                            InputStream is = conn.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                            String line = reader.readLine();
                            if (!(line == null)) {
                                StringBuilder builder = new StringBuilder(line);
                                while (line != null) {
                                    line = reader.readLine();
                                    if (line != null) {
                                        builder.append(line);
                                    }
                                }
                                JSONObject b = new JSONObject(builder);
                                if (b.getBoolean("success")) {
                                    if (b.has("error_cause")) {
                                        ChatUtils.sendMessage("The webserver suggests you update your mod! go to http://sk1er.club/publicmod to do so!");
                                    }
                                } else {
                                    ChatUtils.sendMessage("The error returned from the web server was:" + b.getString("cause"));
                                }

                            } else {
                                ChatUtils.sendMessage("Unable to read from webserver!");
                            }
                        } catch (Exception e) {
                            ChatUtils.sendMessage("Published :)");
                        }
                    });


                }

            }

        }
    }
}
