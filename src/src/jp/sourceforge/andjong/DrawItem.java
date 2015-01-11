package jp.sourceforge.andjong;

import android.content.res.Resources;
import jp.sourceforge.andjong.mahjong.Hai;
import jp.sourceforge.andjong.mahjong.Hou;
import jp.sourceforge.andjong.mahjong.Mahjong;
import jp.sourceforge.andjong.mahjong.Tehai;
import jp.sourceforge.andjong.mahjong.EventIf.EventId;

/**
 * I want to manage the drawing items.
 *
 * Tenbo //Dian Bang 25000
 * KYOKU //Ju
 *
 * @author Yuji Urushibara
 *
 */
public class DrawItem {

    private boolean isDebug = false;
    public DrawItem(boolean isDebug) {
        this.m_isDebug = isDebug;
    }
	/** Initialization waiting */
	public static final int STATE_INIT_WAIT = 0;
	/** No state */
	public static final int STATE_NONE = 1;
	/** Start of Ju*/
	public static final int STATE_KYOKU_START = 2;
	/** Play State */
	public static final int STATE_PLAY = 3;
	/** Processing wait state */
	public static final int STATE_RIHAI_WAIT = 4;
	/** Zi Li state (Reach) */
	public static final int STATE_REACH = 5;
	/** State of Zi Mo  */
	public static final int STATE_TSUMO = 6;
	/** State of Hu */
	public static final int STATE_RON = 7;
	/** State of Liu Ju */
	public static final int STATE_RYUUKYOKU = 8;
	/** State of Result*/
	public static final int STATE_RESULT = 9;
	/** end */
	public static final int STATE_END = 10;

	/** state*/
	int m_state = STATE_INIT_WAIT;

	/** Ju String */
	private String m_kyokuString = null;

	/**  The number of Reach bar (zi li) */
	private int m_reachbou = 0;

	/** Beng Chang */
	private int m_honba = 0;

	/** who start playing (Started) */
	private int m_chiicha = 0;

	/** Index of pie was discarded from the tile hand */
	private int m_iSkip = 0;

	/**
	 * I set the string of stations (Ju)
	 *
	 * @param a_kyoku
	 *           Ju
	 */
	public synchronized void setKyokuString(Resources a_resources, int a_kyoku) {
		if (a_kyoku > Mahjong.KYOKU_NAN_4) {
			m_kyokuString = "Endless";
			return;
		}

		String[] kyokuStrings = a_resources.getStringArray(R.array.kyoku);
		m_kyokuString = kyokuStrings[a_kyoku];
	}

	/**
	 * I get a string of stations.
	 *
	 * @return text of Ju
	 */
	public synchronized String getKyokuString() {
		return m_kyokuString;
	}

	/**
	 * I set the number of Reach bar.
	 *
	 * @param a_reachbou
	 *            Number of Reach bar
	 */
	public synchronized void setReachbou(int a_reachbou) {
		this.m_reachbou = a_reachbou;
	}

	/**
	 * I get the number of Reach bar.
	 *
	 * @return number of Reach bar
	 */
	public synchronized int getReachbou() {
		return m_reachbou;
	}

	/**
	 * I set the home. (Beng Chang)
	 *
	 * @param a_honba
	 *            home
	 */
	public synchronized void setHonba(int a_honba) {
		this.m_honba = a_honba;
	}

	/**
	 * I get a home.
	 *
	 * @return home
	 */
	public synchronized int getHonba() {
		return m_honba;
	}

	/**
	 * I set the Okoshi-ka (Qi jia, starting player)
	 *
	 * @param a_chiicha
	 *            started
	 */
	public synchronized void setChiicha(int a_chiicha) {
		this.m_chiicha = a_chiicha;
	}

	/**
	 * get Qi jia
	 *
	 * @return started
	 */
	public synchronized int getChiicha() {
		return m_chiicha;
	}

	/**
	 * I set the index of the pie was discarded from tile hand.
	 *
	 * @param a_iSkip
	 *            Index of pie was discarded from tile hand
	 */
	public synchronized void setSkipIdx(int a_iSkip) {
		this.m_iSkip = a_iSkip;
	}

	/**
	 * I get the index of the pie was discarded from tile hand.
	 *
	 * @return index of pie was discarded from tile hand
	 */
	public synchronized int getISkip() {
		return m_iSkip;
	}

	/**
	 * set state
	 *
	 * @param m_state
	 * state
	 */
	synchronized void setState(int m_state) {
		this.m_state = m_state;
	}

	/**
	 * get state
	 *
	 * @return state
	 */
	synchronized int getState() {
		return m_state;
	}

	public class PlayerInfo {
		/** Hand 手牌 */
		Tehai m_tehai = new Tehai();
		/** He 河 */
		Hou m_kawa = new Hou();
		/** Tsumo tile  */
		Hai m_tsumoHai;
		/**  Point bar 点棒 */
		int m_tenbo;
		boolean m_tenpai;
	}

	/** Player information  */
	PlayerInfo m_playerInfos[] = new PlayerInfo[4];

	/** Debug flag */
	boolean m_isDebug = isDebug; //by gary Debug flag

	/** Event ID  */
	EventId m_eventId;

	int m_kazeFrom;
	int m_kazeTo;

	Hai m_suteHai = new Hai();

	boolean m_isManReach = false;

	{
		for (int i = 0; i < 4; i++) {
			m_playerInfos[i] = new PlayerInfo();
		}
	}

	int m_tsumoRemain = 0;
}
