package com.syteck.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.syteck.prison.Messages;
import com.syteck.prison.utils.PlayerUtil;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(label.equalsIgnoreCase("rank")) {

			if(!sender.hasPermission("prison.admin")) {

				sender.sendMessage(Messages.get("NO_PERMISSION"));

			} else {

				if(args.length == 0) {

					sender.sendMessage(Messages.get("RANK_HELP"));

				} else {

					if(args[0].equalsIgnoreCase("setrank")) {

						if(args.length < 3) {

							sender.sendMessage(Messages.get("RANK_SETRANK_HELP"));

						} else {

							new Commandrank("setrank", sender, args);

						}

					} else if(args[0].equalsIgnoreCase("whois")) {

						if(args.length < 2) {

							sender.sendMessage(Messages.get("RANK_WHOIS_HELP"));

						} else {

							new Commandrank("whois", sender, args);

						}

					}
				}
			}

		} else if(label.equalsIgnoreCase("rankup")) {

			if(!PlayerUtil.isPlayer(sender)) {

				sender.sendMessage(Messages.get("NOT_PLAYER"));

			} else {

				new Commandrankup(PlayerUtil.playerOfSender(sender));

			}
		}

		return true;
	}

}
