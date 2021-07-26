package net.fortressgames.moreblocks;

import net.fortressgames.moreblocks.menus.MoreBlocksMenu;
import net.fortressgames.moreblocks.utils.ViewType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MoreBlocksCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender.hasPermission("moreblocks.menu") && sender instanceof Player) {

			if(!MoreBlocks.getInstance().getViewType().containsKey(sender)) {
				MoreBlocks.getInstance().getViewType().put((Player)sender, ViewType.ALL);
			}

			new MoreBlocksMenu((Player)sender, 1).openInventory();
			return true;
		}

		return false;
	}
}