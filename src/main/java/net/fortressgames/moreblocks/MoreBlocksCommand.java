package net.fortressgames.moreblocks;

import net.fortressgames.moreblocks.menus.MoreBlocksMenu;
import net.fortressgames.moreblocks.utils.ViewType;
import org.bukkit.ChatColor;
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

		if(sender.hasPermission("moreblocks.reload")) {

			if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
				MoreBlocks.getInstance().reloadConfig();
				MoreBlocks.getInstance().loadBlocks();
				sender.sendMessage(ChatColor.GREEN + "Reloaded!");
				sender.sendMessage(ChatColor.WHITE + ChatColor.BOLD.toString() + MoreBlocks.getInstance().getBlocks().size() + ChatColor.GREEN + " blocks loaded!");
			}
			return true;
		}

		sender.sendMessage(ChatColor.RED + "You do not have permission for this!");
		return false;
	}
}