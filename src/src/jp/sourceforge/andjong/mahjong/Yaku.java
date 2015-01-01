package jp.sourceforge.andjong.mahjong;
import static jp.sourceforge.andjong.mahjong.AgariSetting.YakuflgName.*;
import static jp.sourceforge.andjong.mahjong.Hai.*;
import static jp.sourceforge.andjong.mahjong.Tehai.JYUN_TEHAI_LENGTH_MAX;
import android.content.res.Resources;
import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.mahjong.AgariSetting.YakuflgName;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

/**
 * Tile hand Class determine the overall role.
 *
 * @author Hiroyuki Muromachi
 *
 */
public class Yaku {
	public static final int JIKAZE_TON = 0;
	public static final int JIKAZE_NAN = 1;
	public static final int JIKAZE_SYA = 2;
	public static final int JIKAZE_PEI = 3;

	Tehai m_tehai;
	Hai m_addHai;
	Combi m_combi;
	AgariSetting m_setting;
	YakuHantei yakuhantei[];
	boolean nakiflg = false;
	int m_doraCount;
	Resources m_res;
	boolean m_kokushi = false;

	/**
	 * Yaku  class constructor.
	 * Save the argument, to create an array of YakuHantei class.
	 * @param tehai tile hand addHai up pie combi tile hand combination info information of
	 */
	Yaku(Tehai tehai, Hai addHai, Combi combi,AgariSetting setting, Resources a_res){
		this.m_tehai = tehai;
		this.m_addHai = addHai;
		this.m_combi  = combi;
		this.m_setting = setting;
		this.m_res = a_res;
		//If there is a squeal
		nakiflg = tehai.isNaki();

		YakuHantei buffer[] = {new CheckTanyao(a_res),
							   new CheckPinfu(a_res),
							   new CheckIpeikou(a_res),
							   new CheckReach(a_res),
							   new CheckIppatu(a_res),
							   new CheckTumo(a_res),
							   new CheckTon(a_res),
							   new CheckNan(a_res),
							   new CheckSya(a_res),
							   new CheckPei(a_res),
							   new CheckHaku(a_res),
							   new CheckHatu(a_res),
							   new CheckCyun(a_res),
							   new CheckHaitei(a_res),
							   new CheckHoutei(a_res),
							   new CheckRinsyan(a_res),
							   new CheckCyankan(a_res),
							   new CheckDoubleReach(a_res),
//							   new CheckTeetoitu(a_res),
							   new CheckCyanta(a_res),
							   new CheckIkkituukan(a_res),
							   new CheckSansyokuDoukou(a_res),
							   new CheckSansyokuDoujun(a_res),
							   new CheckToitoi(a_res),
							   new CheckSanankou(a_res),
							   new CheckSankantu(a_res),
							   new CheckRyanpeikou(a_res),
							   new CheckHonitu(a_res),
							   new CheckJunCyan(a_res),
							   new CheckSyousangen(a_res),
							   new CheckHonroutou(a_res),
							   new CheckTinitu(a_res),
							   new CheckSuuankou(a_res),
							   new CheckSuukantu(a_res),
							   new CheckDaisangen(a_res),
							   new CheckSyousuushi(a_res),
							   new CheckDaisuushi(a_res),
							   new CheckTuuisou(a_res),
							   new CheckChinroutou(a_res),
							   new CheckRyuuisou(a_res),
							   new CheckCyuurennpoutou(a_res),
							   new CheckKokushi(a_res),
							   new CheckTenhou(a_res),
							   new CheckTihou(a_res),
							   new CheckDora(a_res)
		};

		yakuhantei = buffer;

		yakuhantei[yakuhantei.length - 1].hanSuu = m_doraCount;

		//if Yakuman is true (Yi Man), all others are discarded
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if((yakuhantei[i].getYakuman() == true) && (yakuhantei[i].getYakuHantei() == true)) {
				for(int j = 0 ; j < yakuhantei.length; j++){
					if(yakuhantei[j].getYakuman() == false){
						yakuhantei[j].setYakuHantei(false);
					}
				}
			}
		}
	}


	/**
	 * Yaku class constructor
	 *  Save the argument, to create an array of YakuHantei class.
	 * @param tehai tile hand addHai up pie combi tile hand combination info information of
	 */
	Yaku(Tehai tehai, Hai addHai, AgariSetting setting, Resources a_res){
		this.m_tehai = tehai;
		this.m_addHai = addHai;
		this.m_setting = setting;
		this.m_res = a_res;
		this.m_kokushi = false;

		YakuHantei buffer[] = {new CheckKokushi(a_res),
							   new CheckTenhou(a_res),
							   new CheckTihou(a_res)
		};

		this.m_kokushi = buffer[0].hantei;
		yakuhantei = buffer;
	}

	Yaku(Tehai tehai, Hai addHai, Combi combi,AgariSetting setting, int a_status, Resources a_res){
		this.m_tehai = tehai;
		this.m_addHai = addHai;
		this.m_combi  = combi;
		this.m_setting = setting;
		this.m_res = a_res;
		nakiflg = false;

		YakuHantei buffer[] = {
				new CheckTanyao(a_res),
				new CheckReach(a_res),
				new CheckIppatu(a_res),
				new CheckTumo(a_res),
				new CheckHaitei(a_res),
				new CheckHoutei(a_res),
				new CheckRinsyan(a_res),
				new CheckDoubleReach(a_res),
				new CheckTeetoitu(a_res),
				new CheckHonroutouChiitoitsu(a_res),
				new CheckHonitu(a_res),
				new CheckTinitu(a_res),
				new CheckTuuisou(a_res),
				new CheckTenhou(a_res),
				new CheckTihou(a_res),
				new CheckDora(a_res)
		};

		yakuhantei = buffer;

		yakuhantei[yakuhantei.length - 1].hanSuu = m_doraCount;
	}

	/**
	 * Tile hand I get the transliteration number of the total.
	 *
	 * @return There are all hand turn number
	 */
	int getHanSuu(){
		int hanSuu = 0;
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				hanSuu+= yakuhantei[i].getHanSuu();
			}
		}

		// Dora only None
		if (hanSuu == yakuhantei[yakuhantei.length - 1].hanSuu) {
			return 0;
		}

		return hanSuu;
	}

	int getHan(){
		int hanSuu = 0;
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				hanSuu+= yakuhantei[i].getHanSuu();
			}
		}

		return hanSuu;
	}

	/**
	 * get the name of the role that has been established.
	 *
	 * @return true to that array of names of role
	 */
	String[] getYakuName(){
		int count = 0;
		int hanSuu;

		//Count the number of role that has been established
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				count++;
			}
		}

		String yakuName[] = new String[count];
		count = 0;
		for(int i = 0 ; i < yakuhantei.length ; i++){
			if( yakuhantei[i].getYakuHantei() == true){
				hanSuu = yakuhantei[i].getHanSuu();
				if (hanSuu >= 13) {
					yakuName[count] = yakuhantei[i].getYakuName() + m_res.getString(R.string.space) + m_res.getString(R.string.yakuman);
				} else {
					yakuName[count] = yakuhantei[i].getYakuName() + m_res.getString(R.string.space) + hanSuu + m_res.getString(R.string.han);
				}
				count++;
			}
		}
		return yakuName;
	}

	/**
	 * YakuMitsuru is I get whether you are satisfied.
	 *
	 * @return isYakuman
	 */
	boolean getYakumanflg(){
		for(int i = 0 ; yakuhantei[i] != null ; i++){
			if( yakuhantei[i].getYakuman() == true){
				return true;
			}
		}
		return false;
	}

	/**
	 * This is a class for determining an individual role.
	 *
	 * @author Hiroyuki Muromachi
	 *
	 */
	private class YakuHantei{
		/** Auditor establishment determination flag of */
		boolean hantei = false;
		/** YakuMitsuru of determination flag */
		boolean yakuman = false;
		/** Role of name */
		String  yakuName;
		/**  Transliteration number of Auditor */
		int hanSuu;

		/**
		 * get the establishment determination flag of role.
		 *
		 * @return Auditor establishment determination flag of
		 */
		boolean getYakuHantei(){
			return hantei;
		}

		/**
		 * set the establishment determination flag of role.
		 *
		 * @param hantei
		 */
		void setYakuHantei(boolean hantei){
			this.hantei = hantei;
		}

		/**
		 * get the transliteration number of role
		 *
		 * @return transliteration number of officers
		 */
		int getHanSuu(){
			return hanSuu;
		}

		/**
		 * get the name of the role.
		 *
		 * @return the name of the officer
		 */
		String getYakuName(){
			return yakuName;
		}

		/**
		 * get a determination flag of YakuMitsuru.
		 *
		 * @return YakuMitsuru determination flag of
		 */
		boolean getYakuman(){
			return yakuman;
		}
	}

	private class CheckTanyao extends YakuHantei{
		CheckTanyao(Resources a_res){
			hantei = checkTanyao();
			yakuName = a_res.getString(R.string.yaku_tanyao);
			hanSuu = 1;
		}
	}

	private class CheckPinfu extends YakuHantei{
		CheckPinfu(Resources a_res){
			hantei = checkPinfu();
			yakuName = a_res.getString(R.string.yaku_pinfu);
			hanSuu = 1;
		}
	}

	private class CheckIpeikou extends YakuHantei{
		CheckIpeikou(Resources a_res){
			hantei = checkIpeikou();
			if(checkRyanpeikou()){
				hantei = false;
			}
			yakuName = a_res.getString(R.string.yaku_ipeikou);
			hanSuu = 1;
		}
	}

	private class CheckReach extends YakuHantei{
		CheckReach(Resources a_res){
			hantei = checkReach();
			if(checkDoubleReach() == true){
				hantei = false;
			}
			yakuName = a_res.getString(R.string.yaku_reach);
			hanSuu = 1;
		}
	}

	private class CheckIppatu extends YakuHantei{
		CheckIppatu(Resources a_res){
			hantei = checkIppatu();
			yakuName = a_res.getString(R.string.yaku_ippatu);
			hanSuu = 1;
		}
	}

	private class CheckTumo extends YakuHantei{
		CheckTumo(Resources a_res){
			hantei = checkTumo();
			yakuName = a_res.getString(R.string.yaku_tumo);
			hanSuu = 1;
		}
	}

	private class CheckTon extends YakuHantei{
		CheckTon(Resources a_res){
			hantei = checkTon();
			if((m_setting.getJikaze() == JIKAZE_TON) && (m_setting.getBakaze() == JIKAZE_TON)){
				yakuName = a_res.getString(R.string.yaku_doubleton);
				hanSuu = 2;
			}else{
				yakuName = a_res.getString(R.string.yaku_ton);
				hanSuu = 1;
			}
		}
	}

	private class CheckNan extends YakuHantei{
		CheckNan(Resources a_res){
			hantei = checkNan();
			if((m_setting.getJikaze() == JIKAZE_NAN) && (m_setting.getBakaze() == JIKAZE_NAN)){
				yakuName = a_res.getString(R.string.yaku_doublenan);
				hanSuu = 2;
			}else{
				yakuName = a_res.getString(R.string.yaku_nan);
				hanSuu = 1;
			}
		}
	}

	private class CheckSya extends YakuHantei{
		CheckSya(Resources a_res){
			hantei = checkSya();
			yakuName = a_res.getString(R.string.yaku_sya);
			hanSuu = 1;
		}
	}

	private class CheckPei extends YakuHantei{
		CheckPei(Resources a_res){
			hantei = checkPei();
			yakuName = a_res.getString(R.string.yaku_pei);
			hanSuu = 1;
		}
	}

	private class CheckHaku extends YakuHantei{
		CheckHaku(Resources a_res){
			hantei = checkHaku();
			yakuName = a_res.getString(R.string.yaku_haku);
			hanSuu = 1;
		}
	}

	private class CheckHatu extends YakuHantei{
		CheckHatu(Resources a_res){
			hantei = checkHatu();
			yakuName = a_res.getString(R.string.yaku_hatu);
			hanSuu = 1;
		}
	}

	private class CheckCyun extends YakuHantei{
		CheckCyun(Resources a_res){
			hantei = checkCyun();
			yakuName = a_res.getString(R.string.yaku_cyun);
			hanSuu = 1;
		}
	}

	private class CheckHaitei extends YakuHantei{
		CheckHaitei(Resources a_res){
			hantei = checkHaitei();
			yakuName = a_res.getString(R.string.yaku_haitei);
			hanSuu = 1;
		}
	}

	private class CheckHoutei extends YakuHantei{
		CheckHoutei(Resources a_res){
			hantei = checkHoutei();
			yakuName = a_res.getString(R.string.yaku_houtei);
			hanSuu = 1;
		}
	}

	private class CheckRinsyan extends YakuHantei{
		CheckRinsyan(Resources a_res){
			hantei = checkRinsyan();
			yakuName = a_res.getString(R.string.yaku_rinsyan);
			hanSuu = 1;
		}
	}

	private class CheckCyankan extends YakuHantei{
		CheckCyankan(Resources a_res){
			hantei = checkCyankan();
			yakuName = a_res.getString(R.string.yaku_cyankan);
			hanSuu = 1;
		}
	}

	private class CheckDoubleReach extends YakuHantei{
		CheckDoubleReach(Resources a_res){
			hantei = checkDoubleReach();
			yakuName = a_res.getString(R.string.yaku_doublereach);
			hanSuu = 2;
		}
	}

	private class CheckTeetoitu extends YakuHantei{
		CheckTeetoitu(Resources a_res){
			hantei = checkTeetoitu();
			yakuName = a_res.getString(R.string.yaku_teetoitu);
			hanSuu = 2;
		}
	}

	private class CheckCyanta extends YakuHantei{
		CheckCyanta(Resources a_res){
			hantei = checkCyanta();
			if(checkJunCyan()){
				hantei = false;
			}
			if(checkHonroutou()){
				hantei = false;
			}
			yakuName = a_res.getString(R.string.yaku_cyanta);
			if (nakiflg == true) {
				hanSuu = 1;
			}else{
				hanSuu = 2;
			}
		}
	}

	private class CheckIkkituukan extends YakuHantei{
		CheckIkkituukan(Resources a_res){
			hantei = checkIkkituukan();
			yakuName = a_res.getString(R.string.yaku_ikkituukan);
			if (nakiflg == true) {
				hanSuu = 1;
			}else{
				hanSuu = 2;
			}
		}
	}

	private class CheckSansyokuDoujun extends YakuHantei{
		CheckSansyokuDoujun(Resources a_res){
			hantei = checkSansyokuDoujun();
			yakuName = a_res.getString(R.string.yaku_sansyokudoujyun);
			if (nakiflg == true) {
				hanSuu = 1;
			}else{
				hanSuu = 2;
			}
		}
	}

	private class CheckSansyokuDoukou extends YakuHantei{
		CheckSansyokuDoukou(Resources a_res){
			hantei = checkSansyokuDoukou();
			yakuName = a_res.getString(R.string.yaku_sansyokudoukou);
			hanSuu = 2;
		}
	}

	private class CheckToitoi extends YakuHantei{
		CheckToitoi(Resources a_res){
			hantei = checkToitoi();
			yakuName = a_res.getString(R.string.yaku_toitoi);
			hanSuu = 2;
		}
	}

	private class CheckSanankou extends YakuHantei{
		CheckSanankou(Resources a_res){
			hantei = checkSanankou();
			yakuName = a_res.getString(R.string.yaku_sanankou);
			hanSuu = 2;
		}
	}

	private class CheckSankantu extends YakuHantei{
		CheckSankantu(Resources a_res){
			hantei = checkSankantu();
			yakuName = a_res.getString(R.string.yaku_sankantu);
			hanSuu = 2;
		}
	}

	private class CheckRyanpeikou extends YakuHantei{
		CheckRyanpeikou(Resources a_res){
			hantei = checkRyanpeikou();
			yakuName = a_res.getString(R.string.yaku_ryanpeikou);
			hanSuu = 3;
		}
	}

	private class CheckHonitu extends YakuHantei{
		CheckHonitu(Resources a_res){
			hantei = checkHonitu();
			if(checkTinitu()){
				hantei = false;
			}
			yakuName = a_res.getString(R.string.yaku_honitu);
			if (nakiflg == true) {
				hanSuu = 2;
			}else{
				hanSuu = 3;
			}
		}
	}

	private class CheckJunCyan extends YakuHantei{
		CheckJunCyan(Resources a_res){
			hantei = checkJunCyan();
			yakuName = a_res.getString(R.string.yaku_juncyan);
			if (nakiflg == true) {
				hanSuu = 2;
			}else{
				hanSuu = 3;
			}
		}
	}

	private class CheckSyousangen extends YakuHantei{
		CheckSyousangen(Resources a_res){
			hantei = checkSyousangen();
			yakuName = a_res.getString(R.string.yaku_syousangen);
			hanSuu = 2;
		}
	}

	private class CheckHonroutou extends YakuHantei{
		CheckHonroutou(Resources a_res){
			hantei = checkHonroutou();
			yakuName = a_res.getString(R.string.yaku_honroutou);
			hanSuu = 2;
		}
	}

	private class CheckHonroutouChiitoitsu extends YakuHantei{
		CheckHonroutouChiitoitsu(Resources a_res){
			hantei = checkHonroutouChiitoitsu();
			yakuName = a_res.getString(R.string.yaku_honroutou);
			hanSuu = 2;
		}
	}

	private class CheckTinitu extends YakuHantei{
		CheckTinitu(Resources a_res){
			hantei = checkTinitu();
			yakuName = a_res.getString(R.string.yaku_tinitu);
			if (nakiflg == true) {
				hanSuu = 5;
			}else{
				hanSuu = 6;
			}
		}
	}

	private class CheckSuuankou extends YakuHantei{
		CheckSuuankou(Resources a_res){
			hantei = checkSuuankou();
			yakuName = a_res.getString(R.string.yaku_suuankou);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckSuukantu extends YakuHantei{
		CheckSuukantu(Resources a_res){
			hantei = checkSuukantu();
			yakuName = a_res.getString(R.string.yaku_suukantu);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckDaisangen extends YakuHantei{
		CheckDaisangen(Resources a_res){
			hantei = checkDaisangen();
			yakuName = a_res.getString(R.string.yaku_daisangen);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckSyousuushi extends YakuHantei{
		CheckSyousuushi(Resources a_res){
			hantei = checkSyousuushi();
			yakuName = a_res.getString(R.string.yaku_syousuushi);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckDaisuushi extends YakuHantei{
		CheckDaisuushi(Resources a_res){
			hantei = checkDaisuushi();
			yakuName = a_res.getString(R.string.yaku_daisuushi);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckTuuisou extends YakuHantei{
		CheckTuuisou(Resources a_res){
			hantei = checkTuuisou();
			yakuName = a_res.getString(R.string.yaku_tuuisou);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckChinroutou extends YakuHantei{
		CheckChinroutou(Resources a_res){
			hantei = checkChinroutou();
			yakuName = a_res.getString(R.string.yaku_chinroutou);
			hanSuu = 13;
			yakuman = true;
		}
	}

	private class CheckRyuuisou extends YakuHantei{
		CheckRyuuisou(Resources a_res){
			hantei = checkRyuuisou();
			yakuName = a_res.getString(R.string.yaku_ryuuisou);
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckCyuurennpoutou extends YakuHantei{
		CheckCyuurennpoutou(Resources a_res){
			hantei = checkCyuurennpoutou();
			yakuName = a_res.getString(R.string.yaku_cyuurennpoutou);
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckKokushi extends YakuHantei{
		CheckKokushi(Resources a_res){
			hantei = checkKokushi();
			yakuName = a_res.getString(R.string.yaku_kokushi);
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckTenhou extends YakuHantei{
		CheckTenhou(Resources a_res){
			hantei = checkTenhou();
			yakuName = a_res.getString(R.string.yaku_tenhou);
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckTihou extends YakuHantei{
		CheckTihou(Resources a_res){
			hantei = checkTihou();
			yakuName = a_res.getString(R.string.yaku_tihou);
			hanSuu = 13;
			yakuman = true;
		}
	}
	private class CheckDora extends YakuHantei{
		CheckDora(Resources a_res){
			hantei = checkDora();
			yakuName = a_res.getString(R.string.yaku_dora);
			hanSuu = 1;
			yakuman = false;
		}
	}


	boolean checkTanyao() {
		int no;
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();

		// Eating without Tan, not satisfied when I absent.
		if (m_setting.getYakuflg(YakuflgName.KUITAN.ordinal()) == false) {
			if (fuuroNum > 0) {
				return false;
			}
		}

		//Check Juntepai
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			//�P�X���v�Ȃ�Εs����
			if (jyunTehai[i].isYaochuu() == true){
				return false;
			}
		}

		//  Check additional tiles

		//If 1 or 9 characters tiles not satisfied
		if (m_addHai.isYaochuu() == true){
			return false;
		}

		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//Check pie Akirajun
				checkHai = fuuros[i].getHais();
				no = checkHai[0].getNo();
				//Not satisfied if there is Junko of 123 and 789
				if ((no == 1) || (no == 7)){
					return false;
				}
				break;
			case Fuuro.TYPE_MINKOU:
				//Check pie Akirakoku
				checkHai = fuuros[i].getHais();
				if (checkHai[0].isYaochuu() == true){
					return false;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//Check pie Akira槓
				checkHai = fuuros[i].getHais();
				if (checkHai[0].isYaochuu() == true){
					return false;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//Check the tiles of dark 槓
				checkHai = fuuros[i].getHais();
				if (checkHai[0].isYaochuu() == true){
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	boolean checkPinfu() {
		Hai atamaHai;
		//is not satisfied if the squeaking is in
		if(nakiflg == true){
			return false;
		}

		//Face is not the only Junko
		if(m_combi.m_shunNum != 4){
			return false;
		}

		//Head ga Dragons
		atamaHai = new Hai(Hai.noKindToId(m_combi.m_atamaNoKind));
		if( atamaHai.getKind() == KIND_SANGEN ){
			return false;
		}

		// Head place wind
		if( atamaHai.getKind() == KIND_FON
				&& (atamaHai.getNo() - 1) == m_setting.getBakaze()){
			return false;
		}

		//Head self-wind
		if( atamaHai.getKind() == KIND_FON
				&& (atamaHai.getNo() - 1) == m_setting.getJikaze()){
			return false;
		}

		// Not satisfied in the case of head waiting for shaped tiles
		if(m_addHai.isTsuu() == true){
			return false;
		}

		//  Judgment waiting or both sides waiting
		boolean ryanmenflg = false;
		//int addHaiid = addHai.getId();
		int addHaiid = m_addHai.getNoKind();
		//Is divided If you check the number of up tiles
		switch(m_addHai.getNo()){
			//If rise pie 1, 2, 3 and check whether you are able to Junko of 123,234,345
			case 1:
			case 2:
			case 3:
				for(int i = 0 ; i < m_combi.m_shunNum ; i++){
					if(addHaiid == m_combi.m_shunNoKinds[i]){
						ryanmenflg = true;
					}
				}
				break;
			//If rise pie 4, 5, 6, and check whether you are able to Junko of 456 or 234,567 or 345,678 or 456
			case 4:
			case 5:
			case 6:
				for(int i = 0 ; i < m_combi.m_shunNum ; i++){
					if((addHaiid == m_combi.m_shunNoKinds[i])
					 ||(addHaiid-2 == m_combi.m_shunNoKinds[i])){
						ryanmenflg = true;
					}
				}
				break;
			//If rise pie of 7, 8, 9 and check whether you are able to Junko of 567,678,789
			case 7:
			case 8:
			case 9:
				for(int i = 0 ; i < m_combi.m_shunNum ; i++){
					if(addHaiid-2 == (m_combi.m_shunNoKinds[i])){
						ryanmenflg = true;
					}
				}
				break;
			default:
				break;
		}
		if(ryanmenflg == false){
			return false;
		}


		//And if they meet the conditions, about establishment
		return true;
	}

	boolean checkIpeikou() {
		//is not satisfied if the squeaking is in
		if(nakiflg == true){
			return false;
		}

		//I check the combination of Junko
		for (int i = 0; i < m_combi.m_shunNum -1; i++) {
			if(m_combi.m_shunNoKinds[i] == m_combi.m_shunNoKinds[i+1]){
				return true;
			}
		}
		return false;
	}

	boolean checkReach() {
		return m_setting.getYakuflg(REACH.ordinal());
	}

	boolean checkIppatu() {
		return m_setting.getYakuflg(IPPATU.ordinal());
	}

	boolean checkTumo() {
		//is not satisfied if the squeaking is in
		if(nakiflg == true){
			return false;
		}
		return m_setting.getYakuflg(TUMO.ordinal());
	}


	//Auxiliary method that should be used to determine whether Auditor tiles are made
	private boolean checkYakuHai(Tehai tehai, Combi combi , int yakuHaiId) {
		int id;
		Hai checkHai[];

		//Check Juntepai
		for(int i = 0; i < combi.m_kouNum ; i++){
			//Check the ID of the ID and Yakupai
			id = Hai.noKindToId(combi.m_kouNoKinds[i]);
			if( id == yakuHaiId ){
				return true;
			}
		}

		Fuuro[] fuuros;
		fuuros = tehai.getFuuros();
		int fuuroNum;
		fuuroNum = tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
				//Check pie Akirakoku
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				// Check the ID of the ID and Yakupai
				if( id == yakuHaiId ){
					return true;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//Check pie Akira槓
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//Check the ID of the ID and Yakupai
				if( id == yakuHaiId ){
					return true;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//Check the tiles of dark 槓
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//Check the ID of the ID and Yakupai
				if( id == yakuHaiId ){
					return true;
				}
				break;
			default:
				break;
			}
		}
		return false;
	}

	boolean checkTon() {
		if((m_setting.getJikaze() == JIKAZE_TON) || (m_setting.getBakaze() == JIKAZE_TON)){
			return checkYakuHai(m_tehai,m_combi,ID_TON);
		}else{
			return false;
		}
	}

	boolean checkNan() {
		if((m_setting.getJikaze() == JIKAZE_NAN) || (m_setting.getBakaze() == JIKAZE_NAN)){
			return checkYakuHai(m_tehai,m_combi,ID_NAN);
		}else{
			return false;
		}
	}

	boolean checkSya() {
		if(m_setting.getJikaze() == JIKAZE_SYA){
			return checkYakuHai(m_tehai,m_combi,ID_SHA);
		}else{
			return false;
		}
	}

	boolean checkPei() {
		if(m_setting.getJikaze() == JIKAZE_PEI){
			return checkYakuHai(m_tehai,m_combi,ID_PE);
		}else{
			return false;
		}
	}

	boolean checkHaku() {
		return checkYakuHai(m_tehai,m_combi,ID_HAKU);
	}

	boolean checkHatu() {
		return checkYakuHai(m_tehai,m_combi,ID_HATSU);
	}

	boolean checkCyun() {
		return checkYakuHai(m_tehai,m_combi,ID_CHUN);
	}

	boolean checkHaitei() {
		return m_setting.getYakuflg(HAITEI.ordinal());
	}

	boolean checkHoutei(){
		return m_setting.getYakuflg(HOUTEI.ordinal());
	}

	boolean checkRinsyan() {
		return m_setting.getYakuflg(RINSYAN.ordinal());
	}

	boolean checkCyankan() {
		return m_setting.getYakuflg(CHANKAN.ordinal());
	}

	boolean checkDoubleReach() {
		return m_setting.getYakuflg(DOUBLEREACH.ordinal());
	}

	boolean checkTeetoitu() {
		// is not satisfied if the squeaking is in
		if(nakiflg == true){
			return false;
		}

		return true;
	}

	boolean checkCyanta() {
		Hai checkHais[];
		Hai checkHai;

		//Check Koku-ko of Juntepai
		for(int i = 0; i < m_combi.m_kouNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_kouNoKinds[i]));
			//Check the number in the case of  the number of tiles
			if (checkHai.isYaochuu() == false){
				return false;
			}
		}

		//Check Junko of Juntepai
		for(int i = 0; i < m_combi.m_shunNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_shunNoKinds[i]));
			//Check the number in the case of the number of tiles
			if (checkHai.isTsuu() == false){
				if ((checkHai.getNo() > 1) && (checkHai.getNo() < 7))
					return false;
			}
		}

		// Check the head of Juntepai
		checkHai = new Hai(Hai.noKindToId(m_combi.m_atamaNoKind));
		if (checkHai.isYaochuu() == false){
			return false;
		}
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//Check pie Akirajun
				checkHais = fuuros[i].getHais();
				//123 not satisfied if there is Junko other than 789
				if ((checkHais[0].getNo() > 1) && (checkHais[0].getNo() < 7))
					return false;
				break;
			case Fuuro.TYPE_MINKOU:
				//Check pie Akirakoku
				checkHais = fuuros[i].getHais();
				//Check the number in the case of // the number of tiles
				if (checkHais[0].isYaochuu() == false){
					return false;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//Check pie Akira槓
				checkHais = fuuros[i].getHais();
				// Check the number in the case of // the number of tiles
				if (checkHais[0].isYaochuu() == false){
					return false;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//Check the tiles of dark 槓
				checkHais = fuuros[i].getHais();
				// Check the number in the case of the number of tiles
				if (checkHais[0].isYaochuu() == false){
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	boolean checkIkkituukan() {
		int id;
		Hai checkHai[];
		boolean ikkituukanflg[]= {false,false,false,false,false,false,false,false,false};
		//Check Yorozuko, Tsutsu-ko, the 1, 4 and 7 of Saku-ko
		int checkId[] = {ID_WAN_1,ID_WAN_4,ID_WAN_7,ID_PIN_1,ID_PIN_4,ID_PIN_7,ID_SOU_1,ID_SOU_4,ID_SOU_7};

		//Check Junko of tile hand
		for(int i = 0 ; i < m_combi.m_shunNum ; i++){
			id = Hai.noKindToId(m_combi.m_shunNoKinds[i]);
			for(int j =0 ; j < checkId.length ; j++){
				if(id == checkId[j]){
					ikkituukanflg[j] = true;
				}
			}
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//There is no was to check the tiles
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				for(int j =0 ; j < checkId.length ; j++){
					if(id == checkId[j]){
						ikkituukanflg[j] = true;
					}
				}
				break;
			default:
				break;
			}
		}

		//Check whether binge communication transmural are made (yi qi guan tong)
		if(   (ikkituukanflg[0] == true && ikkituukanflg[1] == true && ikkituukanflg[2] == true )
			||(ikkituukanflg[3] == true && ikkituukanflg[4] == true && ikkituukanflg[5] == true )
			||(ikkituukanflg[6] == true && ikkituukanflg[7] == true && ikkituukanflg[8] == true )){
			return true;
		}else{
			return false;
		}
	}

	//Auxiliary method that should be used to determine whether the can three colors
	private static void checkSansyoku(int id , boolean sansyokuflg[][]){
		//Check Yorozuko, Tsutsu-ko, the Saku-ko
		int checkId[] = {ID_WAN_1,ID_PIN_1,ID_SOU_1};
		for(int i =0 ; i < sansyokuflg.length ; i++){
			for(int j = 0; j < sansyokuflg[i].length ; j++){
				if(id == (checkId[i] + j) ){
					sansyokuflg[i][j] = true;
				}
			}
		}
	}

	boolean checkSansyokuDoujun() {
		int id;
		Hai checkHai[];
		boolean sansyokuflg[][]= new boolean[3][9];

		//Initialization of flag
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}

		//Check Junko of tile hand
		for(int i = 0 ; i < m_combi.m_shunNum ; i++){
			id = Hai.noKindToId(m_combi.m_shunNoKinds[i]);
			checkSansyoku(id,sansyokuflg);
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//There is no was to check the tiles
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				checkSansyoku(id,sansyokuflg);
				break;
			default:
				break;
			}
		}

		//Check whether can three colors same order
		for(int i = 0 ; i < sansyokuflg[0].length ; i++){
			if( (sansyokuflg[0][i] == true) && (sansyokuflg[1][i] == true ) && (sansyokuflg[2][i] == true)){
				return true;
			}
		}
		//And return false if it is not able to
		return false;
	}

	boolean checkSansyokuDoukou() {
		int id;
		Hai checkHai[];
		boolean sansyokuflg[][]= new boolean[3][9];


		//Initialization of // flag
		for(int i = 0 ; i<sansyokuflg.length; i++){
			for (int k = 0; k <sansyokuflg[i].length ; k++){
				sansyokuflg[i][k] = false;
			}
		}

		//Check Koku-ko of tile hand
		for(int i = 0 ; i < m_combi.m_kouNum ; i++){
			id = Hai.noKindToId(m_combi.m_kouNoKinds[i]);
			checkSansyoku(id,sansyokuflg);
		}
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				//�����v�̖������`�F�b�N
				//�����v�̖��Ȃ��`�F�b�N
				//�����v�̈ÞȂ��`�F�b�N
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				checkSansyoku(id,sansyokuflg);
				break;
			default:
				break;
			}
		}

		//Check whether can three colors same time
		for(int i = 0 ; i < sansyokuflg[0].length ; i++){
			if( (sansyokuflg[0][i] == true) && (sansyokuflg[1][i] == true ) && (sansyokuflg[2][i] == true)){
				return true;
			}
		}

		//And return false if it is not able to
		return false;
	}

	boolean checkToitoi() {
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		int minShunNum = 0;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				minShunNum++;
				break;
			default:
				break;
			}
		}
		//There is Junko to tile hand
		if((m_combi.m_shunNum != 0) || (minShunNum != 0) ){
			return false;
		}else{
			return true;
		}
	}

	boolean checkSanankou() {

		//対々形で鳴きがなければ成立している【ツモ和了りや単騎の場合、四暗刻が優先される）
		if((checkToitoi() == true)
		 &&(nakiflg == false)){
			return true;
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		int anKanNum = 0;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_ANKAN:
				anKanNum++;
				break;
			default:
				break;
			}
		}

		//Anko and is not satisfied if the sum of the Kura槓 3 of just not
		if((m_combi.m_kouNum + anKanNum) != 3){
			return false;
		}

		//In the case of Tsumo rise establishment
		if(m_setting.getYakuflg(TUMO.ordinal()) == true){
			return true;
		}
		//In the case of Ron rise, and the tiles were Tsu KazuRyo
		else{
			int noKind = m_addHai.getNoKind();
			// Established in the case of head waiting for  Ron rise
			if(noKind == m_combi.m_atamaNoKind){
				return true;
			}else{
				//Pie has become KazuRyo Tsu was pie and Koku-ko to make sure that your same
				boolean checkflg = false;
				for(int i = 0 ; i < m_combi.m_kouNum ; i++){
					if(noKind == m_combi.m_kouNoKinds[i]){
						checkflg = true;
					}
				}

				//If it was sum Ryo~tsu pie of Koku-ko
				if(checkflg == true){
					//If shaped tiles not satisfied
					if(m_addHai.isTsuu() == true){
						return false;
					}else{
						//Check // Do not also become a wait of Junk
						// Example: 11123 で で 1 and the ri, 45556 There are five で and the ri
						boolean checkshun = false;
						for(int i = 0 ; i < m_combi.m_shunNum ; i++){
							switch(m_addHai.getNo()){
								case 1:
									if(noKind == m_combi.m_shunNoKinds[i]){
										checkshun = true;
									}
									break;
								case 2:
									if((noKind == m_combi.m_shunNoKinds[i])
									 ||(noKind-1 == m_combi.m_shunNoKinds[i])){
										checkshun = true;
									}
									break;
								case 3:
								case 4:
								case 5:
								case 6:
								case 7:
									if((noKind == m_combi.m_shunNoKinds[i])
										 ||(noKind-1 == m_combi.m_shunNoKinds[i])
										 ||(noKind-2 == m_combi.m_shunNoKinds[i])){
											checkshun = true;
									}
									break;
								case 8:
									if((noKind-1 == m_combi.m_shunNoKinds[i])
										 ||(noKind-2 == m_combi.m_shunNoKinds[i])){
											checkshun = true;
									}
									break;
								case 9:
									if(noKind-2 == m_combi.m_shunNoKinds[i]){
											checkshun = true;
									}
									break;
							}
						}
						//If you was also the tiles of Junko is established
						if(checkshun == true){
							return true;
						}
						//It is not established if there is no relationship there Junko
						else{
							return false;
						}
					}
				}
				//Koku-ko and if it was the sum Ryo~tsu In unrelated tile established
				else{
					return true;
				}
			}
		}
	}

	boolean checkSankantu() {
		int kansnumber = 0;

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				kansnumber++;
				break;
			default:
				break;
			}
		}
		if(kansnumber == 3){
			return true;
		}else{
			return false;
		}
	}

	boolean checkRyanpeikou() {
		//is not satisfied if the // squeaking is in
		if(nakiflg == true){
			return false;
		}

		//順子が４
		if(m_combi.m_shunNum < 4){
			return false;
		}

		//順子の組み合わせを確認する
		if(m_combi.m_shunNoKinds[0] == m_combi.m_shunNoKinds[1]
		&& m_combi.m_shunNoKinds[2] == m_combi.m_shunNoKinds[3]){
			return true;
		}else{
			return false;
		}
	}

	boolean checkHonitu() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//check 萬子、筒子、索子
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean honituflg = true;
			//check 純手牌
			int jyunTehaiLength = m_tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				//牌が(萬子、筒子、索子)以外もしくは字牌以外
				if ((jyunTehai[j].getKind() != checkId[i]) && (jyunTehai[j].isTsuu() == false)){
					honituflg = false;
				}
			}

			Fuuro[] fuuros;
			fuuros = m_tehai.getFuuros();
			int fuuroNum;
			fuuroNum = m_tehai.getFuuroNum();
			int type;
			for (int j = 0; j < fuuroNum; j++) {
				type = fuuros[j].getType();
				switch (type) {
				case Fuuro.TYPE_MINSHUN:
				case Fuuro.TYPE_MINKOU:
				case Fuuro.TYPE_DAIMINKAN:
				case Fuuro.TYPE_KAKAN:
				case Fuuro.TYPE_ANKAN:
					//check 明順
					//check 明刻
					//check 明槓
					//check 暗槓
					checkHai = fuuros[j].getHais();
					//牌が(萬子、筒子、索子)以外もしくは字牌以外
					if ((checkHai[0].getKind() != checkId[i]) && (checkHai[0].isTsuu() == false)){
						honituflg = false;
					}
					break;
				default:
					break;
				}
			}

			//混一が成立している
			if(honituflg == true){
				return true;
			}

		}
		//
		return false;

	}

	boolean checkJunCyan() {
		Hai checkHais[];
		Hai checkHai;

		//check 純手牌の刻子をチェック
		for(int i = 0; i < m_combi.m_kouNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_kouNoKinds[i]));
			//���v������Εs����
			if( checkHai.isTsuu() == true){
				return false;
			}

			//中張牌ならば不成立
			if(checkHai.isYaochuu() == false ){
				return false;
			}
		}

		//check 純手牌の順子をチェック
		for(int i = 0; i < m_combi.m_shunNum ; i++){
			checkHai = new Hai(Hai.noKindToId(m_combi.m_shunNoKinds[i]));
			//Not satisfied if there is a character tiles
			if( checkHai.isTsuu() == true){
				return false;
			}

			//Check the number in the case of the number of tile
			if ((checkHai.getNo() > NO_1) && (checkHai.getNo() < NO_7)){
				return false;
			}
		}

		//Check the head of Juntepai
		checkHai = new Hai(Hai.noKindToId(m_combi.m_atamaNoKind));
		//字牌があれば不成立
		if( checkHai.isTsuu() == true){
			return false;
		}
		//中張牌ならば不成立
		if(checkHai.isYaochuu() == false ){
			return false;
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//�����̔v���`�F�b�N
				checkHais = fuuros[i].getHais();
				//123 �Ɓ@789 �ȊO�̏��q������Εs����
				if ((checkHais[0].getNo() > NO_1) && (checkHais[0].getNo()< NO_7)){
					return false;
				}
				break;
			case Fuuro.TYPE_MINKOU:
				//�����̔v���`�F�b�N
				checkHais = fuuros[i].getHais();
				//���v������Εs����
				if( checkHais[0].isTsuu() == true){
					return false;
				}
				//�����v�Ȃ�Εs����
				if(checkHais[0].isYaochuu() == false ){
					return false;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//明槓の牌をチェック
				checkHais = fuuros[i].getHais();
				//Not satisfied if there is a character tiles
				if( checkHais[0].isTsuu() == true){
					return false;
				}
				//中張牌ならば不成立
				if(checkHais[0].isYaochuu() == false ){
					return false;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//暗槓の牌をチェック
				checkHais = fuuros[i].getHais();
				//字牌があれば不成立
				if( checkHais[0].isTsuu() == true){
					return false;
				}
				//中張牌ならば不成立
				if(checkHais[0].isYaochuu() == false ){
					return false;
				}
				break;
			default:
				break;
			}
		}

		return true;
	}

	boolean checkSyousangen() {
		//三元牌役が成立している個数を調べる
		int countSangen = 0;
		//白が刻子
		if(checkHaku() == true){
			countSangen++;
		}
		//発が刻子
		if(checkHatu() == true){
			countSangen++;
		}
		//中が刻子
		if(checkCyun() == true){
			countSangen++;
		}
		//頭が三元牌 かつ、三元牌役が2つ成立
		//Hai atamaHai = new Hai(combi.m_atamaNoKind);
		//if((atamaHai.getKind() == KIND_SANGEN) && (countSangen == 2)){
		if(((m_combi.m_atamaNoKind & KIND_SANGEN) == KIND_SANGEN) && (countSangen == 2)){
			return true;
		}

		return false;
	}

	boolean checkHonroutou() {
		// Toitoi are satisfied
		if(checkToitoi() == false){
			return false;
		}

		//Chanthaburi has been satisfied
		if(checkCyanta() == true){
			return true;
		}else{
			return false;
		}
	}

	boolean checkHonroutouChiitoitsu() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();

		// Check Juntepai
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			//１９字牌ならば成立
			if (jyunTehai[i].isYaochuu() == false){
				return false;
			}
		}

		// Check additional tiles

		//１９字牌ならば成立
		if (m_addHai.isYaochuu() == false){
			return false;
		}

		return true;
	}

	boolean checkRenhou() {
		if(m_setting.getYakuflg(RENHOU.ordinal())){
			return true;
		}else{
			return false;
		}
	}

	boolean checkTinitu() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//check 萬子、筒子、索子
		int checkId[] = {KIND_WAN,KIND_PIN,KIND_SOU};

		for (int i = 0 ; i < checkId.length ; i++){
			boolean Tinituflg = true;
			//chck 純手牌
			int jyunTehaiLength = m_tehai.getJyunTehaiLength();
			for (int j = 0; j < jyunTehaiLength; j++) {
				//牌が(萬子、筒子、索子)以外
				if (jyunTehai[j].getKind() != checkId[i]){
					Tinituflg = false;
				}
			}
			Fuuro[] fuuros;
			fuuros = m_tehai.getFuuros();
			int fuuroNum;
			fuuroNum = m_tehai.getFuuroNum();
			for (int j = 0; j < fuuroNum; j++) {
				checkHai = fuuros[j].getHais();
				//�v��(�ݎq�A���q�A���q)�ȊO
				if (checkHai[0].getKind() != checkId[i]){
					Tinituflg = false;
					break;
				}
			}

			//清一が成立している
			if(Tinituflg == true){
				return true;
			}

		}
		//�
		return false;

	}

	boolean checkSuuankou() {
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		int anKanNum = 0;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_ANKAN:
				anKanNum++;
				break;
			default:
				break;
			}
		}

		//手牌の暗刻が4つ
		if((m_combi.m_kouNum + anKanNum) != 4){
			return false;
		}else{
			//In the case of Ron KazuRyori
			if(m_setting.getYakuflg(TUMO.ordinal())){
				return true;
			}
			//In the case of Ron KazuRyoriꍇ
			else{
				//頭待ちならば成立 (四暗刻単騎待ち)
				if(m_addHai.getNoKind() == m_combi.m_atamaNoKind){
					return true;
				}else{
					return false;
				}
			}
		}
	}

	boolean checkSuukantu() {
		int kansnumber = 0;
		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				kansnumber++;
				break;
			default:
				break;
			}
		}
		if(kansnumber == 4){
			return true;
		}else{
			return false;
		}
	}

	boolean checkDaisangen() {
		//三元牌役が成立している個数を調べる
		int countSangen = 0;
		//White ga pung
		if(checkHaku() == true){
			countSangen++;
		}
		//Departure is Koku-ko
		if(checkHatu() == true){
			countSangen++;
		}
		//In ga pung
		if(checkCyun() == true){
			countSangen++;
		}
		//3 yuan pie to have all three
		if(countSangen == 3){
			return true;
		}else{
			return false;
		}
	}

	boolean checkTenhou() {
		return m_setting.getYakuflg(TENHOU.ordinal());
	}

	boolean checkTihou() {
		return m_setting.getYakuflg(TIHOU.ordinal());
	}

	boolean checkSyousuushi() {
		//風牌役が成立している個数を調べる
		int countFon = 0;
		//
		if(checkYakuHai(m_tehai,m_combi,ID_TON) == true){
			countFon++;
		}
		//
		if(checkYakuHai(m_tehai,m_combi,ID_NAN) == true){
			countFon++;
		}
		//
		if(checkYakuHai(m_tehai,m_combi,ID_SHA) == true){
			countFon++;
		}
		//北が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_PE) == true){
			countFon++;
		}

		//And head Kazepai, Kazepai auditors are three established
		//Hai atamaHai = new Hai(combi.m_atamaNoKind);
		//if((atamaHai.getKind() == KIND_FON) && (countFon == 3)){
		if(((m_combi.m_atamaNoKind & KIND_FON) == KIND_FON) && (countFon == 3)){
			return true;
		}else{
			return false;
		}
	}

	boolean checkDaisuushi() {
		//I examine a number of Kazepai auditors are satisfied
		int countFon = 0;
		//
		if(checkYakuHai(m_tehai,m_combi,ID_TON) == true){
			countFon++;
		}
		//
		if(checkYakuHai(m_tehai,m_combi,ID_NAN) == true){
			countFon++;
		}
		//
		if(checkYakuHai(m_tehai,m_combi,ID_SHA) == true){
			countFon++;
		}
		//北が刻子
		if(checkYakuHai(m_tehai,m_combi,ID_PE) == true){
			countFon++;
		}
			//風牌役が4つ成立
		if(countFon == 4){
			return true;
		}else{
			return false;
		}
	}

	boolean checkTuuisou() {
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//���q�����邩�ǂ����m�F
		if(checkToitoi() == false){
			return false;
		}

		//Check Juntepai
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int j = 0; j < jyunTehaiLength; j++) {
			//Not pie shaped tiles
			if (jyunTehai[j].isTsuu() == false){
				return false;
			}
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		for (int i = 0; i < fuuroNum; i++) {
			checkHai = fuuros[i].getHais();
			// Not pie shaped tiles
			if (checkHai[0].isTsuu() == false){
				return false;
			}
		}

		return true;
	}

	boolean checkChinroutou() {
		//Check whether there are Junko
		if(checkToitoi() == false){
			return false;
		}

		//順子なしでジュンチャンが成立しているか（1と9のみで作成）
		if(checkJunCyan() == false){
			return false;
		}

		return true;

	}

	boolean checkRyuuisou() {
		int checkId[] = {ID_SOU_2,ID_SOU_3,ID_SOU_4,ID_SOU_6,ID_SOU_8,ID_HATSU};
		int id;
		boolean ryuuisouflg = false;
		Hai[] jyunTehai = m_tehai.getJyunTehai();
		Hai checkHai[];

		//����v���`�F�b�N
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < jyunTehaiLength; i++) {
			id = jyunTehai[i].getId();
			ryuuisouflg = false;
			for(int j = 0 ; j < checkId.length ; j++){
				//緑一色に使用できる牌だった
				if(id == checkId[j]){
					ryuuisouflg = true;
				}
			}
			//It was not in the appropriate tiles
			if(ryuuisouflg == false){
				return false;
			}
		}

		Fuuro[] fuuros;
		fuuros = m_tehai.getFuuros();
		int fuuroNum;
		fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINSHUN:
				//明順の牌をチェック
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				//索子の2,3,4以外の順子があった場合不成立
				if (id != ID_SOU_2){
					return false;
				}
				break;
			case Fuuro.TYPE_MINKOU:
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				checkHai = fuuros[i].getHais();
				id = checkHai[0].getId();
				ryuuisouflg = false;
				for(int j = 0 ; j < checkId.length ; j++){
					//�緑一色に使用できる牌だった
					if(id == checkId[j]){
						ryuuisouflg = true;
					}
				}
				//It was not in the appropriate tiles
				if(ryuuisouflg == false){
					return false;
				}
				break;
			default:
				break;
			}
		}


		return true;
	}

	boolean checkCyuurennpoutou() {
		//Array to determine the number of tiles (address 0 is not used)
		int countNumber[] = {0,0,0,0,0,0,0,0,0,0};
		Hai checkHai[] = new Hai[JYUN_TEHAI_LENGTH_MAX];

		//��������ꍇ�͐������Ȃ�
		if(nakiflg == true){
			return false;
		}
		//it is not satisfied even if the // tile hand is not in Seiichi
		if(checkTinitu() == false){
			return false;
		}

		//I want to copy the tile hand
		checkHai = m_tehai.getJyunTehai();

		// I examine the number of tiles in the tile hand
		for(int i = 0 ; i < m_tehai.getJyunTehaiLength() ; i++){
			//I increments the number of digits
			countNumber[checkHai[i].getNo()]++;
		}

		//九蓮宝燈になっているか調べる（1と9が３枚以上 2〜8が１枚以上)
		if(( countNumber[1] >= 3)
		&& ( countNumber[2] >= 1)
		&& ( countNumber[3] >= 1)
		&& ( countNumber[4] >= 1)
		&& ( countNumber[5] >= 1)
		&& ( countNumber[6] >= 1)
		&& ( countNumber[7] >= 1)
		&& ( countNumber[8] >= 1)
		&& ( countNumber[9] >= 3)){
			return true;
		}
		return false;
	}

	boolean checkKokushi() {
		//Array to determine the number of tiles (address 0 is not used)
		int checkId[] = {ID_WAN_1,ID_WAN_9,ID_PIN_1,ID_PIN_9,ID_SOU_1,ID_SOU_9,
								ID_TON,ID_NAN,ID_SHA,ID_PE,ID_HAKU,ID_HATSU,ID_CHUN};
		int countHai[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
		Hai checkHai[] = new Hai[JYUN_TEHAI_LENGTH_MAX];

		//is not satisfied if there Crying
		if(nakiflg == true){
			return false;
		}

		//I want to copy the tile hand
		checkHai = m_tehai.getJyunTehai();

		//I find the ID of the tile hand
		for(int i = 0 ; i < m_tehai.getJyunTehaiLength() ; i++){
			for(int j = 0 ; j < checkId.length ; j++){
				if(checkHai[i].getId() == checkId[j]){
					countHai[j]++;
				}
			}
		}

		for(int j = 0 ; j < checkId.length ; j++){
			if(m_addHai.getId() == checkId[j]){
				countHai[j]++;
			}
		}

		boolean atama = false;
		//国士無双が成立しているか調べる(手牌がすべて1.9字牌 すべての１,９字牌を持っている
		for(int i = 0 ; i < countHai.length ; i++){
			//0���̔v������Εs����
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

	boolean checkDora() {
		int doraCount = 0;

		Hai[] doraHais = m_setting.getDoraHais();

		Hai[] jyunTehai = m_tehai.getJyunTehai();
		int jyunTehaiLength = m_tehai.getJyunTehaiLength();
		for (int i = 0; i < doraHais.length; i++) {
			for (int j = 0; j < jyunTehaiLength; j++) {
				if (doraHais[i].getNextHaiId() == jyunTehai[j].getId()) {
					doraCount++;
				}
			}
		}

		for (int j = 0; j < jyunTehaiLength; j++) {
			if (jyunTehai[j].isRed()) {
				doraCount++;
			}
		}

		for (int i = 0; i < doraHais.length; i++) {
			if (doraHais[i].getNextHaiId() == m_addHai.getId()) {
				doraCount++;
				break;
			}
		}

		if (m_addHai.isRed()) {
			doraCount++;
		}

		Fuuro[] fuuros = m_tehai.getFuuros();
		int fuuroNum = m_tehai.getFuuroNum();
		int type;
		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
				for (int j = 0; j < doraHais.length; j++) {
					if (doraHais[j].getNextHaiId() == fuuros[i].getHais()[0].getId()) {
						doraCount += 3;
						break;
					}
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				for (int j = 0; j < doraHais.length; j++) {
					if (doraHais[j].getNextHaiId() == fuuros[i].getHais()[0].getId()) {
						doraCount += 4;
						break;
					}
				}
				break;
			case Fuuro.TYPE_MINSHUN:
				SEARCHLOOP: for (int j = 0; j < doraHais.length; j++) {
					for (int k = 0; k < 3; k++) {
						if (doraHais[j].getNextHaiId() == fuuros[i].getHais()[k].getId()) {
							doraCount += 1;
							break SEARCHLOOP;
						}
					}
				}
				break;
			default:
				break;
			}
		}

		for (int i = 0; i < fuuroNum; i++) {
			type = fuuros[i].getType();
			switch (type) {
			case Fuuro.TYPE_MINKOU:
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
			case Fuuro.TYPE_ANKAN:
				for (int j = 0; j < 4; j++) {
					if (fuuros[i].getHais()[j].isRed()) {
						doraCount++;
					}
				}
				break;
			case Fuuro.TYPE_MINSHUN:
				for (int j = 0; j < 3; j++) {
					if (fuuros[i].getHais()[j].isRed()) {
						doraCount++;
					}
				}
				break;
			default:
				break;
			}
		}

		if (doraCount > 0) {
			m_doraCount = doraCount;
			return true;
		}

		return false;
	}
}
