package com.syteck.prison.events;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import com.syteck.prison.Messages;
import com.syteck.prison.Prison;
import com.syteck.prison.Rank;
import com.syteck.prison.Storage;
import com.syteck.prison.User;
import com.syteck.prison.api.VaultApi;
import com.syteck.prison.utils.NumberUtil;
import com.syteck.prison.utils.UUIDUtil;

public class Events implements Listener {

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event) {

		String stringId = UUIDUtil.toString(event.getPlayer().getUniqueId());

		try { Storage.loadUser(stringId); } catch (IOException e) { e.printStackTrace(); }
	}

	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent event) {

		String stringId = UUIDUtil.toString(event.getPlayer().getUniqueId());

		try { Storage.saveUser(stringId); } catch (IOException e) { e.printStackTrace(); }
		Storage.removeUser(stringId);
	}

	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {

		if(event.isCancelled()) return;

		String stringId = UUIDUtil.toString(event.getPlayer().getUniqueId());
		String prefix = Storage.getUser(stringId).getRank().getPrefix();

		event.setFormat(event.getFormat().replaceAll(Prison.prefixTag, prefix));
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event) {

		if(event.getInventory().getTitle().equalsIgnoreCase("Rankup menu")) {

			event.setCancelled(true);

			ItemStack is = event.getCurrentItem();

			if(is.getType() == Material.WOOL) {

				if(is.getData().getData() == DyeColor.GREEN.getData()) {

					Player p = (Player) event.getWhoClicked();

					User user = Storage.getUser(UUIDUtil.toString(p.getUniqueId()));
					Rank targetRank = Storage.getRank(user.getRankId()-1);

					double current = VaultApi.getMoney(p.getName());
					double needed = targetRank.getCost();

					if(!NumberUtil.canAfford(current, needed)) {

						p.sendMessage(Messages.get("RANKUP_FAILED").replaceAll("<MISSING>", ""+NumberUtil.missing(current, needed)).replaceAll("<COST>", ""+needed).replaceAll("<RANK>", targetRank.getPrefix()).replaceAll("<RANKID>", ""+targetRank.getRankId()));		

					} else {

						user.setRank(targetRank.getRankId());

						p.sendMessage(Messages.get("RANKUP_SUCCESS").replaceAll("<RANK>", targetRank.getPrefix()).replaceAll("<RANKID>", ""+targetRank.getRankId()));
						Bukkit.broadcastMessage(Messages.get("RANKUP_BROADCAST").replaceAll("<RANK>", targetRank.getPrefix()).replaceAll("<RANKID>", ""+targetRank.getRankId()).replaceAll("<NAME>", p.getName()));

						Firework fw = (Firework) p.getWorld().spawn(p.getLocation(), Firework.class);
						FireworkMeta fwMeta = fw.getFireworkMeta();
						FireworkEffect fwe = FireworkEffect.builder().trail(true).withColor(Color.AQUA).withColor(Color.GREEN).withColor(Color.PURPLE).withColor(Color.ORANGE).build();
						fwMeta.addEffect(fwe);

						VaultApi.takeMoney(p.getName(), needed);
						try { Storage.saveUser(UUIDUtil.getStringIdFromPlayerName(p.getName())); } catch (IOException e) { e.printStackTrace(); }

					}

					event.getWhoClicked().closeInventory();
					event.getInventory().clear();

				} else if(is.getData().getData() == DyeColor.RED.getData()) {

					event.getWhoClicked().closeInventory();
					event.getInventory().clear();

				}
			}
		}
	}

}
