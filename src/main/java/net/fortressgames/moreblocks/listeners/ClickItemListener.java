package net.fortressgames.moreblocks.listeners;

import net.fortressgames.moreblocks.MoreBlocks;
import net.fortressgames.moreblocks.utils.GetBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ClickItemListener implements Listener {

	private final List<Player> delay = new ArrayList<>();

	@EventHandler
	public void use(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if(e.getHand() == EquipmentSlot.OFF_HAND) return;

		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.NOTE_BLOCK) {
			if(!player.isSneaking() || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
				e.setCancelled(true);
			}
		}

		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {

			if(player.getInventory().getItemInMainHand().getType().equals(MoreBlocks.getInstance().getItemType())) {

				if(delay.contains(player)) return;
				delay.add(player);
				Bukkit.getScheduler().scheduleSyncDelayedTask(MoreBlocks.getInstance(), () -> delay.remove(player), 2);

				if(player.getInventory().getItemInMainHand().getItemMeta().hasLore()) {

					ItemStack item = player.getInventory().getItemInMainHand();
					Block block = GetBlock.get(e.getBlockFace(), e.getClickedBlock());

					if(!block.getType().isAir()) return;

					NoteBlock noteBlock = ((NoteBlock) Bukkit.createBlockData(Material.NOTE_BLOCK));;

					noteBlock.setInstrument(Instrument.valueOf(ChatColor.stripColor(item.getItemMeta().getDisplayName().replace(" ", "_"))));
					noteBlock.setNote(new Note(Integer.valueOf(ChatColor.stripColor(item.getItemMeta().getLore().get(0).replace("Note: ", "")))));

					block.setBlockData(noteBlock);
					player.playSound(player.getLocation(), Sound.BLOCK_STONE_PLACE, 1, 1);
					e.setCancelled(true);
				}
			}
		}
	}
}