package net.fortressgames.moreblocks.utils;

import lombok.Data;
import net.fortressgames.moreblocks.Instrument;
import org.bukkit.Note;

@Data
public class MoreBlock {

	private final Instrument instrument;
	private final Note note;
	private final int modelID;

	public MoreBlock(Instrument instrument, Note note, int modelID) {
		this.instrument = instrument;
		this.note = note;
		this.modelID = modelID;
	}
}