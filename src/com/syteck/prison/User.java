package com.syteck.prison;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class User {

	private String stringId;

	private int rankId;

	public User(String stringId, int rankId) {

		this.stringId = stringId;

		this.rankId = rankId;

	}

	public String getStringId() { return this.stringId; }
	public UUID getUUID() { return UUID.fromString(this.stringId); }
	public OfflinePlayer getOfflinePlayer() { return Bukkit.getOfflinePlayer(this.getUUID()); }

	public void setRank(int rankId) { this.rankId = rankId; }
	public int getRankId() { return this.rankId; }
	public Rank getRank() { return Storage.getRank(this.rankId); }


}
