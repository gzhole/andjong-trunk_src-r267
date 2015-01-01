package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.EventIf.*;

public class AgariSetting {
	/** Special line service*/
	public enum YakuflgName{
		/** Li zi */
		REACH,
		/** One shot */
		IPPATU,
		/** TUMO */
		TUMO  ,
		/** Seabed  */
		HAITEI,
		/** River */
		HOUTEI,
		/** Gang Shang Kai Hua*/
		RINSYAN,
		/** Chang Gang */
		CHANKAN,
		/** Double and riichi */
		DOUBLEREACH,
		/** Tian Hu */
		TENHOU,
		/** Di Hu */
		TIHOU,
		/** Ren Hu	*/
		RENHOU,
		/** Si shan bu kao */
		TIISANPUTOU,
		/** Sink manganese */
		NAGASHIMANGAN,
		/** Eight consecutive Zhuang */
		PARENCHAN,
		/** Kuitan 2 */
		KUITAN,
		/** The number of officers */
		YAKUFLGNUM
	}

	/** Array of Auditor establishment flag */
	boolean yakuflg[] = new boolean [YakuflgName.YAKUFLGNUM.ordinal()];
	/** Own wind */
	private int jikaze = KAZE_NONE;
	/** Field-style setting */
	private int bakaze = KAZE_TON;
	/** Table ド Getting expressed card */
	Hai doraHais[] = new Hai[4];
	/** Back Dora */
	Hai uraDoraHais[] = new Hai[4];

	AgariSetting(){
		for(int i = 0 ; i < yakuflg.length ; i++){
			yakuflg[i] = false;
		}
	}

	/** Constructor  */
	AgariSetting(Mahjong game){
		for(int i = 0 ; i < yakuflg.length ; i++){
			yakuflg[i] = false;
		}
		this.doraHais = game.getDoras();
		this.uraDoraHais = game.getUraDoras();
		this.jikaze = game.getJikaze();
		this.bakaze = game.getBakaze();
	}

	/** Special role established set of */
	void setYakuflg(int yakuNum ,boolean yakuflg){
		this.yakuflg[yakuNum] = yakuflg;
	}
	/** There are special service was established to obtai */
	boolean getYakuflg(int yakuNum){
		return this.yakuflg[yakuNum];
	}

	/** Own wind setting*/
	void setJikaze(int jikaze){
		this.jikaze = jikaze;
	}
	/** Self-wind of acquisition*/
	int getJikaze(){
		return this.jikaze;
	}

	/** Field-style setting  */
	void setBakaze(int bakaze){
		this.bakaze = bakaze;
	}
	/** Field style of acquisition  */
	int getBakaze(){
		return this.bakaze;
	}


	/** Dora display tile setting */
	void setDoraHais(Hai[] doraHais){
		this.doraHais = doraHais;
	}
	/** Get Dora display tiles */
	Hai[] getDoraHais(){
		return this.doraHais;
	}

	/** Back Dora display tile setting */
	void setUraDoraHais(Hai[] uraDoraHais){
		this.doraHais = uraDoraHais;
	}
	/** Get the back Dora display tiles */
	Hai[] getUraDoraHais(){
		return this.uraDoraHais;
	}
}
