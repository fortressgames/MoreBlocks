package net.fortressgames.moreblocks.listeners;

import net.fortressgames.moreblocks.MoreBlocks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CloseInventoryListener implements Listener {

	public static List<Player> singleSub = new ArrayList<>();

	public static HashMap<Player, ItemStack> offhand = new HashMap<>();

	@EventHandler
	public void close(InventoryCloseEvent e) {
		Player player = (Player)e.getPlayer();

		if(MoreBlocks.getInstance().getOpenMenu().containsKey(player) && !singleSub.contains(player)) {

			if(MoreBlocks.getInstance().getOpenMenu().get(player).getInventoryName().equalsIgnoreCase(player.getOpenInventory().getTitle())) {
				MoreBlocks.getInstance().getOpenMenu().remove(player);
			}
		}

			singleSub.remove(player);

		if(offhand.containsKey(player)) {
			player.getInventory().setItemInOffHand(offhand.get(player));
			offhand.remove(player);
		}
	}
}