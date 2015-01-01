package jp.sourceforge.andjong.mahjong;

/**
 * 手牌を管理
 *
 * @author Yuji Urushibara
 *
 */
public class Tehai {
	/** 純手牌の長さの最大値 */
	public final static int JYUN_TEHAI_LENGTH_MAX = 14;

	/**  副露の最大値 */
	public final static int FUURO_MAX = 4;

	/** The length of the face (3) */
	public final static int MENTSU_LENGTH_3 = 3;

	/** The length of the face (4) */
	public final static int MENTSU_LENGTH_4 = 4;

	/** 純手牌 */
	private Hai[] m_jyunTehai = new Hai[JYUN_TEHAI_LENGTH_MAX];

	/** 純手牌の長 */
	private int m_jyunTehaiLength;

	/** 副露の配列 */
	private Fuuro[] m_fuuros = new Fuuro[FUURO_MAX];

	/** 副露の個数 */
	private int m_fuuroNums;

	{
		for (int i = 0; i < JYUN_TEHAI_LENGTH_MAX; i++) {
			m_jyunTehai[i] = new Hai();
		}

		for (int i = 0; i < FUURO_MAX; i++) {
			m_fuuros[i] = new Fuuro();
		}
	}

	/**
	 * 手牌を作成
	 */
	public Tehai() {
		initialize();
	}

	/**
	 * 手牌を初期化
	 */
	public void initialize() {
		m_jyunTehaiLength = 0;
		m_fuuroNums = 0;
	}

	/**
	 * Tile hand I want to copy the
	 *
	 * @param a_dest
	 *
	 * @param a_src
	 *
	 * @param jyunTehaiCopy
	 *
	 */
	public static void copy(Tehai a_dest, Tehai a_src, boolean jyunTehaiCopy) {
		if (jyunTehaiCopy == true) {
			a_dest.m_jyunTehaiLength = a_src.m_jyunTehaiLength;
			Tehai.copyJyunTehai(a_dest.m_jyunTehai, a_src.m_jyunTehai, a_dest.m_jyunTehaiLength);
		}

		a_dest.m_fuuroNums = a_src.m_fuuroNums;
		copyFuuros(a_dest.m_fuuros, a_src.m_fuuros, a_dest.m_fuuroNums);
	}

	/**
	 * 純手牌を取得
	 *
	 * @return 純手牌
	 */
	public Hai[] getJyunTehai() {
		return m_jyunTehai;
	}

	/**
	 * �want to get the length of Juntepai
	 *
	 * @return length
	 */
	public int getJyunTehaiLength() {
		return m_jyunTehaiLength;
	}

	/**
	 * want to add a pie pure hand pie
	 *
	 * @param a_hai
	 *            追加する牌
	 * @return result
	 */
	public boolean addJyunTehai(Hai a_hai) {
		if (m_jyunTehaiLength >= JYUN_TEHAI_LENGTH_MAX) {
			return false;
		}

		int i;
		for (i = m_jyunTehaiLength; i > 0; i--) {
			if (m_jyunTehai[i - 1].getId() <= a_hai.getId()) {
				break;
			}

			Hai.copy(m_jyunTehai[i], m_jyunTehai[i - 1]);
		}

		Hai.copy(m_jyunTehai[i], a_hai);
		m_jyunTehaiLength++;

		return true;
	}

	/**
	 * I want to remove the tiles of the specified position from the pure hand pie.
	 *
	 * @param a_index
	 *
	 * @return
	 */
	public boolean rmJyunTehai(int a_index) {
		if (a_index >= JYUN_TEHAI_LENGTH_MAX) {
			return false;
		}

		for (int i = a_index; i < m_jyunTehaiLength - 1; i++) {
			Hai.copy(m_jyunTehai[i], m_jyunTehai[i + 1]);
		}

		m_jyunTehaiLength--;

		return true;
	}

