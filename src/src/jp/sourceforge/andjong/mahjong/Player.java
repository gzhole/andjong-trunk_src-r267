package jp.sourceforge.andjong.mahjong;

/**
 * Manage player
 *
 * @author Yuji Urushibara
 *
 */
class Player {
	/** EventIF */
	private EventIf eventIf;

	/**
	 *
	 *
	 * @return EventIF
	 */
	EventIf getEventIf() {
		return eventIf;
	}

	/** 手牌 */
	private Tehai m_tehai = new Tehai();

	/**
	 *
	 *
	 * @return
	 */
	Tehai getTehai() {
		return m_tehai;
	}

	/** �� */
	private Hou kawa = new Hou();

	/**
	 * 河
	 *
	 * @return 河
	 */
	Hou getKawa() {
		return kawa;
	}

	/** 自風 */
	private int jikaze;

	/**
	 * 自風
	 *
	 * @return
	 */
	int getJikaze() {
		return jikaze;
	}

	/**
	 * 自風
	 *
	 * @param jikaze
	 *            ����
	 */
	void setJikaze(int jikaze) {
		this.jikaze = jikaze;
	}

	/** 点棒_ */
	private int tenbou;

	/**
	 * 点棒
	 *
	 * @return
	 */
	int getTenbou() {
		return tenbou;
	}

	/**
	 * 点棒
	 *
	 * @param tenbou
	 *
	 */
	void setTenbou(int tenbou) {
		this.tenbou = tenbou;
	}

	/**
	 * 点棒
	 *
	 * @param ten
	 *
	 */
	void increaseTenbou(int ten) {
		tenbou += ten;
	}

	/**
	 * 点棒
	 *
	 * @param ten
	 *
	 */
	void reduceTenbou(int ten) {
		tenbou -= ten;
	}

	/** reach` */
	private boolean reach;

	/**
	 * reach
	 *
	 * @return
	 */
	boolean isReach() {
		return reach;
	}

	/**
	 * reach
	 *
	 * @param reach
	 *
	 */
	void setReach(boolean reach) {
		this.reach = reach;
	}

	/** doubleReach */
	private boolean m_doubleReach;

	/**
	 * doubleReach
	 *
	 * @return
	 */
	boolean isDoubleReach() {
		return m_doubleReach;
	}

	/**
	 * doubleReach
	 *
	 * @param a_doubleReach
	 *
	 */
	void setDoubleReach(boolean a_doubleReach) {
		this.m_doubleReach = a_doubleReach;
	}

	private int m_suteHaisCount;
	void setSuteHaisCount(int a_suteHaisCount) {
		this.m_suteHaisCount = a_suteHaisCount;
	}
	int getSuteHaisCount() {
		return m_suteHaisCount;
	}

	private CountFormat m_countFormat = new CountFormat();

	boolean isTenpai() {
		if (reach) {
			return true;
		}

		Hai addHai;
		for (int id = 0; id < Hai.ID_ITEM_MAX; id++) {
			addHai = new Hai(id);
			m_countFormat.setCountFormat(m_tehai, addHai);
			if (m_countFormat.getCombis(null) > 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * initialize the player.
	 *
	 * @param eventIf
	 *            EventIF
	 */
	Player(EventIf eventIf) {
		this.eventIf = eventIf;
	}

	/**
	 * initialize the player.
	 */
	void init() {
		//  I will initialize the tile hand.
		m_tehai.initialize();

		//  河を初期化
		kawa.initialize();

		// I will initialize the Reach.
		reach = false;

		m_ippatsu = false;
		m_doubleReach = false;
	}
    //一発
	public void setIppatsu(boolean a_ippatsu) {
		this.m_ippatsu = a_ippatsu;
	}

	public boolean isIppatsu() {
		return m_ippatsu;
	}

	private boolean m_ippatsu;
}
