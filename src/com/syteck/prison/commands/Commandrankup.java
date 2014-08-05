package com.syteck.prison.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.syteck.prison.Messages;
import com.syteck.prison.Storage;
import com.syteck.prison.utils.UUIDUtil;

public class Commandrankup {

	private Player player;

	public Commandrankup(Player player) {

		this.player = player;

		if(Storage.getUser(UUIDUtil.toString(player.getUniqueId())).getRankId() == 1) {

			player.sendMessage(Messages.get("RANKUP_HIGHEST"));

		} else {

			setupGUI();

		}
	}

	@SuppressWarnings("deprecation")
	public void setupGUI() {

		ItemStack rankup = new ItemStack(Material.WOOL, 1, DyeColor.GREEN.getData());
		ItemStack information = new ItemStack(Material.PAPER, 1);
		ItemStack cancel = new ItemStack(Material.WOOL, 1, DyeColor.RED.getData());

		ItemMeta imRankup = rankup.getItemMeta();
		ItemMeta imInformation = rankup.getItemMeta();
		ItemMeta imCancel = rankup.getItemMeta();

		imRankup.setDisplayName(ChatColor.GREEN+"Rankup");
		imInformation.setDisplayName(ChatColor.LIGHT_PURPLE+"Information");
		imCancel.setDisplayName(ChatColor.RED+"Cancel");

		ArrayList<String> lore = new ArrayList<String>();
		lore.add(Messages.get("RANKUP_LORE_1"));
		lore.add(Messages.get("RANKUP_LORE_2"));
		lore.add(Messages.get("RANKUP_LORE_3"));
		imInformation.setLore(lore);

		rankup.setItemMeta(imRankup);
		information.setItemMeta(imInformation);
		cancel.setItemMeta(imCancel);

		Inventory gui = Bukkit.createInventory(null, 9, "Rankup menu");

		gui.setItem(0, rankup);
		gui.setItem(4, information);
		gui.setItem(8, cancel);

		this.player.openInventory(gui);

	}
}
