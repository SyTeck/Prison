package com.syteck.prison;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.configuration.file.YamlConfiguration;

public class Messages {

	private static HashMap<String, String> messages = new HashMap<String, String>();

	public static void add(String key, String value) { messages.put(key, value); }
	public static String get(String key) { return messages.get(key).replaceAll("&", "§"); }

	public static void loadMessages() throws IOException {

		File messageFile = Prison.messageFile;
		YamlConfiguration messageYaml = YamlConfiguration.loadConfiguration(messageFile);

		if(!messageFile.exists()) {

			messageFile.createNewFile();

			messageYaml.set("NO_PERMISSION", "&cYou do not have permission to do this.");
			messageYaml.set("NOT_PLAYER", "&cYou are not a player.");
			messageYaml.set("RANKUP_LORE_1", "I am");
			messageYaml.set("RANKUP_LORE_2", "just");
			messageYaml.set("RANKUP_LORE_3", "testing shiz");
			messageYaml.set("RANK_INVALIDRANKID", "&cYou specified an invalid rankid.");
			messageYaml.set("LOADING", "&aLoading, please wait...");
			messageYaml.set("RANK_SETRANK_SUCCESS", "&aYou set the rank of <NAME> to <RANKID>.");
			messageYaml.set("RANK_WHOIS_NAME", "&aPlayer name: <NAME>");
			messageYaml.set("RANK_WHOIS_RANK", "&aPlayer rank: <RANK> (<RANKID>)");
			messageYaml.set("RANK_WHOIS_MONEY", "&aPlayer cash: <MONEY>");
			messageYaml.set("RANK_PLAYERNOTFOUND", "&cThis player was not found.");
			messageYaml.set("RANKUP_HIGHEST", "&cYou are already the highest rank.");
			messageYaml.set("RANKUP_FAILED", "&cYou need <COST> to rankup.");
			messageYaml.set("RANKUP_SUCCESS", "&aYou ranked up to <RANK>&a.");
			messageYaml.set("RANKUP_BROADCAST", "&aPlayer <NAME> ranked up to <RANK>.");
			messageYaml.set("RANK_HELP", "&cYou have 2 options: setrank/whois");
			messageYaml.set("RANK_SETRANK_HELP", "&cYou have to specify 2 things: [name] [rankid]");
			messageYaml.set("RANK_WHOIS_HELP", "&cYou have to specify 1 thing: [name]");

			messageYaml.save(messageFile);
		}

		for(String str: messageYaml.getKeys(true)) { add(str, messageYaml.getString(str)); }
	}
}
