package net.fortressgames.moreblocks;

import lombok.Getter;
import net.fortressgames.moreblocks.listeners.*;
import net.fortressgames.moreblocks.utils.InventoryMenu;
import net.fortressgames.moreblocks.utils.MoreBlock;
import net.fortressgames.moreblocks.utils.ViewType;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MoreBlocks extends JavaPlugin {

	@Getter	private static MoreBlocks instance;
	@Getter private Material itemType;
	@Getter private final List<MoreBlock> blocks = new ArrayList<>();

	@Getter private final HashMap<Player, InventoryMenu> openMenu = new HashMap<>();
	@Getter private final HashMap<Player, ViewType> viewType = new HashMap<>();

	@Override
	public void onLoad() {
		if(!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}

		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	@Override
	public void onEnable() {
		instance = this;

		loadBlocks();

		this.itemType = Material.valueOf(getConfig().getString("ItemType"));

		this.getServer().getPluginManager().registerEvents(new NoteBlockPlayListener(), this);
		this.getServer().getPluginManager().registerEvents(new CloseInventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
		this.getServer().getPluginManager().registerEvents(new ClickItemListener(), this);
		this.getServer().getPluginManager().registerEvents(new BlockUpdateListener(), this);

		getCommand("moreblock").setExecutor(new MoreBlocksCommand());

		System.out.println("\033[0;33m" + "[MoreBlocks] Version: " + getDescription().getVersion() + "\033[0;32m" + " Enabled!" + "\033[0m");
	}

	public void loadBlocks() {
		blocks.clear();

		for(String key : getConfig().getKeys(true)) {
			if(key.equalsIgnoreCase("ItemType")) continue;
			if(!key.contains(".")) continue;

			//Load blocks
			if(!getConfig().getString(key).equalsIgnoreCase("#NA")) {

				this.blocks.add(new MoreBlock(
						Instrument.valueOf(key.split("\\.")[0].toUpperCase()),
						new Note(Integer.valueOf(key.split("\\.")[1])), Integer.valueOf(getConfig().getString(key))));
			}
		}

		System.out.println("\033[0;33m" + "[MoreBlocks] " + "\033[0;32m" + blocks.size() + " blocks loaded!" + "\033[0m");
	}

	@Override
	public void onDisable() {
		System.out.println("\033[0;33m" + "[MoreBlocks] Version: " + getDescription().getVersion() + "\033[0;31m" + " Disabled!" + "\033[0m");
	}
}

//todo switch up and down