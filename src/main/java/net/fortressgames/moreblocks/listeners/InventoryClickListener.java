package net.fortressgames.moreblocks.listeners;

import net.fortressgames.moreblocks.MoreBlocks;
import net.fortressgames.moreblocks.utils.InventoryMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void click(InventoryClickEvent e) {
		Player player = (Player) e.getWhoClicked();

		if(MoreBlocks.getInstance().getOpenMenu().containsKey(player)) {

			if(e.getClickedInventory() == null) return;

			if(e.getAction() == InventoryAction.HOTBAR_SWAP) {
				e.setCancelled(true);
				return;
			}

			InventoryMenu inventoryMenu = MoreBlocks.getInstance().getOpenMenu().get(player);

			e.setCancelled(true);

			if(inventoryMenu.getSlots().containsKey(e.getSlot()) && e.getClickedInventory().equals(inventoryMenu.getInventory())) {
				inventoryMenu.getSlots().get(e.getSlot()).accept(e);
			}
		}
	}
}