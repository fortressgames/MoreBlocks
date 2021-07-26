package net.fortressgames.moreblocks.menus;

import net.fortressgames.moreblocks.MoreBlocks;
import net.fortressgames.moreblocks.utils.InventoryMenu;
import net.fortressgames.moreblocks.utils.ItemBuilder;
import net.fortressgames.moreblocks.utils.MoreBlock;
import net.fortressgames.moreblocks.utils.ViewType;
import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class MoreBlocksMenu extends InventoryMenu {

	public MoreBlocksMenu(Player player, int page) {
		super(player, 54, ChatColor.BLUE + ChatColor.BOLD.toString() + "More Blocks! " + ChatColor.AQUA + "(Page: " + page + ")");

		List<MoreBlock> blocks = getBlocks();

		int slot = 0;
		for(int i = 0; i < 45; i++) {

			if(blocks.size() <= i + ((page -1) * 45)) break;

			MoreBlock moreBlock = blocks.get(i + ((page -1) * 45));

			setItem(block(moreBlock.getInstrument(), moreBlock.getNote().getId(), moreBlock.getModelID()), slot, inventoryClickEvent -> {

				player.getInventory().addItem(inventoryClickEvent.getCurrentItem());
				player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
			});

			slot++;
		}

		if(page >= 2) {
			setItem(createPreviousPage(), 45, inventoryClickEvent -> {
				new MoreBlocksMenu(player, page-1).openInventory();
				player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 2);
			});
		}

		if(slot == 45) {
			setItem(createNextPage(), 53, inventoryClickEvent -> {
				new MoreBlocksMenu(player, page+1).openInventory();
				player.playSound(player.getLocation(), Sound.ITEM_BOOK_PAGE_TURN, 1, 2);
			});
		}

		if(blocks.isEmpty()) {
			setItem(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name(ChatColor.RED + "No blocks!").lore(
					ChatColor.GRAY + "Add blocks in the MoreBlocks config!"
			).build(), 22);
		}

		setItem(view(), 49, inventoryClickEvent -> {
			MoreBlocks.getInstance().getViewType().replace(player, ViewType.next(
					MoreBlocks.getInstance().getViewType().get(getPlayer())
			));

			new MoreBlocksMenu(player, 1).openInventory();
			player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 2);
		});
	}

	private List<MoreBlock> getBlocks() {
		List<MoreBlock> moreBlocks = new ArrayList<>();

		for(MoreBlock moreBlock : MoreBlocks.getInstance().getBlocks()) {

			if(MoreBlocks.getInstance().getViewType().get(getPlayer()).equals(ViewType.ALL) ||
					MoreBlocks.getInstance().getViewType().get(getPlayer()).equals(ViewType.valueOf(moreBlock.getInstrument().name()))) {

				moreBlocks.add(moreBlock);
			}
		}

		return moreBlocks;
	}

	private ItemStack block(Instrument instrument, int note, int modelID) {
		return new ItemBuilder(MoreBlocks.getInstance().getItemType()).name(ChatColor.GREEN + instrument.name().replace("_", " ")).lore(
				ChatColor.GRAY + "Note: " + note
		).customModelData(modelID).build();
	}

	private ItemStack view() {
		List<String> lore = new ArrayList<>();

		if(MoreBlocks.getInstance().getViewType().get(getPlayer()).equals(ViewType.ALL)) {
			lore.add(ChatColor.GRAY + "ALL -" + ChatColor.GREEN + ChatColor.BOLD + " [SELECTED]");

		} else {
			lore.add(ChatColor.GRAY + "ALL");
		}

		for(Instrument instrument : Instrument.values()) {

			if(MoreBlocks.getInstance().getViewType().get(getPlayer()).equals(ViewType.valueOf(instrument.name()))) {
				lore.add(ChatColor.GRAY + instrument.name() + " -" + ChatColor.GREEN + ChatColor.BOLD + " [SELECTED]");

			} else {
				lore.add(ChatColor.GRAY + instrument.name());
			}
		}

		return new ItemBuilder(Material.KNOWLEDGE_BOOK).name(ChatColor.GOLD + "View type:").lore(lore).build();
	}
}