package jp.sourceforge.andjong.mahjong;

/**
 * This is a class that manages the event IF.
 *
 * @author Yuji Urushibara
 *
 */
public interface EventIf {
	/**  Event ID */
	enum EventId {
		/** Start of the game */
		START_GAME,
		/** Start of  stations */
		START_KYOKU,
		/** TSUMO */
		TSUMO,
		/** Discarded tile of choice */
		SELECT_SUTEHAI,
		/** Discarded tile */
		SUTEHAI,
		/** REACH */
		REACH,
		/** PON */
		PON,
		/**CHII_LEFT */
		CHII_LEFT,
		/** CHII_CENTER */
		CHII_CENTER,
		/** CHII_RIGHT */
		CHII_RIGHT,
		/**DAIMINKAN */
		DAIMINKAN,
		/** KAN*/
		KAN,
		/** ANKAN*/
		ANKAN,
		/** Tsumo up*/
		TSUMO_AGARI,
		/** Ron up */
		RON_AGARI,
		/** Sink (Liu Ju)*/
		NAGASHI,
		/** Flow Board */
		RYUUKYOKU,
		/** The stations end */
		END_KYOKU,
		/** End of the game */
		END_GAME,

		/** Ron check */
		RON_CHECK,

		/** Chi-processor cards to be */
		UI_WAIT_RIHAI,
		/**  Progress waiting */
		UI_WAIT_PROGRESS,
		/** Input of player action * / */
		UI_INPUT_PLAYER_ACTION
	}

	/** Wind (east) */
	public final static int KAZE_TON = 0;
	/** Style (south) */
	public final static int KAZE_NAN = 1;
	/**  Wind (west)  */
	public final static int KAZE_SHA = 2;
	/**style (north) */
	public final static int KAZE_PE = 3;
	/**Style kind of number */
	public final static int KAZE_KIND_NUM = KAZE_PE + 1;
	/** Style (none) */
	public final static int KAZE_NONE = 4;

	/**
	 * I get the name of the event IF.
	 *
	 * @return The name of the event IF
	 */
	public String getName();

	/**
	 * The index of the discarded tile I get.
	 *
	 * @return the index of the discarded tile
	 */
	int getISutehai();

	/**
	 * I will handle the event.
	 *
	 * @param a_eventId
	 *              Event ID
	 * @param a_kazeFrom
	 *            Wind that issued the event
	 * @param a_kazeTo
	 *            Wind became the events of interest
	 * @return Event ID
	 */
	EventId event(EventId a_eventId, int a_kazeFrom, int a_kazeTo);
}
