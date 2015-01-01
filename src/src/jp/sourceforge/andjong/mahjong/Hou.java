package jp.sourceforge.andjong.mahjong;

/**
 * I want to manage the river.
 *
 * @author Yuji Urushibara
 *
 */
public class Hou {
	/** The maximum value of the length of the array of discarded tile * / */
	public final static int SUTE_HAIS_LENGTH_MAX = 24;

	/**  Array of discarded tile */
	private SuteHai[] m_suteHais = new SuteHai[SUTE_HAIS_LENGTH_MAX];

	/** The length of the array of discarded tile */
	private int m_suteHaisLength = 0;

	{
		for (int i = 0; i < SUTE_HAIS_LENGTH_MAX; i++) {
			m_suteHais[i] = new SuteHai();
		}
	}

	/**
	 * I want to create a river.
	 */
	public Hou() {
		initialize();
	}

	/**
	 * I want to initialize the river
	 */
	public void initialize() {
		m_suteHaisLength = 0;
	}

	/**
	 * I want to copy the river.
	 *
	 * @param a_dest
	 *            Destination of river
	 * @param a_src
	 *             Copy of the river
	 */
	public static void copy(Hou a_dest, Hou a_src) {
		a_dest.m_suteHaisLength = a_src.m_suteHaisLength;
		for (int i = 0; i < a_dest.m_suteHaisLength; i++) {
			SuteHai.copy(a_dest.m_suteHais[i], a_src.m_suteHais[i]);
		}
	}

	/**
	 * I get an array of discarded tile.
	 *
	 * @return rray of discarded tile
	 */
	public SuteHai[] getSuteHais() {
		return m_suteHais;
	}

	/**
	 *  I get the length of the array of discarded tile
	 *
	 * @return he length of the array of discarded tile
	 */
	public int getSuteHaisLength() {
		return m_suteHaisLength;
	}

	/**
	 * I want to add a pie array of discarded tile.
	 *
	 * @param a_hai
	 *            Tiles you want to add
	 */
	public boolean add(Hai a_hai) {
		if (m_suteHaisLength >= SUTE_HAIS_LENGTH_MAX) {
			return false;
		}

		SuteHai.copy(m_suteHais[m_suteHaisLength], a_hai);
		m_suteHaisLength++;

		return true;
	}

	/**
	 * At the end of the tiles of the array of discarded tile, I set the squeal flag.
	 *
	 * @param a_naki
	 *            Squeal flag
	 */
	public boolean setNaki(boolean a_naki) {
		if (m_suteHaisLength <= 0) {
			return false;
		}

		m_suteHais[m_suteHaisLength - 1].setNaki(a_naki);

		return true;
	}

	/**
	 * At the end of the tiles of the array of discarded tile, I set the reach flag.
	 *
	 * @param a_reach
	 *            Reach flag
	 */
	public boolean setReach(boolean a_reach) {
		if (m_suteHaisLength <= 0) {
			return false;
		}

		m_suteHais[m_suteHaisLength - 1].setReach(a_reach);

		return true;
	}

	/**
	 * At the end of the tiles of the array of discarded tile, I set the flag mess around.
	 *
	 * @param a_tedashi
	 *             Mess around flag
	 */
	public boolean setTedashi(boolean a_tedashi) {
		if (m_suteHaisLength <= 0) {
			return false;
		}

		m_suteHais[m_suteHaisLength - 1].setTedashi(a_tedashi);

		return true;
	}
}