	/**
	 * I want to delete the specified tile from the pure hand pie.
	 *
	 * @param a_hai
	 *            指定の牌
	 * @return
	 */
	public boolean rmJyunTehai(Hai a_hai) {
		int id = a_hai.getId();

		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				return rmJyunTehai(i);
			}
		}

		return false;
	}

	/**
	 * I want to copy the net hand pie.
	 *
	 * @param a_dest
	 *
	 * @param a_src
	 *
	 * @param a_length
	 *
	 * @return
	 */
	public static boolean copyJyunTehai(Hai[] a_dest, Hai[] a_src, int a_length) {
		if (a_length > JYUN_TEHAI_LENGTH_MAX) {
			return false;
		}

		for (int i = 0; i < a_length; i++) {
			Hai.copy(a_dest[i], a_src[i]);
		}

		return true;
	}

	/**
	 * I want to copy the tiles of the specified position of pure hand pie.
	 *
	 * @param a_hai
	 *            牌
	 * @param a_index
	 *            指定位置
	 */
	public boolean copyJyunTehaiIndex(Hai a_hai, int a_index) {
		if (a_index >= m_jyunTehaiLength) {
			return false;
		}

		Hai.copy(a_hai, m_jyunTehai[a_index]);

		return true;
	}

	/**
	 *  I want to check whether the Qi (left).
	 *
	 * @param a_suteHai
	 *
	 * @return (左)の可否
	 */
	public boolean validChiiLeft(Hai a_suteHai, Hai[] a_sarashiHais) {
		if (a_suteHai.isTsuu()) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_8) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_9) {
			return false;
		}

		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int noKindLeft = a_suteHai.getNoKind();
		int noKindCenter = noKindLeft + 1;
		int noKindRight = noKindLeft + 2;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindCenter) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						a_sarashiHais[0] = new Hai(m_jyunTehai[i]);
						a_sarashiHais[1] = new Hai(m_jyunTehai[j]);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Qi (left) I set.
	 *
	 * @param a_suteHai
	 *
	 * @param a_relation
	 *
	 * @return
	 */
	public boolean setChiiLeft(Hai a_suteHai, int a_relation) {
		Hai sarashiHais[] = new Hai[2];
		if (!validChiiLeft(a_suteHai, sarashiHais)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		hais[0] = new Hai(a_suteHai);

		int noKindLeft = a_suteHai.getNoKind();
		int noKindCenter = noKindLeft + 1;
		int noKindRight = noKindLeft + 2;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindCenter) {
				hais[1] = new Hai(m_jyunTehai[i]);

				rmJyunTehai(i);

				for (int j = i; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						hais[2] = new Hai(m_jyunTehai[j]);

						rmJyunTehai(j);

						hais[3] = new Hai();

						m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINSHUN);
						m_fuuros[m_fuuroNums].setRelation(a_relation);
						m_fuuros[m_fuuroNums].setHais(hais);
						m_fuuroNums++;
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * I want to check whether the Qi (center).
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return (中央)の可否
	 */
	public boolean validChiiCenter(Hai a_suteHai, Hai[] a_sarashiHais) {
		if (a_suteHai.isTsuu()) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_1) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_9) {
			return false;
		}

		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int noKindCenter = a_suteHai.getNoKind();
		int noKindLeft = noKindCenter - 1;
		int noKindRight = noKindCenter + 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						a_sarashiHais[0] = new Hai(m_jyunTehai[i]);
						a_sarashiHais[1] = new Hai(m_jyunTehai[j]);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Qi (the center) I set
	 *
	 * @param a_suteHai
	 *
	 * @param a_relation
	 *
	 * @return
	 */
	public boolean setChiiCenter(Hai a_suteHai, int a_relation) {
		Hai sarashiHais[] = new Hai[2];
		if (!validChiiCenter(a_suteHai, sarashiHais)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		hais[1] = new Hai(a_suteHai);

		int noKindCenter = a_suteHai.getNoKind();
		int noKindLeft = noKindCenter - 1;
		int noKindRight = noKindCenter + 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				hais[0] = new Hai(m_jyunTehai[i]);

				rmJyunTehai(i);

				for (int j = i; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindRight) {
						hais[2] = new Hai(m_jyunTehai[j]);

						rmJyunTehai(j);

						hais[3] = new Hai();

						m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINSHUN);
						m_fuuros[m_fuuroNums].setRelation(a_relation);
						m_fuuros[m_fuuroNums].setHais(hais);
						m_fuuroNums++;
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * I want to check whether the Qi (right).
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return
	 */
	public boolean validChiiRight(Hai a_suteHai, Hai[] a_sarashiHais) {
		if (a_suteHai.isTsuu()) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_1) {
			return false;
		}

		if (a_suteHai.getNo() == Hai.NO_2) {
			return false;
		}

		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int noKindRight = a_suteHai.getNoKind();
		int noKindLeft = noKindRight - 2;
		int noKindCenter = noKindRight - 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				for (int j = i + 1; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindCenter) {
						a_sarashiHais[0] = new Hai(m_jyunTehai[i]);
						a_sarashiHais[1] = new Hai(m_jyunTehai[j]);
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Qi (right) I set.
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *
	 * @return
	 */
	public boolean setChiiRight(Hai a_suteHai, int a_relation) {
		Hai sarashiHais[] = new Hai[2];
		if (!validChiiRight(a_suteHai, sarashiHais)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		hais[2] = new Hai(a_suteHai);

		int noKindRight = a_suteHai.getNoKind();
		int noKindLeft = noKindRight - 2;
		int noKindCenter = noKindRight - 1;
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (m_jyunTehai[i].getNoKind() == noKindLeft) {
				hais[0] = new Hai(m_jyunTehai[i]);

				rmJyunTehai(i);

				for (int j = i; j < m_jyunTehaiLength; j++) {
					if (m_jyunTehai[j].getNoKind() == noKindCenter) {
						hais[1] = new Hai(m_jyunTehai[j]);

						rmJyunTehai(j);

						hais[3] = new Hai();

						m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINSHUN);
						m_fuuros[m_fuuroNums].setRelation(a_relation);
						m_fuuros[m_fuuroNums].setHais(hais);
						m_fuuroNums++;
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * valid pong
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @return
	 */
	public boolean validPon(Hai a_suteHai) {
		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int id = a_suteHai.getId();
		for (int i = 0, count = 1; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				count++;
				if (count >= MENTSU_LENGTH_3) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * set Pong 設定
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *
	 * @return result
	 */
	public boolean setPon(Hai a_suteHai, int a_relation) {
		if (!validPon(a_suteHai)) {
			return false;
		}

		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		int iHais = 0;
		hais[iHais] = new Hai(a_suteHai);
		iHais++;

		int id = a_suteHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				hais[iHais] = new Hai(m_jyunTehai[i]);
				iHais++;

				rmJyunTehai(i);
				i--;

				if (iHais >= MENTSU_LENGTH_3) {
					break;
				}
			}
		}

		hais[iHais] = new Hai();

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_MINKOU);
		m_fuuros[m_fuuroNums].setRelation(a_relation);
		m_fuuros[m_fuuroNums].setHais(hais);
		m_fuuroNums++;

		return true;
	}

	/**
	 * check  槓の可否
	 *
	 * @param a_addHai
	 *            追加する牌
	 * @return  暗槓の可否
	 */
	public int validKan(Hai a_addHai, Hai[] a_kanHais) {
		if (m_fuuroNums >= FUURO_MAX) {
			return 0;
		}

		int kanCount = 0;
		int id;

		addJyunTehai(a_addHai);

		// Check of the pressurized cans
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() == Fuuro.TYPE_MINKOU) {
				for (int j = 0; j < m_jyunTehaiLength; j++) {
					if (m_fuuros[i].getHais()[0].getId() == m_jyunTehai[j].getId()) {
						a_kanHais[kanCount] = new Hai(m_jyunTehai[j]);
						kanCount++;
					}
				}
			}
		}

		id = m_jyunTehai[0].getId();
		for (int i = 1, count = 1; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				count++;
				if (count >= MENTSU_LENGTH_4) {
					a_kanHais[kanCount] = new Hai(m_jyunTehai[i]);
					kanCount++;
				}
			} else {
				id = m_jyunTehai[i].getId();
				count = 1;
			}
		}
		rmJyunTehai(a_addHai);

		return kanCount;
	}

	public boolean validDaiMinKan(Hai a_suteHai) {
		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int id = a_suteHai.getId();
		for (int i = 0, count = 1; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				count++;
				if (count >= MENTSU_LENGTH_4) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * set 大明槓を設定
	 *
	 * @param a_suteHai
	 *            捨牌
	 * @param a_relation
	 *            関係
	 * @return 結果
	 */
	public boolean setDaiMinKan(Hai a_suteHai, int a_relation) {
		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		int iHais = 0;
		hais[iHais] = new Hai(a_suteHai);
		iHais++;

		int suteHaiId = a_suteHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (suteHaiId == m_jyunTehai[i].getId()) {
				hais[iHais] = new Hai(m_jyunTehai[i]);
				iHais++;

				rmJyunTehai(i);
				i--;

				if (iHais >= MENTSU_LENGTH_4) {
					break;
				}
			}
		}

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_DAIMINKAN);
		m_fuuros[m_fuuroNums].setRelation(a_relation);
		m_fuuros[m_fuuroNums].setHais(hais);
		m_fuuroNums++;

		return true;
	}

	/**
	 * check 加槓の可否
	 *
	 * @param a_tsumoHai
	 *            Tsumo tiles
	 * @return 加槓の可否
	 */
	public boolean validKaKan(Hai a_tsumoHai) {
		if (m_fuuroNums >= FUURO_MAX) {
			return false;
		}

		int id = a_tsumoHai.getId();
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() == Fuuro.TYPE_MINKOU) {
				if (id == (m_fuuros[i].getHais())[0].getId()) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * set 加槓を設定
	 *
	 * @param a_tsumoHai
	 *            Tsumo tiles
	 * @return result
	 */
	public boolean setKaKan(Hai a_tsumoHai) {
		if (!validKaKan(a_tsumoHai)) {
			return false;
		}

		Hai hais[];

		int id = a_tsumoHai.getId();
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() == Fuuro.TYPE_MINKOU) {
				hais = m_fuuros[i].getHais();
				if (id == hais[0].getId()) {
					Hai.copy(hais[3], a_tsumoHai);
					m_fuuros[i].setType(Fuuro.TYPE_KAKAN);
					m_fuuros[i].setHais(hais);
				}
			}
		}

		return true;
	}

	/**
	 * 暗槓を設定
	 *
	 * @param a_tsumoHai
	 *            Tsumo tiles
	 * @param a_relation
	 *            関係
	 * @return result
	 */
	public boolean setAnKan(Hai a_tsumoHai, int a_relation) {
		Hai fuuroHais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		int id = a_tsumoHai.getId();
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() == Fuuro.TYPE_MINKOU) {
				fuuroHais = m_fuuros[i].getHais();
				if (id == fuuroHais[0].getId()) {
					Hai.copy(fuuroHais[3], a_tsumoHai);
					rmJyunTehai(a_tsumoHai);
					m_fuuros[i].setType(Fuuro.TYPE_KAKAN);
					m_fuuros[i].setHais(fuuroHais);
					//m_fuuroNums++;

					return true;
				}
			}
		}

		int iHais = 0;
		Hai hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

		//id = a_tsumoHai.getId();
		for (int i = 0; i < m_jyunTehaiLength; i++) {
			if (id == m_jyunTehai[i].getId()) {
				hais[iHais] = new Hai(m_jyunTehai[i]);
				iHais++;

				rmJyunTehai(i);
				i--;

				if (iHais >= MENTSU_LENGTH_4) {
					break;
				}
			}
		}

		m_fuuros[m_fuuroNums].setType(Fuuro.TYPE_ANKAN);
		m_fuuros[m_fuuroNums].setRelation(a_relation);
		m_fuuros[m_fuuroNums].setHais(hais);
		m_fuuroNums++;

		return true;
	}

	/**
	 * 副露の配列を取得
	 *
	 * @return 副露の配列
	 */
	public Fuuro[] getFuuros() {
		return m_fuuros;
	}

	/**
	 * 副露の個数を取得
	 *
	 * @return 副露の個数
	 */
	public int getFuuroNum() {
		return m_fuuroNums;
	}

	public boolean isNaki() {
		for (int i = 0; i < m_fuuroNums; i++) {
			if (m_fuuros[i].getType() != Fuuro.TYPE_ANKAN) {
				return true;
			}
		}

		return false;
	}

	/**
	 * copy 副露の配列
	 *
	 * @param a_dest
	 *            dest of 副露の配列
	 * @param a_src
	 *           src of 副露の配列
	 * @param a_length
	 *            length
	 * @return result
	 */
	public static boolean copyFuuros(Fuuro[] a_dest, Fuuro[] a_src, int a_length) {
		if (a_length > FUURO_MAX) {
			return false;
		}

		for (int i = 0; i < a_length; i++) {
			Fuuro.copy(a_dest[i], a_src[i]);
		}

		return true;
	}
}
