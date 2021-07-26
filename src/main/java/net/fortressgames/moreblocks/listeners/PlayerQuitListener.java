package net.fortressgames.moreblocks.listeners;

import net.fortressgames.moreblocks.MoreBlocks;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

	@EventHandler
	public void quit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		MoreBlocks.getInstance().getViewType().remove(player);
	}

	@EventHandler
	public void block(BlockPhysicsEvent e) {
		if(e.getBlock().getType() == Material.NOTE_BLOCK) {
			e.setCancelled(true);
		}
	}
}