package com.syteck.prison;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.configuration.file.YamlConfiguration;

public class Storage {

	private static HashMap<String, User> userMap = new HashMap<String, User>();
	private static HashMap<Integer, Rank> rankMap = new HashMap<Integer, Rank>();

	public static final int highestRank = 1;
	public static int lowestRank;

	public static void addUser(String stringId, int rankId) { userMap.put(stringId, new User(stringId, rankId)); }
	public static User getUser(String stringId) { return userMap.get(stringId); }
	public static void removeUser(String stringId) { userMap.remove(stringId); }

	public static void addRank(int rankId, int cost, String prefix) { rankMap.put(rankId, new Rank(rankId, cost, prefix)); }
	public static Rank getRank(int rankId) { return rankMap.get(rankId); }

	public static void setupStorage() {

		try { loadRanks(); } catch (IOException e) { e.printStackTrace(); }

		int i = 1;

		for(Entry<Integer, Rank> entry: rankMap.entrySet()) {

			if(entry.getKey() > i) i = entry.getKey();

		}

		lowestRank = i;
	}

	public static void loadUser(String stringId) throws IOException {

		File userFile = new File(Prison.userPath, stringId+".yml");
		YamlConfiguration userYaml = YamlConfiguration.loadConfiguration(userFile);

		if(!userFile.exists()) {

			addUser(stringId, lowestRank);

		} else {

			int rankId = userYaml.getInt("rankid");

			addUser(stringId, rankId);
		}
	}

	public static User getUserFromStore(String stringId) {

		File userFile = new File(Prison.userPath, stringId+".yml");
		YamlConfiguration userYaml = YamlConfiguration.loadConfiguration(userFile);

		if(userFile.exists()) {

			int rankId = userYaml.getInt("rankid");

			return new User(stringId, rankId);
		}

		return null;
	}

	public static void saveAllUsers() {

		for(Entry<String, User> entry: userMap.entrySet()) {

			try { saveUser(entry.getKey()); } catch (IOException e) { e.printStackTrace(); }

		}
	}

	public static void saveUser(String stringId) throws IOException {

		File userFile = new File(Prison.userPath, stringId+".yml");
		YamlConfiguration userYaml = YamlConfiguration.loadConfiguration(userFile);

		if(getUser(stringId) == null) {

			userYaml.save(userFile);

		} else {

			if(!userFile.exists()) { userFile.createNewFile(); }

			userYaml.set("uuid", stringId);
			userYaml.set("rankid", getUser(stringId).getRankId());

			userYaml.save(userFile);
		}
	}

	public static void loadRanks() throws IOException {

		File rankFile = Prison.rankFile;
		YamlConfiguration rankYaml = YamlConfiguration.loadConfiguration(rankFile);

		if(!rankFile.exists()) {

			rankFile.createNewFile();

			rankYaml.set("1.cost", 10);
			rankYaml.set("1.prefix", "&aHighest");

			rankYaml.set("2.cost", 5);
			rankYaml.set("2.prefix", "&1Mid");

			rankYaml.set("3.cost", 0);
			rankYaml.set("3.prefix", "&cLowest");

			rankYaml.save(rankFile);
		}

		for(String str: rankYaml.getKeys(false)) {

			int rankId = Integer.parseInt(str);
			int cost = rankYaml.getInt(str+".cost");

			String prefix = rankYaml.getString(str+".prefix");

			addRank(rankId, cost, prefix);
		}
	}



}
