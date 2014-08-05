package com.syteck.prison;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.syteck.prison.api.VaultApi;
import com.syteck.prison.commands.Commands;
import com.syteck.prison.events.Events;

public class Prison extends JavaPlugin {

	public static final String version = "1";

	public static File userPath;
	public static File rankFile;
	public static File messageFile;

	public static String prefixTag;

	@Override
	public void onEnable() {

		userPath = new File(this.getDataFolder()+"\\"+"users");
		rankFile = new File(this.getDataFolder(), "ranks.yml");
		messageFile = new File(this.getDataFolder(), "messages.yml");

		this.saveDefaultConfig();
		Storage.setupStorage();

		prefixTag = this.getConfig().getString("prefix-tag");
		VaultApi.loadEconomy();
		try { Storage.loadRanks(); } catch (IOException e) { e.printStackTrace(); }
		try { Messages.loadMessages(); } catch (IOException e) { e.printStackTrace(); }
		if(!userPath.exists()) { userPath.mkdir(); }

		Bukkit.getPluginManager().registerEvents(new Events(), this);
		getCommand("rank").setExecutor(new Commands());
		getCommand("rankup").setExecutor(new Commands());

		Bukkit.getLogger().log(Level.INFO, "Prison plugin running version "+version+" made by SyTeck.");
	}

	@Override
	public void onDisable() {

		Storage.saveAllUsers();

	}



}
