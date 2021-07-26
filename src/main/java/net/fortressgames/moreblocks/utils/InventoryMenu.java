package net.fortressgames.moreblocks.utils;

import lombok.Getter;
import lombok.Setter;
import net.fortressgames.moreblocks.MoreBlocks;
import net.fortressgames.moreblocks.listeners.CloseInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class InventoryMenu {

//Inventory numbers:

// |                                            |
// | 0  | 1  | 2  | 3  | 4  | 5  | 6  | 7  | 8  |
// | 9  | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 |
// | 18 | 19 | 20 | 21 | 22 | 23 | 24 | 25 | 26 |
// | 27 | 28 | 29 | 30 | 31 | 32 | 33 | 34 | 35 |
// | 36 | 37 | 38 | 39 | 40 | 41 | 42 | 43 | 44 |
// | 45 | 46 | 47 | 48 | 49 | 50 | 51 | 52 | 53 |
// |                                            |

	@Getter private final String inventoryName;

	@Getter private final Inventory inventory;
	@Getter private final Player player;

	@Getter private final HashMap<Integer, Consumer<InventoryClickEvent>> slots = new HashMap<>();

	public InventoryMenu(Player player, int size, String inventoryName) {
		this.inventory = Bukkit.createInventory(player, size, inventoryName);
		this.player = player;
		this.inventoryName = inventoryName;
	}

	/**
	 * Set item in inventory
	 *
	 * @param itemStack item
	 * @param slot inventory slot
	 */
	public void setItem(ItemStack itemStack, int slot) {
		inventory.setItem(slot, itemStack);
	}

	/**
	 * Set item in inventory and click action
	 *
	 * @param itemStack item
	 * @param slot inventory slot
	 * @param event click action
	 */
	public void setItem(ItemStack itemStack, int slot, Consumer<InventoryClickEvent> event) {
		inventory.setItem(slot, itemStack);

		if(slots.containsKey(slot)) {
			slots.replace(slot, event);
		} else {
			slots.put(slot, event);
		}
	}

	/**
	 * Update item for slot
	 *
	 * @param itemStack item
	 * @param slot inventory slot
	 */
	public void updateItem(ItemStack itemStack, int slot) {
		inventory.setItem(slot, itemStack);
	}

	/**
	 * Update item and click event
	 *
	 * @param itemStack item
	 * @param slot inventory slot
	 * @param event InventoryClickEvent
	 */
	public void updateItem(ItemStack itemStack, int slot, Consumer<InventoryClickEvent> event) {
		inventory.setItem(slot, itemStack);

		if(slots.containsKey(slot)) {
			slots.replace(slot, event);
		} else {
			slots.put(slot, event);
		}
	}

	/**
	 * Open the inventory
	 */
	public void openInventory() {
		if(MoreBlocks.getInstance().getOpenMenu().containsKey(player)) {
			MoreBlocks.getInstance().getOpenMenu().replace(player, this);
			CloseInventoryListener.singleSub.add(player);

		} else {
			MoreBlocks.getInstance().getOpenMenu().put(player, this);
		}

		this.player.openInventory(this.inventory);
		CloseInventoryListener.offhand.put(player, player.getInventory().getItemInOffHand());
	}

	/***
	 * Create basic items
	 *
	 * @return itemstack
	 */
	protected ItemStack createBackItem() {
		return new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Go back").build();
	}
	protected ItemStack createQuitItem() {
		return new ItemBuilder(Material.ARROW).name(ChatColor.RED + "Exit menu").build();
	}
	protected ItemStack createNextPage() {
		return new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Next page").build();
	}
	protected ItemStack createPreviousPage() {
		return new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Previous page").build();
	}
}