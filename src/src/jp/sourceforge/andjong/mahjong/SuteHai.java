package jp.sourceforge.andjong.mahjong;

/**
 * 捨牌を管理
 *
 * @author Yuji Urushibara
 *
 */
public class SuteHai extends Hai {
	/** Squeal flag */
	private boolean m_naki = false;

	/** Reach flag */
	private boolean m_reach = false;

	/**Mess around flag */
	private boolean m_tedashi = false;

	/**
	 * 捨牌を作成
	 */
	public SuteHai() {
		super();
	}

	/**
	 * I want to create a discarded tile from ID.
	 *
	 * @param a_id
	 *            ID
	 */
	public SuteHai(int a_id) {
		super(a_id);
	}

	/**
	 * I want to create a discarded tile from pie.
	 *
	 * @param a_hai
	 *            �v
	 */
	public SuteHai(Hai a_hai) {
		super(a_hai);
	}

	/**
	 * Discarded tile I want to copy the.
	 *
	 * @param a_dest
	 *
	 * @param a_src
	 *
	 */
	public static void copy(SuteHai a_dest, SuteHai a_src) {
		Hai.copy(a_dest, a_src);
		a_dest.m_naki = a_src.m_naki;
		a_dest.m_reach = a_src.m_reach;
		a_dest.m_tedashi = a_src.m_tedashi;
	}

	/**
	 * �̔v���R�s�[����B
	 *
	 * @param a_dest
	 *            �R�s�[��̎̔v
	 * @param a_src
	 *            �R�s�[���̎̔v
	 */
	public static void copy(SuteHai a_dest, Hai a_src) {
		Hai.copy(a_dest, a_src);
		a_dest.m_naki = false;
		a_dest.m_reach = false;
		a_dest.m_tedashi = false;
	}

	/**
	 * set the squeal flag.
	 *
	 * @param a_naki
	 *           Squeal flag
	 */
	public void setNaki(boolean a_naki) {
		this.m_naki = a_naki;
	}

	/**
	 * Squeal flag I get.
	 *
	 * @return Squeal flag
	 */
	public boolean isNaki() {
		return m_naki;
	}

	/**
	 * set the reach flag
	 *
	 * @param a_reach
	 *            reach flag
	 */
	public void setReach(boolean a_reach) {
		this.m_reach = a_reach;
	}

	/**
	 *
	 *
	 * @return
	 */
	public boolean isReach() {
		return m_reach;
	}

	/**
	 * set 手出しフラグを取得
	 *
	 * @param a_tedashi
	 *
	 */
	public void setTedashi(boolean a_tedashi) {
		this.m_tedashi = a_tedashi;
	}

	/**
	 * The mess around flag I get
	 *
	 * @return
	 */
	public boolean isTedashi() {
		return m_tedashi;
	}
}
