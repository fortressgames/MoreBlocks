package net.fortressgames.moreblocks;

import lombok.Getter;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;

public enum Instrument {

	HARP(org.bukkit.Instrument.PIANO),
	BASEDRUM(org.bukkit.Instrument.BASS_DRUM),
	SNARE_DRUM(org.bukkit.Instrument.SNARE_DRUM),
	STICKS(org.bukkit.Instrument.STICKS),
	BASS_GUITAR(org.bukkit.Instrument.BASS_GUITAR),
	FLUTE(org.bukkit.Instrument.FLUTE),
	BELL(org.bukkit.Instrument.BELL),
	GUITAR(org.bukkit.Instrument.GUITAR),
	CHIME(org.bukkit.Instrument.CHIME),
	XYLOPHONE(org.bukkit.Instrument.XYLOPHONE),
	IRON_XYLOPHONE(org.bukkit.Instrument.IRON_XYLOPHONE),
	COW_BELL(org.bukkit.Instrument.COW_BELL),
	DIDGERIDOO(org.bukkit.Instrument.DIDGERIDOO),
	BIT(org.bukkit.Instrument.BIT),
	BANJO(org.bukkit.Instrument.BANJO),
	PLING(org.bukkit.Instrument.PLING)
	;

	@Getter private final org.bukkit.Instrument baseInstrument;

	Instrument(org.bukkit.Instrument baseInstrument) {
		this.baseInstrument = baseInstrument;
	}

	@Nullable
	public static Instrument toCustom(org.bukkit.Instrument instrument) {
		for(Instrument i : values()) {
			if(i.baseInstrument.equals(instrument)) return i;
		}

		return null;
	}
}