package jp.sourceforge.andjong.mahjong;

import java.util.Random;

/**
 * I manage the dice.
 *
 * @author Yuji Urushibara
 *
 */
public class Sai {

	private static long m_seed;

	/** number */
	private int m_no = 1;

	/** generator */
	private Random m_random = new Random(m_seed++);

	static {
		m_seed = System.currentTimeMillis();
	}

	/**
	 *
	 *
	 * @return
	 */
	public int getNo() {
		return m_no;
	}

	/**
	 * Get the number Shake the dice.
	 *
	 * @return number
	 */
	public int saifuri() {
		return m_no = m_random.nextInt(6) + 1;
	}
}
