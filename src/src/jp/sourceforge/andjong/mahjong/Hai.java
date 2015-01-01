package jp.sourceforge.andjong.mahjong;

/**
 * I manage the pie.
 *
 * @author Yuji Urushibara
 *
 */
public class Hai {
	/*
	 * ID
	 */


	public final static int ID_WAN_1 = 0;

	public final static int ID_WAN_2 = 1;

	public final static int ID_WAN_3 = 2;

	public final static int ID_WAN_4 = 3;

	public final static int ID_WAN_5 = 4;

	public final static int ID_WAN_6 = 5;

	public final static int ID_WAN_7 = 6;

	public final static int ID_WAN_8 = 7;

	public final static int ID_WAN_9 = 8;

	/** Tong */
	public final static int ID_PIN_1 = 9;

	public final static int ID_PIN_2 = 10;

	public final static int ID_PIN_3 = 11;

	public final static int ID_PIN_4 = 12;

	public final static int ID_PIN_5 = 13;

	public final static int ID_PIN_6 = 14;

	public final static int ID_PIN_7 = 15;

	public final static int ID_PIN_8 = 16;

	public final static int ID_PIN_9 = 17;

	/** suo */
	public final static int ID_SOU_1 = 18;

	public final static int ID_SOU_2 = 19;

	public final static int ID_SOU_3 = 20;

	public final static int ID_SOU_4 = 21;

	public final static int ID_SOU_5 = 22;

	public final static int ID_SOU_6 = 23;

	public final static int ID_SOU_7 = 24;

	public final static int ID_SOU_8 = 25;

	public final static int ID_SOU_9 = 26;


	public final static int ID_TON = 27;

	public final static int ID_NAN = 28;

	public final static int ID_SHA = 29;

	public final static int ID_PE = 30;

	/** White */
	public final static int ID_HAKU = 31;
	/** Fa */
	public final static int ID_HATSU = 32;
	/** Zhong */
	public final static int ID_CHUN = 33;

	/**  Maximum value of ID */
	public final static int ID_MAX = ID_CHUN;

	/**  Maximum value of ID +1 */
	public final static int ID_ITEM_MAX = ID_MAX + 1;

	/*
	 * NO
	 */

	/** 1 */
	public final static int NO_1 = 1;
	/** 2 */
	public final static int NO_2 = 2;
	/** 3 */
	public final static int NO_3 = 3;
	/** 4 */
	public final static int NO_4 = 4;
	/** 5 */
	public final static int NO_5 = 5;
	/** 6 */
	public final static int NO_6 = 6;
	/** 7 */
	public final static int NO_7 = 7;
	/** 8 */
	public final static int NO_8 = 8;
	/** 9 */
	public final static int NO_9 = 9;


	public final static int NO_WAN_1 = 1;

	public final static int NO_WAN_2 = 2;

	public final static int NO_WAN_3 = 3;

	public final static int NO_WAN_4 = 4;

	public final static int NO_WAN_5 = 5;

	public final static int NO_WAN_6 = 6;

	public final static int NO_WAN_7 = 7;

	public final static int NO_WAN_8 = 8;

	public final static int NO_WAN_9 = 9;


	public final static int NO_PIN_1 = 1;

	public final static int NO_PIN_2 = 2;

	public final static int NO_PIN_3 = 3;

	public final static int NO_PIN_4 = 4;

	public final static int NO_PIN_5 = 5;

	public final static int NO_PIN_6 = 6;

	public final static int NO_PIN_7 = 7;

	public final static int NO_PIN_8 = 8;

	public final static int NO_PIN_9 = 9;


	public final static int NO_SOU_1 = 1;

	public final static int NO_SOU_2 = 2;

	public final static int NO_SOU_3 = 3;

	public final static int NO_SOU_4 = 4;

	public final static int NO_SOU_5 = 5;

	public final static int NO_SOU_6 = 6;

	public final static int NO_SOU_7 = 7;

