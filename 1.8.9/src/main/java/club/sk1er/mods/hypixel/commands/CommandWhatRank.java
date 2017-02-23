package club.sk1er.mods.hypixel.commands;

import club.sk1er.mods.hypixel.Multithreading;
import club.sk1er.mods.hypixel.Sk1erPublicMod;
import club.sk1er.mods.hypixel.utils.ChatUtils;
import org.json.JSONObject;

import club.sk1er.html.Utils;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;

public class CommandWhatRank extends CommandBase {

	private Sk1erPublicMod mod;
	public CommandWhatRank(Sk1erPublicMod mod) {
		this.mod=mod;
	}
	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) {
		return true;
	}
	@Override
	public String getCommandName() {
		return "whatrank";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		return "/whatrank [username]";
	}

	@Override
	public void processCommand(ICommandSender sender, final String[] args) throws CommandException {
		Multithreading.runAsync(() -> {
			try {
				if (args.length == 0) {
					ChatUtils.sendMessage("/whatrank + [username]");
					return;
				}

				JSONObject returned = new JSONObject(mod.getApiHandler().pullPlayerProfile(args[0]));
				if (returned == null) {
					ChatUtils.sendMessage("Unable to load player: " + args[0] + " API might be down");
					return;
				}
				JSONObject j = null;
				try {
					j = new JSONObject(returned);
				} catch (Exception e) {
					ChatUtils.sendMessage("Player: " + args[0] + " does not exist!");
				}
				if(j.optBoolean("success"))
				ChatUtils.sendMessage("Targeted player: " + Utils.getFormatedName(j));
				else ChatUtils.sendMessage("Player does not exist!");

			} catch (Exception e) {
				mod.newError(e);
			}
		});


		
	}



}
