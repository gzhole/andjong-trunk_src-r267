package jp.sourceforge.andjong.mahjong;

import java.util.Random;

import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.Settings;

/**
 * Mountain I will manage.
 *
 * @author Yuji Urushibara
 *
 */
public class Yama {
    //The maximum number of array of Yamapai
	private int YAMA_HAIS_MAX = 136;

	/** The maximum number of array of Tsumo tile * / */
    //private final static int TSUMO_HAIS_MAX = 122;
    private int TSUMO_HAIS_MAX;

    /** The maximum number of array of Rinshan tiles */
    //private final static int RINSHAN_HAIS_MAX = 4;

    private int RINSHAN_HAIS_MAX;

    /** The maximum number of sequence of each Dora tiles */
    public int DORA_HAIS_MAX;

    /** Array of Yamapai */
    private Hai[] m_yamaHais;

    /** Array of Tsumo tile*/
    private Hai[] m_tsumoHais;

    /**  Array of Rinshan tiles  */
    private Hai[] m_rinshanHais;

    /** position of iRinshanHai */
    private int m_iRinshanHais;

    /** Index of Tsumo tile */
    private int m_iTsumoHais;

    /** Table Dora array of tiles */
    private Hai[] m_omoteDoraHais ;

    /**  Array of back Dora tiles */
    private Hai[] m_uraDoraHais ;
	/**
	 * I want to create a mountain.
	 */
	/*private Yama() {

		setTsumoHaisStartIndex(0);
	}*/

    private String style ="hongkong";
    Yama(String style) {
        this.style = style;
        YAMA_HAIS_MAX = 136;
        if (style.equalsIgnoreCase("hongkong")) {
            this.TSUMO_HAIS_MAX = 136;
            this.RINSHAN_HAIS_MAX = 0;
            /** The maximum number of sequence of each Dora tiles */
            DORA_HAIS_MAX = 0;
            /** Table Dora array of tiles */
            m_omoteDoraHais = new Hai[0];

            /**  Array of back Dora tiles */
            m_uraDoraHais = new Hai[0];
        } else if (style.equalsIgnoreCase("japan")){
            this.TSUMO_HAIS_MAX = 122;
            this.RINSHAN_HAIS_MAX = 4;
            /** The maximum number of sequence of each Dora tiles */
            DORA_HAIS_MAX = RINSHAN_HAIS_MAX + 1;
            /** Table Dora array of tiles */
            m_omoteDoraHais = new Hai[DORA_HAIS_MAX];

            /**  Array of back Dora tiles */
            m_uraDoraHais = new Hai[DORA_HAIS_MAX];
        }


        /** Array of Yamapai */
        m_yamaHais = new Hai[YAMA_HAIS_MAX];

        /** Array of Tsumo tile*/
        m_tsumoHais = new Hai[TSUMO_HAIS_MAX];

        /**  Array of Rinshan tiles  */
        m_rinshanHais = new Hai[RINSHAN_HAIS_MAX];




        {
            for (int i = Hai.ID_WAN_1; i < Hai.ID_ITEM_MAX; i++) {
                for (int j = 0; j < 4; j++) {
                    m_yamaHais[(i * 4) + j] = new Hai(i);
                }
            }
        }
        setTsumoHaisStartIndex(0);
    }
	/**
	 * Shuffle
	 */
	void xipai() {
		Random random = new Random();
		Hai temp;

		for (int i = 0, j; i < YAMA_HAIS_MAX; i++) {
			j = random.nextInt(YAMA_HAIS_MAX);
			temp = m_yamaHais[i];
			m_yamaHais[i] = m_yamaHais[j];
			m_yamaHais[j] = temp;
		}
	}

	/**
	 * Tsumo I get the pie
	 *
	 * @return Tsumo tile
	 */
	Hai tsumo() {
		if (m_iTsumoHais >= (TSUMO_HAIS_MAX - m_iRinshanHais)) {
			return null;
		}

		Hai tsumoHai = new Hai(m_tsumoHais[m_iTsumoHais]);
		m_iTsumoHais++;

		return tsumoHai;
	}