	public final static int NO_SOU_8 = 8;

	public final static int NO_SOU_9 = 9;


	public final static int NO_TON = 1;

	public final static int NO_NAN = 2;

	public final static int NO_SHA = 3;

	public final static int NO_PE = 4;


	public final static int NO_HAKU = 1;

	public final static int NO_HATSU = 2;

	public final static int NO_CHUN = 3;

	/*
	 * Type
	 */

	/** Wang*/
	public final static int KIND_WAN = 0x00000010;
	/** Tong */
	public final static int KIND_PIN = 0x00000020;
	/** Suo*/
	public final static int KIND_SOU = 0x00000040;
	/** Shu pai */
	public final static int KIND_SHUU = KIND_WAN | KIND_PIN | KIND_SOU;
	/** Feng Pai */
	public final static int KIND_FON = 0x00000100;
	/** San ruan pai*/
	public final static int KIND_SANGEN = 0x00000200;
	/** Zi pai*/
	public final static int KIND_TSUU = KIND_FON | KIND_SANGEN;

	/** Array of number */
	private final static int[] NOS = {
	//
	NO_WAN_1, NO_WAN_2, NO_WAN_3, NO_WAN_4, NO_WAN_5, NO_WAN_6, NO_WAN_7, NO_WAN_8, NO_WAN_9,
	//
	NO_PIN_1, NO_PIN_2, NO_PIN_3, NO_PIN_4, NO_PIN_5, NO_PIN_6, NO_PIN_7, NO_PIN_8, NO_PIN_9,
	//
	NO_SOU_1, NO_SOU_2, NO_SOU_3, NO_SOU_4, NO_SOU_5, NO_SOU_6, NO_SOU_7, NO_SOU_8, NO_SOU_9,
	//
	NO_TON, NO_NAN, NO_SHA, NO_PE,
	//
	NO_HAKU, NO_HATSU, NO_CHUN };

	/** Kind of array */
	private final static int[] KINDS = {

	KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN, KIND_WAN,

	KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN, KIND_PIN,

	KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU, KIND_SOU,

	KIND_FON, KIND_FON, KIND_FON, KIND_FON,

	KIND_SANGEN, KIND_SANGEN, KIND_SANGEN };

	/** one and nine pai */
	private final static boolean[] IS_ICHIKYUUS = {
	// Wan zi
	true, false, false, false, false, false, false, false, true,
	// Tong zi
	true, false, false, false, false, false, false, false, true,
	//Suo zi
	true, false, false, false, false, false, false, false, true,
	// Feng pai
	false, false, false, false,
	// San ruan (zhong fa bai)
	false, false, false };

	/** Zi Pai Pei lie */
	private final static boolean[] IS_TSUUS = {
	// Wan zi
	false, false, false, false, false, false, false, false, false,
	//  Tong zi
	false, false, false, false, false, false, false, false, false,
	// Suo zi
	false, false, false, false, false, false, false, false, false,
	// Feng pai
	true, true, true, true,
	// San ruan (zhong fa bai)
	true, true, true };

	/** Array of ID of the next tile */
	private final static int[] NEXT_HAI_IDS = {

	ID_WAN_2, ID_WAN_3, ID_WAN_4, ID_WAN_5, ID_WAN_6, ID_WAN_7, ID_WAN_8, ID_WAN_9, ID_WAN_1,

	ID_PIN_2, ID_PIN_3, ID_PIN_4, ID_PIN_5, ID_PIN_6, ID_PIN_7, ID_PIN_8, ID_PIN_9, ID_PIN_1,

	ID_SOU_2, ID_SOU_3, ID_SOU_4, ID_SOU_5, ID_SOU_6, ID_SOU_7, ID_SOU_8, ID_SOU_9, ID_SOU_1,

	ID_NAN, ID_SHA, ID_PE, ID_TON,

	ID_HATSU, ID_CHUN, ID_HAKU };

	/** ID */
	private int m_id;

