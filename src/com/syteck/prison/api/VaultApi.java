package com.syteck.prison.api;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.syteck.prison.utils.PlayerUtil;

import net.milkbowl.vault.economy.Economy;

public class VaultApi {

	private static Economy economy;

	public static void loadEconomy() {

		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

		if (economyProvider != null) {

			economy = economyProvider.getProvider();

		}
	}

	public static void takeMoney(String name, double amount) { economy.bankWithdraw(name, amount); }
	public static double getMoney(String name) { return economy.getBalance(PlayerUtil.getOfflinePlayerByName(name)); }
	public static void giveMoney(String name, double amount) { economy.bankDeposit(name, amount); }

	public static Economy getEconomy() { return economy; }

}
