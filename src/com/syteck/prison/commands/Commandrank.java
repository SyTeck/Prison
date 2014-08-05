package com.syteck.prison.commands;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import com.syteck.prison.Messages;
import com.syteck.prison.Prison;
import com.syteck.prison.Rank;
import com.syteck.prison.Storage;
import com.syteck.prison.User;
import com.syteck.prison.api.VaultApi;
import com.syteck.prison.utils.NumberUtil;
import com.syteck.prison.utils.UUIDUtil;

public class Commandrank {

	public Commandrank(String call, CommandSender sender, String[] args) {

		String stringId = UUIDUtil.getStringIdFromPlayerName(args[1]);
		if(stringId.isEmpty() || stringId == null) { sender.sendMessage(Messages.get("RANK_PLAYERNOTFOUND")); return; }

		if(call.equalsIgnoreCase("setrank")) {

			if(!NumberUtil.canParse(args[2])) {

				sender.sendMessage(Messages.get("RANK_INVALIDRANKID"));

			} else {

				int rankId = Integer.parseInt(args[2]);
				Rank rank = Storage.getRank(rankId);

				if(rankId > Storage.lowestRank || rankId < 1) {

					sender.sendMessage(Messages.get("RANK_INVALIDRANKID"));

				} else {

					sender.sendMessage(Messages.get("LOADING"));

					if(Storage.getUser(stringId) != null) {

						Storage.getUser(stringId).setRank(rankId);
						try { Storage.saveUser(stringId); } catch (IOException e) { e.printStackTrace(); }

						sender.sendMessage(Messages.get("RANK_SETRANK_SUCCESS").replaceAll("<NAME>", args[1]).replaceAll("<RANK>", rank.getPrefix()).replaceAll("<RANKID>", ""+rankId));

					} else if(Storage.getUserFromStore(stringId) != null) {

						File userFile = new File(Prison.userPath, stringId+".yml");
						YamlConfiguration userYaml = YamlConfiguration.loadConfiguration(userFile);

						userYaml.set("rankid", rankId);
						try { Storage.saveUser(stringId); } catch (IOException e) { e.printStackTrace(); }

						sender.sendMessage(Messages.get("RANK_SETRANK_SUCCESS").replaceAll("<NAME>", args[1]).replaceAll("<RANK>", rank.getPrefix()).replaceAll("<RANKID>", ""+rankId));

					} else {

						sender.sendMessage(Messages.get("RANK_PLAYERNOTFOUND"));

					}
				}
			}

		} else if(call.equalsIgnoreCase("whois")) {

			sender.sendMessage(Messages.get("LOADING"));

			if(Storage.getUser(stringId) != null) {

				String name = UUIDUtil.getNameFromUUID(stringId);
				User user = Storage.getUser(stringId);
				Rank rank = user.getRank();
				double money = VaultApi.getMoney(name);

				sender.sendMessage(Messages.get("RANK_WHOIS_NAME").replaceAll("<NAME>", name).replaceAll("<UUID>", stringId));
				sender.sendMessage(Messages.get("RANK_WHOIS_RANK").replaceAll("<RANK>", rank.getPrefix()).replaceAll("<RANKID>", ""+rank.getRankId()));
				sender.sendMessage(Messages.get("RANK_WHOIS_MONEY").replaceAll("<MONEY>", ""+money));

			} else if(Storage.getUserFromStore(stringId) != null) {

				String name = UUIDUtil.getNameFromUUID(stringId);
				User user = Storage.getUserFromStore(stringId);
				Rank rank = user.getRank();
				double money = VaultApi.getMoney(name);

				sender.sendMessage(Messages.get("RANK_WHOIS_NAME").replaceAll("<NAME>", name).replaceAll("<UUID>", stringId));
				sender.sendMessage(Messages.get("RANK_WHOIS_RANK").replaceAll("<RANK>", rank.getPrefix()).replaceAll("<RANKID>", ""+rank.getRankId()));
				sender.sendMessage(Messages.get("RANK_WHOIS_MONEY").replaceAll("<MONEY>", ""+money));

			} else {

				sender.sendMessage(Messages.get("RANK_PLAYERNOTFOUND"));

			}
		}
	}
}