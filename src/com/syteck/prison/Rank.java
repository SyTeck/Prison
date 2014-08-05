package com.syteck.prison;

import org.bukkit.ChatColor;

public class Rank {

	private int rankId;
	private double cost;

	private String prefix;

	public Rank(int rankId, double cost, String prefix) {

		this.rankId = rankId;
		this.cost = cost;

		this.prefix = prefix;

	}

	public int getRankId() { return this.rankId; }
	public double getCost() { return this.cost; }

	public String getPrefix() { return this.prefix.replaceAll("&", "§")+ChatColor.RESET; }

}
