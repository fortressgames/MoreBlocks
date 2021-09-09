package net.fortressgames.moreblocks.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

public class BlockChangeListener implements Listener {

	@EventHandler
	public void fade(BlockFadeEvent e) {

		if(e.getBlock().getType() == Material.GRASS_BLOCK) {
			Block topBlock = e.getBlock().getWorld().getBlockAt(e.getBlock().getLocation().getBlockX(), e.getBlock().getLocation().getBlockY() +1, e.getBlock().getLocation().getBlockZ());

			if(topBlock.getType() == Material.NOTE_BLOCK) {
				e.setCancelled(true);
			}
		}
	}
}