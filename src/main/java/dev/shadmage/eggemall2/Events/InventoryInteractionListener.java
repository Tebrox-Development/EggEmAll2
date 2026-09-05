package dev.shadmage.eggemall2.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.menu.Menu;

@AutoRegister
public final class InventoryInteractionListener implements Listener {

	/**
	 * This EventHandler prevents players from being able to duplicate Foundation Menu items
	 * by pressing the F hotkey while hovering over items. Without this handler the
	 * item is duplicated to the players OffHand.
	 */
	@EventHandler
	public void PreventSwapOffhandWhileInventoryOpen(InventoryClickEvent event) {
		if (event.getClick() == ClickType.SWAP_OFFHAND && event.getWhoClicked() instanceof Player player) {
			if (Menu.getMenu(player) != null) {
				player.closeInventory();
				player.updateInventory();
				event.setCancelled(true);
			}
		}
	}

}
