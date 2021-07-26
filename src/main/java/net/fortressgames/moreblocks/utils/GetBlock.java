package net.fortressgames.moreblocks.utils;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class GetBlock {

	public static Block get(BlockFace blockFace, Block block) {
		Block placedBlock = null;

		switch (blockFace) {
			case UP:
				placedBlock = block.getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY() +1, block.getLocation().getBlockZ());
				break;

			case DOWN:
				placedBlock = block.getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY() -1, block.getLocation().getBlockZ());
				break;

			case SOUTH:
				placedBlock = block.getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ() +1);
				break;

			case EAST:
				placedBlock = block.getWorld().getBlockAt(block.getLocation().getBlockX() +1, block.getLocation().getBlockY(), block.getLocation().getBlockZ());
				break;

			case NORTH:
				placedBlock = block.getWorld().getBlockAt(block.getLocation().getBlockX(), block.getLocation().getBlockY(), block.getLocation().getBlockZ() -1);
				break;

			case WEST:
				placedBlock = block.getWorld().getBlockAt(block.getLocation().getBlockX() -1, block.getLocation().getBlockY(), block.getLocation().getBlockZ());
				break;
		}

		return placedBlock;
	}
}