	/**
	 * Rinshan I get the pie.
	 *
	 * @return ri nn nnャSilicone brand
	 */
	Hai rinshanTsumo() {
		if (m_iRinshanHais >= RINSHAN_HAIS_MAX) {
			return null;
		}

		Hai rinshanHai = new Hai(m_rinshanHais[m_iRinshanHais]);
		m_iRinshanHais++;

		return rinshanHai;
	}

	/**
	 * I want to get an array of table Dora.
	 *
	 * @return Table Dora array of
	 */
	Hai[] getOmoteDoraHais() {
        if (style.equalsIgnoreCase("japan")) {
            int omoteDoraHaisLength = m_iRinshanHais + 1;
            Hai[] omoteDoraHais = new Hai[omoteDoraHaisLength];

            for (int i = 0; i < omoteDoraHaisLength; i++) {
                omoteDoraHais[i] = new Hai(this.m_omoteDoraHais[i]);
            }

            return omoteDoraHais;
            }
        return new Hai[0];
	}

	/**
	 * I want to get an array of back Dora.
	 *
	 * @return back Dora array of
	 */
	Hai[] getUraDoraHais() {
        if (style.equalsIgnoreCase("japan")) {
            int uraDoraHaisLength = m_iRinshanHais + 1;
            Hai[] uraDoraHais = new Hai[uraDoraHaisLength];

            for (int i = 0; i < uraDoraHaisLength; i++) {
                uraDoraHais[i] = new Hai(this.m_uraDoraHais[i]);
            }

            return uraDoraHais;
        }
        return new Hai[0];
	}

	Hai[] getAllDoraHais() {
		int omoteDoraHaisLength = m_iRinshanHais + 1;
		int uraDoraHaisLength = m_iRinshanHais + 1;
		int allDoraHaisLength = omoteDoraHaisLength + uraDoraHaisLength;
		Hai[] allDoraHais = new Hai[allDoraHaisLength];

		for (int i = 0; i < omoteDoraHaisLength; i++) {
			allDoraHais[i] = new Hai(this.m_omoteDoraHais[i]);
		}

		for (int i = 0; i < uraDoraHaisLength; i++) {
			allDoraHais[omoteDoraHaisLength + i] = new Hai(this.m_uraDoraHais[i]);
		}

		return allDoraHais;
	}

	/**
	 * Tsumo I set the start position of the pie
	 *
	 * @param a_tsumoHaiStartIndex
	 *            Tsumo start position of the tiles
	 */
	boolean setTsumoHaisStartIndex(int a_tsumoHaiStartIndex) {
		if (a_tsumoHaiStartIndex >= YAMA_HAIS_MAX) {
			return false;
		}

		int yamaHaisIdx = a_tsumoHaiStartIndex;

		for (int i = 0; i < TSUMO_HAIS_MAX; i++) {
			m_tsumoHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		m_iTsumoHais = 0;

		for (int i = 0; i < RINSHAN_HAIS_MAX; i++) {
			m_rinshanHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		m_iRinshanHais = 0;

		for (int i = 0; i < DORA_HAIS_MAX; i++) {
			m_omoteDoraHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}

			m_uraDoraHais[i] = m_yamaHais[yamaHaisIdx];

			yamaHaisIdx++;
			if (yamaHaisIdx >= YAMA_HAIS_MAX) {
				yamaHaisIdx = 0;
			}
		}

		return true;
	}

	/**
	 * Tsumo I get the remaining number of tiles.
	 *
	 * @return Tsumo remaining number of tiles
	 */
	int getTsumoNokori() {
		return TSUMO_HAIS_MAX - m_iTsumoHais - m_iRinshanHais;
	}

	/**
	 * set the red Dora
	 *
	 * @param a_id
	 *            ID
	 * @param a_num
	 *            Number
	 */
	void setRedDora(int a_id, int a_num) {
		if (a_num <= 0) {
			return;
		}

		for (int i = 0; i < m_yamaHais.length; i++) {
			if (m_yamaHais[i].getId() == a_id) {
				m_yamaHais[i].setRed(true);
				a_num--;
				if (a_num <= 0) {
					break;
				}
			}
		}
	}
}
