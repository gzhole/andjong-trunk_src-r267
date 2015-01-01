package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.Hai.ID_CHUN;
import static jp.sourceforge.andjong.mahjong.Hai.ID_HAKU;
import static jp.sourceforge.andjong.mahjong.Hai.ID_HATSU;
import static jp.sourceforge.andjong.mahjong.Hai.ID_NAN;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PE;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PIN_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_PIN_9;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SHA;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SOU_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_SOU_9;
import static jp.sourceforge.andjong.mahjong.Hai.ID_TON;
import static jp.sourceforge.andjong.mahjong.Hai.ID_WAN_1;
import static jp.sourceforge.andjong.mahjong.Hai.ID_WAN_9;

/**
 * This is a class that manages the count format.
 *
 * @author Yuji Urushibara
 *
 */
public class CountFormat {
	/**
	 * This is a class that manages the count.
	 *
	 * @author Yuji Urushibara
	 *
	 */
	public static class Count {
		/** NK */
		public int m_noKind = 0;

		/** �� */
		public int m_num = 0;

		/**
		 * �J�E���g��������B
		 */
		public void initialize() {
			m_noKind = 0;
			m_num = 0;
		}
	}

	/**
	 * It is a combination of rising class.
	 *
	 * @author Yuji Urushibara
	 *
	 */
	public static class Combi {
		/** Head of NK */
		public int m_atamaNoKind = 0;

		/** Array of NK of Junko */
		public int[] m_shunNoKinds = new int[4];
		/** Number of valid sequences of NK Junko */
		public int m_shunNum = 0;

		/** NK array of  Kezi */
		public int[] m_kouNoKinds = new int[4];
		/** Number of valid sequences of NK Koku-ko*/
		public int m_kouNum = 0;

		/**
		 * CCombi I want to copy the.
		 *
		 * @param a_dest
		 *            Destination Combi
		 * @param a_src
		 *            Copy of the Combi
		 */
		public static void copy(Combi a_dest, Combi a_src) {
			a_dest.m_atamaNoKind = a_src.m_atamaNoKind;

			a_dest.m_shunNum = a_src.m_shunNum;
			for (int i = 0; i < a_dest.m_shunNum; i++) {
				a_dest.m_shunNoKinds[i] = a_src.m_shunNoKinds[i];
			}

			a_dest.m_kouNum = a_src.m_kouNum;
			for (int i = 0; i < a_dest.m_kouNum; i++) {
				a_dest.m_kouNoKinds[i] = a_src.m_kouNoKinds[i];
			}
		}
	}

	/**
	 * This is a class that manages an array of combination of rise.
	 *
	 * @author Yuji Urushibara
	 *
	 */
	private static class CombiManage {
		/** The maximum value of an array of combinations of up */
		public final static int COMBI_MAX = 10;

		/** Array of combination of up */
		public Combi[] m_combis = new Combi[COMBI_MAX];
		/** Effective number of sequences of combinations of up  */
		public int m_combiNum = 0;

		/** Remaining number of array of count */
		public int m_remain = 0;

		/**  Work area*/
		public Combi m_work = new Combi();

		{
			for (int i = 0; i < m_combis.length; i++) {
				m_combis[i] = new Combi();
			}
		}

		/**
		 *  I want to initialize the work area.
		 *
		 * @param a_remain
		 *            Remaining number of count of array
		 */
		public void initialize(int a_remain) {
			m_combiNum = 0;
			this.m_remain = a_remain;
			m_work.m_atamaNoKind = 0;
			m_work.m_shunNum = 0;
			m_work.m_kouNum = 0;
		}

		/**
		 * I want to add a combination of rise.
		 */
		public void add() {
			Combi.copy(m_combis[m_combiNum++], m_work);
		}
	}

	/**  The maximum value of the count */
	public static final int COUNT_MAX = 14 + 2;

	/** Array of count*/
	public Count[] m_counts;
	/** Valid number sequence count */
	public int m_countNum;

	/**  Manage an array of combinations of up */
	private CombiManage m_combiManage = new CombiManage();

	{
		m_counts = new Count[COUNT_MAX];
		for (int i = 0; i < COUNT_MAX; i++) {
			m_counts[i] = new Count();
		}
	}