	/**
	 *  I want to create an empty pie.
	 */
	public Hai() {
	}

	/**
	 * I want to create a tile from ID.
	 *
	 * @param a_id
	 *            ID
	 */
	public Hai(int a_id) {
		this.m_id = a_id;
		this.m_red = false;
	}

	/**
	 * I want to create a tile from the ID and red Dora.
	 *
	 * @param a_id
	 *            ID
	 * @param a_red
	 *            Red Dora
	 */
	public Hai(int a_id, boolean a_red) {
		this.m_id = a_id;
		this.m_red = a_red;
	}

	/**
	 * I want to create a tile from the tile.
	 *
	 * @param a_hai
	 *            Brand
	 */
	public Hai(Hai a_hai) {
		copy(this, a_hai);
	}

	/**
	 * I want to copy the pie.
	 *
	 * @param a_dest
	 *
	 * @param a_src
	 *
	 */
	public static void copy(Hai a_dest, Hai a_src) {
		a_dest.m_id = a_src.m_id;
		a_dest.m_red = a_src.m_red;
	}

	/**
	 *
	 *
	 * @return ID
	 */
	public int getId() {
		return m_id;
	}

	/**
	 * get the number.
	 *
	 * @return number
	 */
	public int getNo() {
		return NOS[m_id];
	}

	/**
	 * get the type.
	 *
	 * @return type
	 */
	public int getKind() {
		return KINDS[m_id];
	}

	/**
	 *  get the NK (number and type of OR).
	 *
	 * @return NK (number and type of OR)
	 */
	public int getNoKind() {
		return NOS[m_id] | KINDS[m_id];
	}

	/**
	 * is one or nine pai
	 *
	 * @return is one or nine pai
	 */
	public boolean isIchikyuu() {
		return IS_ICHIKYUUS[m_id];
	}

	/**
	 * get the letter tiles flag
	 *
	 * @return plate fu gu ra
	 */
	public boolean isTsuu() {
		return IS_TSUUS[m_id];
	}

	/**
	 * get the letter tiles flag
	 *
	 * @param a_noKind
	 *            NK (number and type of OR)
	 * @return plate fu gu ra
	 */
	public static boolean isTsuu(int a_noKind) {
		return (a_noKind & KIND_TSUU) != 0;
	}

	/**
	 * one or nine pai
	 *
	 * @return one or nine pai
	 */
	public boolean isYaochuu() {
		return IS_ICHIKYUUS[m_id] | IS_TSUUS[m_id];
	}

	/**
	 * get the ID of the next pie.
	 *
	 * @return ID of the next tile
	 */
	public int getNextHaiId() {
		return NEXT_HAI_IDS[m_id];
	}

	/**
	 * I want to convert the NK (number and type of OR) to ID.
	 *
	 * @param a_noKind
	 *             NK (number and type of OR)
	 * @return ID
	 */
	public static int noKindToId(int a_noKind) {
		int id;
		if (a_noKind >= KIND_SANGEN) {
			id = a_noKind - KIND_SANGEN + ID_HAKU - 1;
		} else if (a_noKind >= KIND_FON) {
			id = a_noKind - KIND_FON + ID_TON - 1;
		} else if (a_noKind >= KIND_SOU) {
			id = a_noKind - KIND_SOU + ID_SOU_1 - 1;
		} else if (a_noKind >= KIND_PIN) {
			id = a_noKind - KIND_PIN + ID_PIN_1 - 1;
		} else {
			id = a_noKind - KIND_WAN + ID_WAN_1 - 1;
		}
		return id;
	}

	/**  Red Dora */
	private boolean m_red;

	/**
	 * set the red Dora.
	 *
	 * @param a_red
	 *             Red Dora
	 */
	public void setRed(boolean a_red) {
		this.m_red = a_red;
	}

	/**
	 * get the red Dora.
	 *
	 * @return red Dora
	 */
	public boolean isRed() {
		return m_red;
	}
}
