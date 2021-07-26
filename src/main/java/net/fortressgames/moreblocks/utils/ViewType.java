package net.fortressgames.moreblocks.utils;

public enum ViewType {

	ALL(0),
	PIANO(1),
	BASS_DRUM(2),
	SNARE_DRUM(3),
	STICKS(4),
	BASS_GUITAR(5),
	FLUTE(6),
	BELL(7),
	GUITAR(8),
	CHIME(9),
	XYLOPHONE(10),
	IRON_XYLOPHONE(11),
	COW_BELL(12),
	DIDGERIDOO(13),
	BIT(14),
	BANJO(15),
	PLING(16)
	;

	private final int id;

	ViewType(int id) {
		this.id = id;
	}

	public static ViewType next(ViewType type) {

		for(ViewType viewType : ViewType.values()) {
			if(viewType.id == type.id +1) return viewType;
		}

		return ALL;
	}
}