	/**
	 * I get the total length of the count of the array.
	 *
	 * @return the sum of the lengths of the count of the array
	 */
	private int getTotalCountLength() {
		int totalCountLength = 0;

		for (int i = 0; i < m_countNum; i++) {
			totalCountLength += m_counts[i].m_num;
		}

		return totalCountLength;
	}

	/**
	 * I set the count format.
	 *
	 * @param a_tehai
	 *            Hand
	 * @param a_addHai
	 *           Tiles you want to add
	 */
	public void setCountFormat(Tehai a_tehai, Hai a_addHai) {
		for (int i = 0; i < m_counts.length; i++) {
			m_counts[i].initialize();
		}
		m_countNum = 0;

		int addHaiNoKind = 0;
		boolean set = true;
		if (a_addHai != null) {
			addHaiNoKind = a_addHai.getNoKind();
			set = false;
		}

		int jyunTehaiNoKind;
		int jyunTehaiLength = a_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength;) {
			jyunTehaiNoKind = (a_tehai.getJyunTehai())[i].getNoKind();

			if (!set && (jyunTehaiNoKind > addHaiNoKind)) {
				set = true;
				m_counts[m_countNum].m_noKind = addHaiNoKind;
				m_counts[m_countNum].m_num = 1;
				m_countNum++;
				continue;
			}

			m_counts[m_countNum].m_noKind = jyunTehaiNoKind;
			m_counts[m_countNum].m_num = 1;

			if (!set && (jyunTehaiNoKind == addHaiNoKind)) {
				set = true;
				m_counts[m_countNum].m_num++;
			}

			while (++i < jyunTehaiLength) {
				if (jyunTehaiNoKind == (a_tehai.getJyunTehai())[i].getNoKind()) {
					m_counts[m_countNum].m_num++;
				} else {
					break;
				}
			}

			m_countNum++;
		}

		if (!set) {
			m_counts[m_countNum].m_noKind = addHaiNoKind;
			m_counts[m_countNum].m_num = 1;
			m_countNum++;
		}

