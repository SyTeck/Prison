package com.syteck.prison.utils;

import java.util.UUID;

import org.bukkit.Bukkit;

public class UUIDUtil {
	
	public static String toString(UUID id) { return id.toString(); }
	public static UUID toUUID(String stringId) { return UUID.fromString(stringId); }
	
	public static String getStringIdFromPlayerName(String name) { return PlayerUtil.getOfflinePlayerByName(name).getUniqueId().toString(); }
	public static String getNameFromUUID(String stringId) { return Bukkit.getOfflinePlayer(toUUID(stringId)).getName(); }

}
