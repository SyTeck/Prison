package com.syteck.prison.utils;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerUtil {
	
	@SuppressWarnings("deprecation")
	public static OfflinePlayer getOfflinePlayerByName(String name) { return Bukkit.getOfflinePlayer(name); }
	
	public static Player playerOfSender(CommandSender sender) { return (Player) sender; }
	public static boolean isPlayer(CommandSender sender) { return sender instanceof Player; }

}
