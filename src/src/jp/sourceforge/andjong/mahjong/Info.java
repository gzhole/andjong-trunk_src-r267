package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.Settings;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;
import jp.sourceforge.andjong.mahjong.EventIf.EventId;

/**
 * This is a class that manages the information to be provided to the player.
 *
 * @author Yuji Urushibara
 *
 */
public class Info {
	/** Game */
	protected Mahjong game;

	/**
	 * initialize an instance
	 *
	 * @param game
	 *            Game
	 */
	public Info(Mahjong game) {
		this.game = game;
	}

	/**
	 * get an array of dice.
	 *
	 * @return array of dice
	 */
	public Sai[] getSais() {
		return game.getSais();
	}

	/**
	 * Table Dora, I get an array of 槓 Dora.
	 *
	 * @return Table Dora, an array of槓Dora
	 */
	public Hai[] getDoraHais() {
		return game.getDoras();
	}

	/**
	 * get the self-wind
	 *
	 * @return own wind
	 */
	public int getJikaze() {
		return game.getJikaze();
	}

	public void copyTehai(Tehai tehai) {
		game.copyTehai(tehai, game.getJikaze());
	}

    public boolean isSecondFan() {
        return game.isSecondFan();
    }
	/**
	 * Tile hand I want to copy the.
	 *
	 * @param tehai
	 *            Hand
	 * @param kaze
	 *            Wind
	 */
	public void copyTehai(Tehai tehai, int kaze) {
		game.copyTehai(tehai, kaze);
	}

	/**
	 * I want to copy the river.
	 *
	 * @param kawa
	 *            River
	 * @param kaze
	 *            Wind
	 */
	public void copyKawa(Hou kawa, int kaze) {
		game.copyKawa(kawa, kaze);
	}

	/**
	 * Tsumo I get the pie.
	 *
	 * @return Tsumo tile
	 */
	public Hai getTsumoHai() {
		Hai tsumoHai = game.getTsumoHai();
		if (tsumoHai != null) {
			return new Hai(game.getTsumoHai());
		}
		return null;
	}

	/**
	 * get the discarded tile.
	 *
	 * @return discarded tile
	 */
	public Hai getSuteHai() {
		return new Hai(game.getSuteHai());
	}

	public int getAgariScore() {
		return 0;
	}

	/**
	 * get the upstream point.
	 *
	 * @param tehai
	 *            Hand
	 * @param addHai
	 *            Tiles to be added to the tile hand
	 * @return
	 */
	public int getAgariScore(Tehai tehai, Hai addHai) {
		return game.getAgariScore(tehai, addHai);
	}

	public boolean isReach() {
		return game.isReach(game.getJikaze());
	}

	/**
	 * get a reach.
	 *
	 * @param kaze
	 *            Wind
	 * @return Reach
	 */
	public boolean isReach(int kaze) {
		return game.isReach(kaze);
	}

	/**
	 * get the remaining number of Tsumo
	 *
	 * @return the number of remaining Tsumo
	 */
	public int getTsumoRemain() {
		return game.getTsumoRemain();
	}

	/**
	 * get the stations (Ju)
	 *
	 * @return Ju
	 */
	public int getkyoku() {
		return game.getkyoku();
	}

	/**
	 * get the name
	 *
	 * @param kaze
	 *            Wind
	 * @return name
	 */
	public String getName(int kaze) {
		return game.getName(kaze);
	}

	/**
	 * get a home.
	 *
	 * @return home
	 */
	public int getHonba() {
		return game.getHonba();
	}

	/**
	 *  get the number of Reach bar.
	 *
	 * @return number of Reach bar
	 */
	public int getReachbou() {
		return game.getReachbou();
	}

	/**
	 * get the Tenbo
	 *
	 * @param kaze
	 *            Wind
	 * @return point stick
	 */
	public int getTenbou(int kaze) {
		return game.getTenbou(kaze);
	}

//	public String[] getYakuName(Tehai tehai, Hai addHai){
//		return game.getYakuName(tehai, addHai);
//	}

	public void setSutehaiIdx(
			int mSutehaiIdx) {
		this.mSutehaiIdx = mSutehaiIdx;
	}

	public int getSutehaiIdx() {
		return mSutehaiIdx;
	}

	private int mSutehaiIdx;

	{
		setSutehaiIdx(Integer.MAX_VALUE);
	}

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	public int getReachIndexs(Tehai a_tehai, Hai a_tsumoHai, int[] a_indexs) {
		// If you are not present, it is not possible to reach.
		if (a_tehai.isNaki()) {
			return 0;
		}

		Tehai tehai = new Tehai();
		Tehai.copy(tehai, a_tehai, true);

		int index = 0;
		Hai[] jyunTehai = tehai.getJyunTehai();
		int jyunTehaiLength = tehai.getJyunTehaiLength();
		Hai haiTemp = new Hai();
		Hai addHai;
		CountFormat countFormat = new CountFormat();

		for (int i = 0; i < jyunTehaiLength; i++) {
			Hai.copy(haiTemp, jyunTehai[i]);
			tehai.rmJyunTehai(jyunTehai[i]);
			for (int id = 0; id < Hai.ID_ITEM_MAX; id++) {
				addHai = new Hai(id);
				tehai.addJyunTehai(addHai);
				countFormat.setCountFormat(tehai, a_tsumoHai);
				if (countFormat.getCombis(combis) > 0) {
					a_indexs[index] = i;
					index++;
					tehai.rmJyunTehai(addHai);
					break;
				}
				tehai.rmJyunTehai(addHai);
			}
			tehai.addJyunTehai(haiTemp);
		}

		for (int id = 0; id < Hai.ID_ITEM_MAX; id++) {
			addHai = new Hai(id);
			tehai.addJyunTehai(addHai);
			countFormat.setCountFormat(tehai, null);
			if (countFormat.getCombis(combis) > 0) {
				a_indexs[index] = 13;
				index++;
				tehai.rmJyunTehai(addHai);
				break;
			}
			tehai.rmJyunTehai(addHai);
		}

		return index;
	}

	public int getMachiIndexs(Tehai a_tehai, Hai[] a_hais) {
		Tehai tehai = new Tehai();
		Tehai.copy(tehai, a_tehai, true);

		int index = 0;
		Hai addHai;
		CountFormat countFormat = new CountFormat();

		for (int id = 0; id < Hai.ID_ITEM_MAX; id++) {
			addHai = new Hai(id);
			tehai.addJyunTehai(addHai);
			countFormat.setCountFormat(tehai, null);
			if (countFormat.getCombis(combis) > 0) {
				a_hais[index] = new Hai(id);
				index++;
				tehai.rmJyunTehai(addHai);
			} else {
				tehai.rmJyunTehai(addHai);
			}
		}

		return index;
	}

	public void postUiEvent(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		game.postUiEvent(a_eventId, a_kazeFrom, a_kazeTo);
	}

	public int getSuteHaisCount() {
		return game.getSuteHaisCount();
	}

	public SuteHai[] getSuteHais() {
		return game.getSuteHais();
	}

	public int getPlayerSuteHaisCount() {
		return game.getPlayerSuteHaisCount(game.getJikaze());
	}

    public int getHan(){
        return game.getAgariInfo().m_han;
    }
}
