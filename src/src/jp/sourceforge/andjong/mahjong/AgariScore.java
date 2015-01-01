package jp.sourceforge.andjong.mahjong;

import static jp.sourceforge.andjong.mahjong.Hai.*;
import android.content.res.Resources;
import jp.sourceforge.andjong.R;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;

public class AgariScore {
	public static class AgariInfo {
		public int m_han;
		public int m_fu;
		public String[] m_yakuNames;
		public Score m_score;
		public int m_agariScore;
	}

	public static class Score {
		public int m_oyaRon;
		public int m_oyaTsumo;
		public int m_koRon;
		public int m_koTsumo;

		public Score(Score a_score) {
			this.m_oyaRon = a_score.m_oyaRon;
			this.m_oyaTsumo = a_score.m_oyaTsumo;
			this.m_koRon = a_score.m_koRon;
			this.m_koTsumo = a_score.m_koTsumo;
		}

		public Score(int a_oyaRon, int a_oyaTsumo, int a_koRon, int a_koTsumo) {
			this.m_oyaRon = a_oyaRon;
			this.m_oyaTsumo = a_oyaTsumo;
			this.m_koRon = a_koRon;
			this.m_koTsumo = a_koTsumo;
		}
	}

	Score m_scoreWork;
	final static Score[][] SCORE = {
			{new Score(    0,    0,    0,    0),new Score(    0,    0,    0,    0),new Score( 1500,  500, 1000,  300),new Score( 2000,  700, 1300,  400),new Score( 2400,  800, 1600,  400),new Score( 2900, 1000, 2000,  500),new Score( 3400, 1200, 2300,  600),new Score( 3900, 1300, 2600,  700),new Score( 4400, 1500, 2900,  800),new Score( 4800, 1600, 3200,  800),new Score( 5300,    0, 3600,    0),new Score( 5800,    0, 3900,    0),new Score( 6300,    0, 2100,    0)},
			{new Score( 2000,  700, 1300,  400),new Score( 2400,    0, 1600,    0),new Score( 2900, 1000, 2000,  500),new Score( 3900, 1300, 2600,  700),new Score( 4800, 1600, 3200,  800),new Score( 5800, 2000, 3900, 1000),new Score( 6800, 2300, 4500, 1200),new Score( 7700, 2600, 5200, 1300),new Score( 8700, 2900, 5800, 1500),new Score( 9600, 3200, 6400, 1600),new Score(10600, 3600, 7100, 1800),new Score(11600, 3900, 7700, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score( 3900, 1300, 2600,  700),new Score( 4800, 1600, 3200,  800),new Score( 5800, 2000, 3900, 1000),new Score( 7700, 2600, 5200, 1300),new Score( 9600, 3200, 6400, 1600),new Score(11600, 3900, 7700, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score( 7700, 2600, 5200, 1300),new Score( 9600, 3200, 6400, 1600),new Score(11600, 3900, 7700, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000),new Score(12000, 4000, 8000, 2000)},
			{new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000)},
			{new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000),new Score(18000, 6000,12000, 3000)},
			{new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000)},
			{new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000)},
			{new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000),new Score(24000, 8000,16000, 4000)},
			{new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000)},
			{new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000),new Score(36000,12000,24000, 6000)},
			{new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000),new Score(48000,16000,32000, 8000)}};

	/** Count format */
	private CountFormat countFormat = new CountFormat();

	/**
	 * Marks I make the calculation.
	 *
	 * @param tehai
	 *            Hand addHai and the っ ta combi card hand There wa combined group Mi se
	 *
	 * @return int points
	 *
	 */
	int countHu(Tehai tehai, Hai addHai, Combi combi, Yaku yaku,AgariSetting setting) {
		int countHu = 20;
		Hai checkHais[];

		//Seven-child and 25 marks in the case of
//		if(yaku.checkTeetoitu() == true){
//			return 25;
//		}

		//Get the tiles of the head
		Hai atamaHai = new Hai(Hai.noKindToId(combi.m_atamaNoKind));

		// 3 yuan brand na ra 2 additional characters
		if (atamaHai.getKind() == KIND_SANGEN) {
			countHu += 2;
		}

		//  2 marks added if field wind
		if ((atamaHai.getId() - ID_TON) == setting.getBakaze()){
			countHu += 2;
		}

		// 2 marks added if self-wind
		if ((atamaHai.getId() - ID_TON) == setting.getJikaze()){
			countHu += 2;
		}

		//If the peace is satisfied, it is priority than 2 marks added by waiting
		if(yaku.checkPinfu() == false){
			// In the case of Tanki waiting 2 marks added
			if(addHai.getNoKind() == combi.m_atamaNoKind){
				countHu += 2;
			}

			// If the Hamahari waiting 2 marks added
           // 2-8 decision whether  the number of tiles
			if(addHai.isYaochuu() == false){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if((addHai.getNoKind()-1) == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}

			// 2 marks additional case of HenCho waiting (3)
			if((addHai.isYaochuu() == false) && (addHai.getNo() == NO_3)){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if( (addHai.getNoKind()-2) == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}

			//   2 marks additional case of // HenCho waiting (7)
			if((addHai.isYaochuu() == false) && (addHai.getNo() == NO_7)){
				for(int i = 0 ; i < combi.m_shunNum ; i++){
					if( addHai.getNoKind() == combi.m_shunNoKinds[i]){
						countHu += 2;
					}
				}
			}
		}

		// Anko due to the added point
		for (int i = 0; i < combi.m_kouNum; i++) {
			Hai checkHai = new Hai(Hai.noKindToId(combi.m_kouNoKinds[i]));
			// License plates ga mo shi ku wa 1,9
			if (checkHai.isYaochuu() == true) {
				countHu += 8;
			} else {
				countHu += 4;
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
				checkHais = fuuros[i].getHais();
				// License plates ga mo shi ku wa 1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 4;
				} else {
					countHu += 2;
				}
				break;
			case Fuuro.TYPE_DAIMINKAN:
			case Fuuro.TYPE_KAKAN:
				//Check pie Akira槓
				checkHais = fuuros[i].getHais();
				//  License plates ga mo shi ku wa 1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 16;
				} else {
					countHu += 8;
				}
				break;
			case Fuuro.TYPE_ANKAN:
				//Check the tiles of dark 槓
				checkHais = fuuros[i].getHais();
				// License plates ga mo shi ku wa 1,9
				if (checkHais[0].isYaochuu() == true) {
					countHu += 32;
				} else {
					countHu += 16;
				}
				break;
			default:
				break;
			}
		}

		// Tsumo 2 score added if peace is not established in the rise
		if(setting.getYakuflg(AgariSetting.YakuflgName.TUMO.ordinal() )== true){
			if(yaku.checkPinfu() == false){
				countHu += 2;
			}
		}

		// 10 marks added in the case of presence Ron rise
		if(setting.getYakuflg(AgariSetting.YakuflgName.TUMO.ordinal() )== false){
			if (yaku.nakiflg == false) {
				countHu += 10;
			}
		}

		// If there is one digit is rounded up
		if (countHu % 10 != 0) {
			countHu = countHu - (countHu % 10) + 10;
		}

		return countHu;
	}

	/**
	 * The rise points I get.
	 *
	 * @param m_tehai
	 *            Hand addHai and the っ ta combi card hand There wa combined group Mi se
	 *
	 * @return int int and the point ri
	 */
	public int getScore(int hanSuu, int huSuu) {
		if (hanSuu == 0) {
			return 0;
		}

		int iFu;
		if (huSuu == 20) {
			iFu = 0;
		} else if (huSuu == 25) {
			iFu = 1;
		} else if (huSuu > 120) {
			iFu = 12;
		} else {
			iFu = (huSuu / 10) - 1;
		}

		int iHan;
		if (hanSuu > 13) {
			iHan = 13 - 1;
		} else {
			iHan = hanSuu - 1;
		}

		m_scoreWork = new Score(SCORE[iHan][iFu]);

		return m_scoreWork.m_koRon;

//		int score;
//		// ���@�~ �Q�́@�i�|���@+�@��]����2�|)��
//		score = huSuu * (int) Math.pow(2, hanSuu + 2);
//		// �q�͏��4�{����{�_(�e��6�{)
//		score *= 4;
//
//		// 100�Ŋ���؂�Ȃ���������ꍇ100�_�J�グ
//		if (score % 100 != 0) {
//			score = score - (score % 100) + 100;
//		}
//		// 7700�ȏ��8000�Ƃ���
////		if (score >= 7700) {
////			score = 8000;
////		}
//
//		if (hanSuu >= 13) { // 13�|�ȏ�͖�
//			score = 32000;
//		} else if (hanSuu >= 11) { // 11�|�ȏ��3�{��
//			score = 24000;
//		} else if (hanSuu >= 8) { // 8�|�ȏ�͔{��
//			score = 16000;
//		} else if (hanSuu >= 6) { // 6�|�ȏ�͒���
//			score = 12000;
//		} else if (hanSuu >= 5) { // 5�|�ȏ�͖���
//			score = 8000;
//		}
//
//		return score;
	}

	public void setNagashiMangan(AgariInfo a_agariInfo, Resources a_res) {
		getScore(5, 30);
		a_agariInfo.m_score = m_scoreWork;
		a_agariInfo.m_fu = 30;
		a_agariInfo.m_han = 5;
		String[] strings = new String[1];
		strings[0] = a_res.getString(R.string.yaku_nagashimangan);
		a_agariInfo.m_yakuNames = strings;
	}

	public int getAgariScore(Tehai tehai, Hai addHai, Combi[] combis,AgariSetting setting, AgariInfo a_agariInfo, Resources a_res) {
		// I get the count format.
		countFormat.setCountFormat(tehai, addHai);

		// I get the combination of the upstream.
		int combisCount = countFormat.getCombis(combis);
		combis = countFormat.getCombis();
		combisCount = countFormat.getCombiNum();

		if (countFormat.isChiitoitsu()) {
			Yaku yaku = new Yaku(tehai, addHai, combis[0], setting, 0, a_res);
			String[] yakuNames = yaku.getYakuName();
			int hanSuu = yaku.getHan();
			int agariScore = getScore(hanSuu, 25);
			a_agariInfo.m_score = m_scoreWork;
			a_agariInfo.m_fu = 25;
			a_agariInfo.m_han = hanSuu;
			a_agariInfo.m_yakuNames = yakuNames;

			return agariScore;
		}

		if (countFormat.isKokushi()) {
			Yaku yaku = new Yaku(tehai, addHai, setting, a_res);
			if (yaku.m_kokushi) {
				int hanSuu = 13;
				int agariScore = getScore(hanSuu, 20);
				a_agariInfo.m_score = m_scoreWork;
				a_agariInfo.m_fu = 0;
				a_agariInfo.m_han = hanSuu;
				a_agariInfo.m_yakuNames = yaku.getYakuName();

				return agariScore;
			}
			return 0;
		}

		//  If there is no combination of up to 0 points
		if (combisCount == 0) {
			return 0;
		}

		// Auditor
		int hanSuu[] = new int[combisCount];
		// Break
		int huSuu[] = new int[combisCount];
		//Points (Ron rise of child)
		int agariScore[] = new int[combisCount];
		// Maximum number of points
		int maxagariScore = 0;

		for (int i = 0; i < combisCount; i++) {
			Yaku yaku = new Yaku(tehai, addHai, combis[i], setting, a_res);
			hanSuu[i] = yaku.getHanSuu();
			huSuu[i] = countHu(tehai, addHai, combis[i],yaku,setting);
			agariScore[i] = getScore(hanSuu[i], huSuu[i]);

			if(maxagariScore < agariScore[i]){
				a_agariInfo.m_score = m_scoreWork;
				maxagariScore = agariScore[i];
				a_agariInfo.m_fu = huSuu[i];
				a_agariInfo.m_han = hanSuu[i];
				a_agariInfo.m_yakuNames = yaku.getYakuName();
			}
		}

		// I Find the maximum value
		maxagariScore = agariScore[0];
		for (int i = 0; i < combisCount; i++) {
			maxagariScore = Math.max(maxagariScore, agariScore[i]);
		}
		return maxagariScore;
	}

	public String[] getYakuName(Tehai tehai, Hai addHai, Combi[] combis,AgariSetting setting, Resources a_res) {
		//  The name of the KazuRyori role
		String[] yakuNames = {""};
		// I get the count format.
		countFormat.setCountFormat(tehai, addHai);

		//I get the combination of the upstream.
		int combisCount = countFormat.getCombis(combis);

		// If there is no combination of up to 0 points
		if (combisCount == 0){
			return yakuNames;
		}

		// Auditor
		int hanSuu[] = new int[combisCount];
		// Break
		int huSuu[] = new int[combisCount];
		// Points (Ron rise of child)
		int agariScore[] = new int[combisCount];
		// Maximum number of points
		int maxagariScore = 0;


		for (int i = 0; i < combisCount; i++) {
			Yaku yaku = new Yaku(tehai, addHai, combis[i], setting, a_res);
			hanSuu[i] = yaku.getHanSuu();
			huSuu[i] = countHu(tehai, addHai, combis[i],yaku,setting);
			agariScore[i] = getScore(hanSuu[i], huSuu[i]);

			if(maxagariScore < agariScore[i]){
				maxagariScore = agariScore[i];
				yakuNames = yaku.getYakuName();
			}
		}

		return yakuNames;
	}
}
