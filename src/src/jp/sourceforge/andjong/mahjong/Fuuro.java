package jp.sourceforge.andjong.mahjong;

/**
 * Sub dew I manage
 *
 * @author Yuji Urushibara
 *
 */
public class Fuuro {
	/*
	 * Type
	 */

	/**  Type Ming shun */
	public static final int TYPE_MINSHUN = 0;
	/** Ming Ke */
	public static final int TYPE_MINKOU = 1;
	/** DAI MIN KAN  */
	public static final int TYPE_DAIMINKAN = 2;
	/** Jia KAN */
	public static final int TYPE_KAKAN = 3;
	/** AN KAN */
	public static final int TYPE_ANKAN = 4;

	/** TYPE */
	private int m_type;

	/** relation with others */
	private int m_relation;

	/**  Configuration tiles */
	private Hai m_hais[] = new Hai[Mahjong.MENTSU_HAI_MEMBERS_4];

	{
		for (int i = 0; i < m_hais.length; i++) {
			m_hais[i] = new Hai();
		}
	}

	/**
	 * I set the type.
	 *
	 * @param a_type
	 *            type
	 */
	public void setType(int a_type) {
		this.m_type = a_type;
	}

	/**
	 * get the type
	 *
	 * @return type
	 */
	public int getType() {
		return m_type;
	}

	/**
	 * relationship
	 *
	 * @param a_relation
	 *            relationship
	 */
	public void setRelation(int a_relation) {
		this.m_relation = a_relation;
	}

	/**
	 * get the relationship
	 *
	 * @return relationship
	 */
	public int getRelation() {
		return m_relation;
	}

	/**
	 * set the configuration pie
	 *
	 * @param m_hais
	 *            Configuration tiles
	 */
	public void setHais(Hai m_hais[]) {
		this.m_hais = m_hais;
	}

	/**
	 * get the configuration pie.
	 *
	 * @return configuration tiles
	 */
	public Hai[] getHais() {
		return m_hais;
	}

	/**
	 * Sub dew I want to copy.
	 *
	 * @param a_dest
	 *            Destination Vice dew
	 * @param a_src
	 *            Copy of the sub-dew
	 */
	public static void copy(Fuuro a_dest, Fuuro a_src) {
		a_dest.m_type = a_src.m_type;
		a_dest.m_relation = a_src.m_relation;

		for (int i = 0; i < Mahjong.MENTSU_HAI_MEMBERS_4; i++) {
			Hai.copy(a_dest.m_hais[i], a_src.m_hais[i]);
		}
	}
}