		for (int i = 0; i < m_countNum; i++) {
			if (m_counts[i].m_num > 4) {
				// Fifth of additional tiles I be invalid.
				m_counts[i].m_num--;
			}
		}
	}

	/**
	 * I want to get an array of combination of rise.
	 *
	 * @param a_combis
	 *            Rise array of combination of
	 * @return
	 */
	public int getCombis(Combi[] a_combis) {
//	public int getCombis(Combi[] a_combis) {
		m_combiManage.initialize(getTotalCountLength());
		searchCombi(0);
		//a_combis = m_combiManage.m_combis;
		if (m_combiManage.m_combiNum == 0) {
			m_chiitoitsu = checkChiitoitsu();
			if (m_chiitoitsu) {
				m_combiManage.m_combiNum = 1;
			} else {
				m_kokushi = checkKokushi();
				if (m_kokushi) {
					m_combiManage.m_combiNum = 1;
				}
			}
		}
		return m_combiManage.m_combiNum;
	}

	public Combi[] getCombis() {
		return m_combiManage.m_combis;
	}

	public int getCombiNum() {
		return m_combiManage.m_combiNum;
	}

	private boolean m_chiitoitsu;
	public boolean isChiitoitsu() {
		return m_chiitoitsu;
	}

	private boolean checkChiitoitsu() {
		int count = 0;
		for (int i = 0; i < m_countNum; i++) {
			if (m_counts[i].m_num == 2) {
				count++;
			} else {
				return false;
			}
		}

		if (count == 7) {
			return true;
		}
		return false;
	}

	private boolean m_kokushi;
	public boolean isKokushi() {
		return m_kokushi;
	}

	private boolean checkKokushi() {
		// Array to determine the number of tiles (address 0 is not used)
		int checkId[] = {ID_WAN_1,ID_WAN_9,ID_PIN_1,ID_PIN_9,ID_SOU_1,ID_SOU_9,
								ID_TON,ID_NAN,ID_SHA,ID_PE,ID_HAKU,ID_HATSU,ID_CHUN};
		int countHai[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};

		//   I find the ID of the tile hand
		for(int i = 0 ; i < m_countNum ; i++){
			for(int j = 0 ; j < checkId.length ; j++){
				if(Hai.noKindToId(m_counts[i].m_noKind) == checkId[j]){
					countHai[j] = m_counts[i].m_num;
				}
			}
		}

		boolean atama = false;
		//Kokushimuso it is checked whether you are satisfied (tile hand have all 1.9 characters tiles all of 1,9 characters pi
		for(int i = 0 ; i < countHai.length ; i++){
			// Not satisfied if there is a // 0 tiles
			if(countHai[i] == 0){
				return false;
			}
			if(countHai[i] == 2){
				atama = true;
			}
		}
		//Satisfied if they meet the conditions
		if (atama) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A combination of up recursively I Find.
	 *
	 * @param a_iSearch
	 *            DOCTOR position
	 */
	private void searchCombi(int a_iSearch) {
		// I want to update the search position.
		for (; a_iSearch < m_countNum; a_iSearch++) {
			if (m_counts[a_iSearch].m_num > 0) {
				break;
			}
		}

		if (a_iSearch >= m_countNum) {
			return;
		}

		//  I want to check the head.
		if (m_combiManage.m_work.m_atamaNoKind == 0) {
			if (m_counts[a_iSearch].m_num >= 2) {
				// I want to confirm the head.
				m_counts[a_iSearch].m_num -= 2;
				m_combiManage.m_remain -= 2;
				m_combiManage.m_work.m_atamaNoKind = m_counts[a_iSearch].m_noKind;

				// Add If you find a combination of rise.
				if (m_combiManage.m_remain <= 0) {
					m_combiManage.add();
				} else {
					searchCombi(a_iSearch);
				}

				// I return the definite head.
				m_counts[a_iSearch].m_num += 2;
				m_combiManage.m_remain += 2;
				m_combiManage.m_work.m_atamaNoKind = 0;
			}
		}

		// I Check Junko (shun zi)
		int left = a_iSearch;
		int center = a_iSearch + 1;
		int right = a_iSearch + 2;
		if (!Hai.isTsuu(m_counts[left].m_noKind)) {
			if ((m_counts[left].m_noKind + 1 == m_counts[center].m_noKind) && (m_counts[center].m_num > 0)) {
				if ((m_counts[left].m_noKind + 2 == m_counts[right].m_noKind) && (m_counts[right].m_num > 0)) {
					// I want to confirm the Junko. shun zi
					m_counts[left].m_num--;
					m_counts[center].m_num--;
					m_counts[right].m_num--;
					m_combiManage.m_remain -= 3;
					m_combiManage.m_work.m_shunNoKinds[m_combiManage.m_work.m_shunNum] = m_counts[left].m_noKind;
					m_combiManage.m_work.m_shunNum++;

					// Add If you find a combination of rise.
					if (m_combiManage.m_remain <= 0) {
						m_combiManage.add();
					} else {
						searchCombi(a_iSearch);
					}

					// I return defined the Junko.
					m_counts[left].m_num++;
					m_counts[center].m_num++;
					m_counts[right].m_num++;
					m_combiManage.m_remain += 3;
					m_combiManage.m_work.m_shunNum--;
				}
			}
		}

		//  I check the Koku-ko. (Ke zi)
		if (m_counts[a_iSearch].m_num >= 3) {
			// I want to confirm the Koku-ko.
			m_counts[a_iSearch].m_num -= 3;
			m_combiManage.m_remain -= 3;
			m_combiManage.m_work.m_kouNoKinds[m_combiManage.m_work.m_kouNum] = m_counts[a_iSearch].m_noKind;
			m_combiManage.m_work.m_kouNum++;

			// Add If you find a combination of rise.
			if (m_combiManage.m_remain <= 0) {
				m_combiManage.add();
			} else {
				searchCombi(a_iSearch);
			}

			// I return the defined Koku-ko.
			m_combiManage.m_remain += 3;
			m_counts[a_iSearch].m_num += 3;
			m_combiManage.m_work.m_kouNum--;
		}
	}
}
