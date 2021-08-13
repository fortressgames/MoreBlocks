package net.fortressgames.moreblocks.listeners;

import net.fortressgames.moreblocks.Instrument;
import net.fortressgames.moreblocks.MoreBlocks;
import net.fortressgames.moreblocks.utils.MoreBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockUpdateListener implements Listener {

	@EventHandler
	public void use(PlayerInteractEvent e) {
		Player player = e.getPlayer();

		if(e.getHand() == EquipmentSlot.OFF_HAND) return;

		if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

			if(e.getClickedBlock().getType().name().contains("DOOR") || e.getClickedBlock().getType().name().contains("GATE")) {

				List<Block> blocks = getBlocks(e.getClickedBlock());
				if(blocks.isEmpty()) return;

				HashMap<Block, MoreBlock> oldBlocks = new HashMap<>();
				for(Block block : blocks) {
					NoteBlock noteBlock = (NoteBlock) block.getBlockData();

					oldBlocks.put(block, new MoreBlock(Instrument.toCustom(noteBlock.getInstrument()), noteBlock.getNote(), 0));
				}

				fixBlocks(oldBlocks, player);
				fixFirst(oldBlocks, e.getClickedBlock());
			}
		}
	}

	@EventHandler
	public void placeBlock(BlockPlaceEvent e) {
		Player player = e.getPlayer();

		Block place = e.getBlock();
		if(e.getBlock().getType().equals(Material.WARPED_DOOR) ||
				e.getBlock().getType().equals(Material.DARK_OAK_DOOR) ||
				e.getBlock().getType().equals(Material.ACACIA_DOOR) ||
				e.getBlock().getType().equals(Material.BIRCH_DOOR) ||
				e.getBlock().getType().equals(Material.CRIMSON_DOOR) ||
				e.getBlock().getType().equals(Material.IRON_DOOR) ||
				e.getBlock().getType().equals(Material.JUNGLE_DOOR)) {

			place = e.getBlock().getWorld().getBlockAt(
					e.getBlock().getLocation().getBlockX(),
					e.getBlock().getLocation().getBlockY() + 1,
					e.getBlock().getLocation().getBlockZ());
		}

		List<Block> blocks = getBlocks(place);
		if(blocks.isEmpty()) return;

		HashMap<Block, MoreBlock> oldBlocks = new HashMap<>();
		for(Block block : blocks) {
			NoteBlock noteBlock = (NoteBlock) block.getBlockData();

			oldBlocks.put(block, new MoreBlock(Instrument.toCustom(noteBlock.getInstrument()), noteBlock.getNote(), 0));
		}

		fixBlocks(oldBlocks, player);
		fixFirst(oldBlocks, place);
	}

	@EventHandler
	public void breakBlock(BlockBreakEvent e) {
		Player player = e.getPlayer();

		List<Block> blocks = getBlocks(e.getBlock());
		if(blocks.isEmpty()) return;

		HashMap<Block, MoreBlock> oldBlocks = new HashMap<>();
		for(Block block : blocks) {
			NoteBlock noteBlock = (NoteBlock) block.getBlockData();

			oldBlocks.put(block, new MoreBlock(Instrument.toCustom(noteBlock.getInstrument()), noteBlock.getNote(), 0));
		}

		e.setCancelled(true);
		e.getBlock().setType(Material.AIR);

		fixBlocks(oldBlocks, player);
	}

	private void fixFirst(HashMap<Block, MoreBlock> oldBlocks, Block block) {
		Bukkit.getScheduler().runTask(MoreBlocks.getInstance(), () -> {
			Block b = block.getWorld().getBlockAt(
					block.getLocation().getBlockX(),
					block.getLocation().getBlockY() + 1,
					block.getLocation().getBlockZ());

			NoteBlock noteBlock = ((NoteBlock) Bukkit.createBlockData(Material.NOTE_BLOCK));
			noteBlock.setInstrument(oldBlocks.get(b).getInstrument().getBaseInstrument());
			noteBlock.setNote(oldBlocks.get(b).getNote());

			b.setBlockData(noteBlock);
		});
	}

	private void fixBlocks(HashMap<Block, MoreBlock> oldBlocks, Player player) {
		for(Map.Entry<Block, MoreBlock> block : oldBlocks.entrySet()) {
			NoteBlock noteBlock = ((NoteBlock) Bukkit.createBlockData(Material.NOTE_BLOCK));
			noteBlock.setInstrument(block.getValue().getInstrument().getBaseInstrument());
			noteBlock.setNote(block.getValue().getNote());

			block.getKey().setBlockData(noteBlock);

			player.sendBlockChange(block.getKey().getLocation(), noteBlock);
		}
	}

	private List<Block> getBlocks(Block block) {
		List<Block> blocks = new ArrayList<>();

		for(int i = 1; i < 256; i++) {

			Block b = block.getWorld().getBlockAt(
					block.getLocation().getBlockX(),
					block.getLocation().getBlockY() + i,
					block.getLocation().getBlockZ());

			if(b.getType().equals(Material.NOTE_BLOCK)) {
				blocks.add(b);
			} else {
				break;
			}
		}

		return blocks;
	}
}