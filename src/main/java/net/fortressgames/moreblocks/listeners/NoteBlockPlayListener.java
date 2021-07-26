package net.fortressgames.moreblocks.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.NotePlayEvent;

public class NoteBlockPlayListener implements Listener {

	@EventHandler
	public void play(NotePlayEvent e) {
		e.setCancelled(true);
	}
}