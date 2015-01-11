package jp.sourceforge.andjong.mahjong;

import android.util.Log;
import jp.sourceforge.andjong.AndjongView;
import jp.sourceforge.andjong.Settings;
import jp.sourceforge.andjong.mahjong.AgariScore;
import jp.sourceforge.andjong.mahjong.AgariScore.AgariInfo;
import jp.sourceforge.andjong.mahjong.AgariSetting.YakuflgName;
import jp.sourceforge.andjong.mahjong.CountFormat.Combi;
import static jp.sourceforge.andjong.mahjong.EventIf.*;

/**
 *  A class to manage the game.
 *
 * @author Yuji Urushibara
 *
 */
public class Mahjong implements Runnable {
	private static final String TAG = "Mahjong";

	/** AndjongView */
	private AndjongView m_view;

	/**  山 */
	private Yama m_yama;

	/** 東一局 */
	public final static int KYOKU_TON_1 = 0;
	/**  */
	public final static int KYOKU_TON_2 = 1;
	/**  */
	public final static int KYOKU_TON_3 = 2;
	/**  */
	public final static int KYOKU_TON_4 = 3;
	/**  */
	public final static int KYOKU_NAN_1 = 4;
	/**  */
	public final static int KYOKU_NAN_2 = 5;
	/**  */
	public final static int KYOKU_NAN_3 = 6;
	/** */
	public final static int KYOKU_NAN_4 = 7;

	/** 局 */
	private int m_kyoku;

	/** 局の最大値 */
	private int m_kyokuEnd;

	/** Tsumo tile */
	private Hai m_tsumoHai;

	/** 捨牌 */
	private Hai m_suteHai;

	/** The number of Reach bar*/
	private int m_reachbou;

	/**  本場 */
	private int m_honba;

	/** Player number */
	private int m_playerNum;

	/** The information provided to the player */
	private Info m_info;

	/**  Array of player */
	private Player[] m_players;

	/** Wind a convert to the player index array*/
	private int[] m_kazeToPlayerIdx = new int[4];

	/** The information provided to the UI*/
	private InfoUi m_infoUi;

	/** Array of dice */
	private Sai[] m_sais = new Sai[] { new Sai(), new Sai() };

	/** Parent of player index */
	private int m_iOya;

	/** Player index of  起家*/
	private int m_iChiicha;

	/**  連荘 */
	private boolean m_renchan;

	/** 発行した風 */
	private int m_kazeFrom;

	/** 対象となった風*/
	private int m_kazeTo;

	/** 持ち点の初期値 */
	private static final int TENBOU_INIT = 25000;

	private int m_suteHaisCount = 0;
	public int getSuteHaisCount() {
		return m_suteHaisCount;
	}
	private SuteHai[] m_suteHais = new SuteHai[136];
	public SuteHai[] getSuteHais() {
		return m_suteHais;
	}

	public int getPlayerSuteHaisCount(int a_kaze) {
		return m_players[m_kazeToPlayerIdx[a_kaze]].getSuteHaisCount();
	}

	/**
	 * Constructor
	 *
	 * @param view
	 *            View
	 */
	public Mahjong(AndjongView view) {
		super();
		this.m_view = view;
	}

	/**
	 * 山を取得
	 *
	 * @return 山
	 */
	Yama getYama() {
		return m_yama;
	}

	/**
	 * 局を取得
	 *
	 * @return 局
	 */
	int getkyoku() {
		return m_kyoku;
	}

	/**
	 * Tsumo I get the pie
	 *
	 * @return Tsumo tile
	 */
	Hai getTsumoHai() {
		return m_tsumoHai;
	}

	/**
	 *  the discarded tile
	 *
	 * @return discarded tile
	 */
	Hai getSuteHai() {
		return m_suteHai;
	}

	public int getReachbou() {
		return m_reachbou;
	}

	public void setReachbou(int reachbou) {
		m_reachbou = reachbou;
	}

	/**
	 * get the player index of Okoshi-ka 起家
	 *
	 * @return index of Okoshi-ka 起家
	 */
	public int getChiichaIdx() {
		return m_iChiicha;
	}

	/**
	 * an array of dice.
	 *
	 * @return array of dice
	 */
	Sai[] getSais() {
		return m_sais;
	}

	public int getRelation(
			int fromKaze,
			int toKaze) {
		int relation;
		if (fromKaze == toKaze) {
			relation = RELATION_JIBUN;
		} else if ((fromKaze + 1) % 4 == toKaze) {
			relation = RELATION_SHIMOCHA;
		} else if ((fromKaze + 2) % 4 == toKaze) {
			relation = RELATION_TOIMEN;
		} else {
			relation = RELATION_KAMICHA;
		}
		return relation;
	}

	/*
	 * 共通定義
	 */

	/**  面子の構成牌の数(3個) */
	public static final int MENTSU_HAI_MEMBERS_3 = 3;
	/**  面子の構成牌の数(4個) */
	public static final int MENTSU_HAI_MEMBERS_4 = 4;

	/** 他家との関係 自分*/
	public static final int RELATION_JIBUN = 0;
	/** 他家との関係 上家 */
	public static final int RELATION_KAMICHA = 1;
	/**  他家との関係 対面 */
	public static final int RELATION_TOIMEN = 2;
	/** 他家との関係 下家 */
	public static final int RELATION_SHIMOCHA = 3;

	/**  割れ目 */
	//private int mWareme;

	/** Active player */
	private Player activePlayer;

	private PlayerAction m_playerAction = new PlayerAction();

	public int getManKaze() {
		return m_players[0].getJikaze();
	}

	/**
	 * I want to start the game
	 */
	public void play() {
		//
		initialize();

		// decide the parent.
		m_sais[0].saifuri();
		m_sais[1].saifuri();
		m_iOya = (m_sais[0].getNo() + m_sais[1].getNo() - 1) % 4;
		m_iChiicha = m_iOya;

		//  will issue events (parent decided).
		//m_view.event(EventId.OYAGIME, KAZE_NONE, KAZE_NONE);
		m_view.event(EventId.START_GAME, KAZE_NONE, KAZE_NONE);

        //System.out.println("By GAry: " + m_view.getStyle());
		// Repeat 局 to proceed with the game
	    if (m_view.isEndless()) {
            while (true) {
                startKyoku();
                if (m_renchan) {
                    // I will issue events (ream Zhuang).
                    //m_view.event(EventId.RENCHAN, KAZE_NONE, KAZE_NONE);
                    continue;
                }
                m_kyoku++;
            }
        } else {
            while (m_kyoku <= m_kyokuEnd) {
                // 局を開始
                startKyoku();

                //  連荘の場合、局を進 you do not advance
                if (m_renchan) {
                    // I will issue events (ream Zhuang).
                    //m_view.event(EventId.RENCHAN, KAZE_NONE, KAZE_NONE);
                    continue;
                }

                // 局を進
                m_kyoku++;
            }

            // I will issue event (end of the game).
            m_view.event(EventId.END_GAME, KAZE_NONE, KAZE_NONE);
        }
	}

	/**
	 *
	 */
	private void initialize() {

        // 山を作成
        m_yama = new Yama(m_view.getStyle());

        // set the red Dora
        if (m_view.isAkaDora()) {
            m_yama.setRedDora(Hai.ID_PIN_5, 2);
            m_yama.setRedDora(Hai.ID_WAN_5, 1);
            m_yama.setRedDora(Hai.ID_SOU_5, 1);
        }

        // 局を初期化
        m_kyoku = KYOKU_TON_1;
        //m_kyoku = KYOKU_TON_4;

        // 局の終了を設定
        //m_kyokuEnd = KYOKU_NAN_4;
        m_kyokuEnd = m_view.getKyokusuu();

        //Tsumo I create a pie.
        m_tsumoHai = new Hai();

        // 捨牌を作成
        m_suteHai = new Hai();

        m_suteHaisCount = 0;

        // initialize the number of Reach bar
        m_reachbou = 0;

        // 本場を初期化
        m_honba = 0;

        //
        m_playerNum = 4;

        //  create information to be provided to the player
        m_info = new Info(this);

        // initialize an array of players
        m_players = new Player[m_playerNum];
        if (m_view.isDebug()) {
            m_players[0] = new Player((EventIf) new AIPon(m_info, "A"));
        } else{
        	m_players[0] = new Player((EventIf) new Man(m_info, "A", m_playerAction));
        }
		m_players[1] = new Player((EventIf) new AIPon(m_info, "B"));
		m_players[2] = new Player((EventIf) new AI(m_info, "C"));
		m_players[3] = new Player((EventIf) new AI(m_info, "D"));

		for (int i = 0; i < m_playerNum; i++) {
			m_players[i].setTenbou(TENBOU_INIT);
		}

		// want to initialize the array to be converted to the player index the wind.
		m_kazeToPlayerIdx = new int[m_players.length];

		// create a information to be provided to the UI.
		m_infoUi = new InfoUi(this, m_playerAction);

		// initialize the UI.
		m_view.initUi(m_infoUi, "AndjongView");
	}

	boolean m_tenpai[] = new boolean[4];

	public boolean[] getTenpai() {
		return m_tenpai;
	}

	/**
	 *  局を開始
	 */
	private void startKyoku() {
		// 連荘を初期化
		m_renchan = false;

		m_isTenhou = true;
		m_isChiihou = true;
		m_isTsumo = false;
		m_isRinshan = false;
		m_isLast = false;

		//  set the self-wind players
		setJikaze();

		//  initialize the wind that issued the event.
		m_kazeFrom = m_players[m_iOya].getJikaze();

		//  initializes the wind was the subject of the event.
		m_kazeTo = m_players[m_iOya].getJikaze();

		// initialize the player array.
		for (int i = 0; i < m_players.length; i++) {
			m_players[i].init();
		}

		m_suteHaisCount = 0;

		// Shuffle
		m_yama.xipai();

		// dice rolls
		m_sais[0].saifuri();
		m_sais[1].saifuri();

		// issue a UI events (re pretend).
		//m_view.event(EventId.SAIFURI, mFromKaze, mToKaze);

		// 山に割れ目を設定
		setWareme(m_sais);

		// 配牌
		haipai();

		// 配牌）を発行
		//m_view.event(EventId.HAIPAI, mFromKaze, mToKaze);
		m_view.event(EventId.START_KYOKU, m_kazeFrom, m_kazeTo);

		EventId retEid;

		int tsumoNokori;
		int score;
		int iPlayer;
		// 局を進行 -- Gary entry point
		KYOKU_MAIN: while (true) {
			// issue a UI events (progression wait)
			m_view.event(EventId.UI_WAIT_PROGRESS, KAZE_NONE, KAZE_NONE);

			// get the Tsumo pie.
			m_tsumoHai = m_yama.tsumo();

			// If there is no Tsumo tile (ツモ牌がない場合、)流局
			if (m_tsumoHai == null) {
				// 流し満貫の確認
				for (int i = 0, j = m_iOya; i < m_players.length; i++, j++) {
					if (j >= m_players.length) {
						j = 0;
					}

					boolean agari = true;
					Hou hou = m_players[j].getKawa();
					SuteHai[] suteHais = hou.getSuteHais();
					int suteHaisLength = hou.getSuteHaisLength();
					for (int k = 0; k < suteHaisLength; k++) {
						if (suteHais[k].isNaki() || !suteHais[k].isYaochuu()) {
							agari = false;
							break;
						}
					}

					if (agari) {
						m_kazeFrom = m_kazeTo = m_players[j].getJikaze();

						m_score = new AgariScore();
						m_score.setNagashiMangan(m_agariInfo, m_view.getResources());

						iPlayer = m_kazeToPlayerIdx[m_kazeFrom];
						if (m_iOya == iPlayer) {
							score = m_agariInfo.m_score.m_oyaRon + (m_honba * 300);
							for (int l = 0; l < 3; l++) {
								iPlayer = (iPlayer + 1) % 4;
								m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_oyaTsumo + (m_honba * 100));
							}
						} else {
							score = m_agariInfo.m_score.m_koRon + (m_honba * 300);
							for (int l = 0; l < 3; l++) {
								iPlayer = (iPlayer + 1) % 4;
								if (m_iOya == iPlayer) {
									m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_oyaTsumo + (m_honba * 100));
								} else {
									m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_koTsumo + (m_honba * 100));
								}
							}
						}

						activePlayer.increaseTenbou(score);
						m_agariInfo.m_agariScore = score - (m_honba * 300);

						// 点数を清算
						activePlayer.increaseTenbou(m_reachbou * 1000);

						// initialize the number of Reach bar.
						m_reachbou = 0;

						// issue a UI events (Tsumo up)
						m_view.event(EventId.TSUMO_AGARI, m_kazeFrom, m_kazeTo);

						// want to update the parent.
						if (m_iOya != m_kazeToPlayerIdx[m_kazeFrom]) {
							m_iOya++;
							if (m_iOya >= m_players.length) {
								m_iOya = 0;
							}
							m_honba = 0;
						} else {
							m_renchan = true;
							m_honba++;
						}

						break KYOKU_MAIN;
					}
				}

				// confirm the Tenpai
				int tenpaiCount = 0;
				for (int i = 0; i < m_tenpai.length; i++) {
					iPlayer = m_kazeToPlayerIdx[i];
					m_tenpai[i] = m_players[iPlayer].isTenpai();
					if (m_tenpai[i]) {
						tenpaiCount++;
					}
				}
				int incScore = 0;
				int redScore = 0;
				switch (tenpaiCount) {
				case 0:
					break;
				case 1:
					incScore = 3000;
					redScore = 1000;
					break;
				case 2:
					incScore = 1500;
					redScore = 1500;
					break;
				case 3:
					incScore = 1000;
					redScore = 3000;
					break;
				}
				for (int i = 0; i < m_tenpai.length; i++) {
					if (m_tenpai[i]) {
						m_players[m_kazeToPlayerIdx[i]].increaseTenbou(incScore);
					} else {
						m_players[m_kazeToPlayerIdx[i]].reduceTenbou(redScore);
					}
				}

				// issue a UI events 流局
				m_view.event(EventId.RYUUKYOKU, KAZE_NONE, KAZE_NONE);

				//  drop the flag
				for (int i = 0; i < m_tenpai.length; i++) {
					m_tenpai[i] = false;
				}

				//  親を更新
				m_iOya++;
				if (m_iOya >= m_players.length) {
					m_iOya = 0;
				}

				//本場を増
				m_honba++;

				break KYOKU_MAIN;
			}

			tsumoNokori = m_yama.getTsumoNokori();
			if (tsumoNokori == 0) {
				m_isLast = true;
			} else if (tsumoNokori < 66) {
				m_isChiihou = false;
			}
			Log.i(TAG, "Remain = " + tsumoNokori + ", isChiihou = " + m_isChiihou);

			// issue an event (Tsumo)
			retEid = tsumoEvent(); //must in the middle have AI Chi/Pon events

			// want to handle the event.
			switch (retEid) {
			case TSUMO_AGARI:// rise Tsumo
				if (activePlayer.isReach() && m_view.getStyle().equalsIgnoreCase("japan")) {
					m_setting.setDoraHais(m_yama.getAllDoraHais());
				}
				//getAgariScore(activePlayer.getTehai(), m_tsumoHai);
				m_score = new AgariScore();
				m_score.getAgariScore(activePlayer.getTehai(), m_tsumoHai, combis, m_setting, m_agariInfo, m_view.getResources());

				iPlayer = m_kazeToPlayerIdx[m_kazeFrom];
				if (m_iOya == iPlayer) {
					score = m_agariInfo.m_score.m_oyaRon + (m_honba * 300);
					for (int i = 0; i < 3; i++) {
						iPlayer = (iPlayer + 1) % 4;
						m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_oyaTsumo + (m_honba * 100));
					}
				} else {
					score = m_agariInfo.m_score.m_koRon + (m_honba * 300);
					for (int i = 0; i < 3; i++) {
						iPlayer = (iPlayer + 1) % 4;
						if (m_iOya == iPlayer) {
							m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_oyaTsumo + (m_honba * 100));
						} else {
							m_players[iPlayer].reduceTenbou(m_agariInfo.m_score.m_koTsumo + (m_honba * 100));
						}
					}
				}

				activePlayer.increaseTenbou(score);
				m_agariInfo.m_agariScore = score - (m_honba * 300);

				// 点数を清算
				activePlayer.increaseTenbou(m_reachbou * 1000);

				//initialize the number of Reach bar
				m_reachbou = 0;

				// issue a UI events (Tsumo up)
				m_view.event(retEid, m_kazeFrom, m_kazeTo);

				// 親を更新
				if (m_iOya != m_kazeToPlayerIdx[m_kazeFrom]) {
					m_iOya++;
					if (m_iOya >= m_players.length) {
						m_iOya = 0;
					}
					m_honba = 0;
				} else {
					m_renchan = true;
					m_honba++;
				}

				break KYOKU_MAIN;
			case RON_AGARI://  Ron
				if (activePlayer.isReach()&& m_view.getStyle().equalsIgnoreCase("japan")) {
					m_setting.setDoraHais(m_yama.getAllDoraHais());
				}
				m_score = new AgariScore();
				m_score.getAgariScore(activePlayer.getTehai(), m_suteHai, combis, m_setting, m_agariInfo, m_view.getResources());

				if (m_iOya == m_kazeToPlayerIdx[m_kazeFrom]) {
					score = m_agariInfo.m_score.m_oyaRon + (m_honba * 300);
				} else {
					score = m_agariInfo.m_score.m_koRon + (m_honba * 300);
				}

				m_players[m_kazeToPlayerIdx[m_kazeFrom]].increaseTenbou(score);
				m_players[m_kazeToPlayerIdx[m_kazeTo]].reduceTenbou(score);

				m_agariInfo.m_agariScore = score - (m_honba * 300);

				// 点数を清算
				activePlayer.increaseTenbou(m_reachbou * 1000);

				// initialize the number of Reach bar
				m_reachbou = 0;

				// issue a UI events (Ron).
				m_view.event(retEid, m_kazeFrom, m_kazeTo);

				// 親を更新
				if (m_iOya != m_kazeToPlayerIdx[m_kazeFrom]) {
					m_iOya++;
					if (m_iOya >= m_players.length) {
						m_iOya = 0;
					}
					m_honba = 0;
				} else {
					m_renchan = true;
					m_honba++;
				}

				break KYOKU_MAIN;
			default:
				break;
			}

			// update the wind that issued the event
			m_kazeFrom++;
			if (m_kazeFrom >= m_players.length) {
				m_kazeFrom = 0;
			}
		}
	}

	/**
	 * set the self-wind players
	 */
	private void setJikaze() {
		for (int i = 0, j = m_iOya; i < m_players.length; i++, j++) {
			if (j >= m_players.length) {
				j = 0;
			}

			// set the self-wind players
			m_players[j].setJikaze(i);

			// Wind me to set the array to be converted to the player index.
			m_kazeToPlayerIdx[i] = j;
		}
	}

	/**
	 * 山に割れ目を設定
	 *
	 * @param sais
	 *            Array of dice
	 */
	void setWareme(Sai[] sais) {
		int sum = sais[0].getNo() + sais[1].getNo() - 1;

		//mWareme = sum % 4;

		int startHaisIdx = ((sum % 4) * 36) + sum;

		m_yama.setTsumoHaisStartIndex(startHaisIdx);
	}

	/**
	 * 配牌
	 */
	private void haipai() {
		for (int i = 0, j = m_iOya, max = m_players.length * 13; i < max; i++, j++) {
			if (j >= m_players.length) {
				j = 0;
			}

			m_players[j].getTehai().addJyunTehai(m_yama.tsumo());
		}

		boolean test = false;
		if (test)
		{
			int iPlayer = 0;
			while (m_players[iPlayer].getTehai().getJyunTehaiLength() > 0) {
				m_players[iPlayer].getTehai().rmJyunTehai(0);
			}
			int haiIds[] = {27, 27, 27, 28, 28, 28, 0, 0, 1, 2, 3, 4, 5, 6};
			//int haiIds[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 33, 33, 33, 31, 31};
			//int haiIds[] = {29, 29, 29, 30, 30, 30, 31, 31, 31, 32, 32, 33, 33, 33};
			//int haiIds[] = {0, 1, 2, 3, 4, 5, 6, 7, 31, 31, 33, 33, 33};
			//int haiIds[] = {0, 1, 2, 10, 11, 12, 13, 14, 15, 31, 31, 33, 33, 33};
			//int haiIds[] = {0, 1, 2, 3, 4, 5, 6, 7, 31, 31, 32, 32, 32};
			//int haiIds[] = {0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8};
			//int haiIds[] = {1, 1, 3, 3, 5, 5, 7, 7, 30, 30, 31, 31, 32, 32};
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7};
			//int haiIds[] = {0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8};
			//int haiIds[] = {27, 27, 28, 28, 29, 29, 30, 30, 31, 31, 32, 32, 33, 33};
			//int haiIds[] = {0, 0, 0, 0, 8, 8, 8, 8, 9, 9, 9, 9, 18, 18};
			//int haiIds[] = {0, 8, 9, 17, 18, 26, 27, 28, 29, 30, 31, 32, 33, 34};
			//int haiIds[] = {0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 8};
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7};
			//int haiIds[] = {19, 19, 20, 20, 21, 21, 23, 23, 23, 23, 25, 25, 25, 25};
			//int haiIds[] = {0, 0, 0, 8, 8, 8, 9, 9, 9, 17, 17, 17, 18, 18};
			//int haiIds[] = {0, 0, 0, 0, 8, 8, 8, 8, 9, 9, 9, 9, 18, 18};
			//int haiIds[] = {0, 0, 0, 8, 8, 8, 9, 9, 9, 18, 18, 18, 26, 26};
			//int haiIds[] = {27, 27, 27, 28, 28, 28, 29, 29, 29, 30, 30, 31, 31, 31};
			//int haiIds[] = {31, 31, 31, 32, 32, 32, 33, 33, 33, 30, 30, 30, 29, 29};
			//int haiIds[] = {0, 0, 1, 1, 2, 2, 6, 6, 7, 7, 8, 8, 9, 9};
			//int haiIds[] = {31, 31, 31, 32, 32, 32, 33, 33, 3, 4, 5, 6, 7, 8};
			//int haiIds[] = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4};
			//int haiIds[] = {0, 0, 0, 9, 9, 9, 18, 18, 18, 27, 27, 29, 28, 28};
			//int haiIds[] = {0, 0, 0, 9, 9, 9, 18, 18, 18, 27, 27, 28, 28, 28};
			//int haiIds[] = {0, 0, 0, 9, 9, 9, 18, 18, 18, 5, 6, 7, 27, 27};
			//int haiIds[] = {0, 0, 0, 2, 2, 2, 3, 3, 3, 5, 6, 7, 27, 27};
			//int haiIds[] = {0, 0, 0, 2, 2, 2, 3, 3, 3, 4, 4, 4, 10, 10};
			//int haiIds[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9, 10, 10}; // �C�b�c�[
			//int haiIds[] = {0, 1, 2, 9, 10, 11, 18, 19, 20, 33, 33, 33, 27, 27};
			//int haiIds[] = {1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4}; // Lee titanium pin Ipeko over
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7}; // Lee titanium pin Ipeko over
			//int haiIds[] = {1, 1, 2, 2, 3, 3, 4, 5, 6, 10, 10, 10, 11, 12}; // Lee titanium pin Ipeko over
			for (int i = 0; i < haiIds.length - 1; i++) {
				m_players[iPlayer].getTehai().addJyunTehai(new Hai(haiIds[i]));
			}
			//m_players[iPlayer].getTehai().rmJyunTehai(0);
			//m_players[iPlayer].getTehai().setPon(new Hai(0), getRelation(this.m_kazeFrom, this.m_kazeTo));
			//m_players[iPlayer].getTehai().setPon(new Hai(31), getRelation(this.m_kazeFrom, this.m_kazeTo));
			//m_players[iPlayer].getTehai().rmJyunTehai(0);
			//m_players[iPlayer].getTehai().setChiiLeft(new Hai(0), getRelation(this.m_kazeFrom, this.m_kazeTo));
			//m_players[iPlayer].getKawa().add(new Hai(0));
		}
	}

	boolean m_isTenhou = false; //天和
	boolean m_isChiihou = false; //地和
	boolean m_isTsumo = false;
	boolean m_isRinshan = false;
	boolean m_isLast = false;

	/**
	 *  issue an event (Tsumo). //Gary key
	 *
	 * @return Event ID
	 */
	private EventId tsumoEvent() {
		// set the active player
		activePlayer = m_players[m_kazeToPlayerIdx[m_kazeFrom]];

		m_isTsumo = true;

		//m_tsumoHai = new Hai(13, true);
		//issue a UI events (Tsumo).
		m_view.event(EventId.TSUMO, m_kazeFrom, m_kazeFrom);

		// issue an event (Tsumo).
		EventId retEid = activePlayer.getEventIf().event(EventId.TSUMO, m_kazeFrom, m_kazeFrom);
		Log.i(TAG, retEid.toString() + ", kazeFrom = " + m_kazeFrom + ", kazeTo = " + m_kazeTo);

		m_isTenhou = false;

		m_isTsumo = false;

		// issue a UI events (progression wait)
		m_view.event(EventId.UI_WAIT_PROGRESS, m_kazeFrom, m_kazeFrom);

		int sutehaiIdx;
		Hai[] kanHais;

		if (retEid != EventId.REACH) {
			activePlayer.setIppatsu(false);
		}

		//  handle the event
		switch (retEid) {
		case ANKAN:
			m_isChiihou = false;

			activePlayer.getTehai().addJyunTehai(m_tsumoHai);
			sutehaiIdx = activePlayer.getEventIf().getISutehai();
			kanHais = m_playerAction.getKanHais();
			activePlayer.getTehai().setAnKan(kanHais[sutehaiIdx], getRelation(this.m_kazeFrom, this.m_kazeTo));

			// post an event.
			retEid = notifyEvent(EventId.ANKAN, m_kazeFrom, m_kazeFrom);

			// issue a UI events (progression wait)
			m_view.event(EventId.UI_WAIT_PROGRESS, KAZE_NONE, KAZE_NONE);

			//  get the Tsumo pie.
			//m_tsumoHai = m_yama.rinshanTsumo();
            String style = m_view.getStyle();

            if (style.equalsIgnoreCase("japan")) {
                m_tsumoHai = m_yama.rinshanTsumo();
            }else if (style.equalsIgnoreCase("hongkong")) {
                m_tsumoHai = m_yama.tsumo();
            }
            //m_tsumoHai = m_yama.tsumo();
			// issue an event (Tsumo).
			m_isRinshan = true;
			retEid = tsumoEvent();
			m_isRinshan = false;
			break;
		case TSUMO_AGARI:// rise // Tsumo
			break;
		case SUTEHAI:// discarded tile
			// get the index of the discarded tile.
			sutehaiIdx = activePlayer.getEventIf().getISutehai();

			// take between the physical pie
			m_infoUi.setSutehaiIdx(sutehaiIdx);
			m_view.event(EventId.UI_WAIT_RIHAI, m_kazeFrom, m_kazeFrom);

			if (sutehaiIdx >= activePlayer.getTehai().getJyunTehaiLength()) {// Tsumo cut
				Hai.copy(m_suteHai, m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
			} else {// 手出
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
				activePlayer.getKawa().setTedashi(true);
			}
			m_suteHais[m_suteHaisCount++] = new SuteHai(m_suteHai);

			if (!activePlayer.isReach()) {
				activePlayer.setSuteHaisCount(m_suteHaisCount);
			}

			//  post an event
			retEid = notifyEvent(EventId.SUTEHAI, m_kazeFrom, m_kazeFrom);
			break;
		case REACH:
			// get the index of the discarded tile.
			sutehaiIdx = activePlayer.getEventIf().getISutehai();
			activePlayer.setReach(true);
			if (m_isChiihou) {
				activePlayer.setDoubleReach(true);
			}
			activePlayer.setSuteHaisCount(m_suteHaisCount);
			m_view.event(EventId.UI_WAIT_RIHAI, m_kazeFrom, m_kazeFrom);

			if (sutehaiIdx >= activePlayer.getTehai().getJyunTehaiLength()) {// Tsumo cut
				Hai.copy(m_suteHai, m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
				activePlayer.getKawa().setReach(true);
			} else {// mess around
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, sutehaiIdx);
				activePlayer.getTehai().rmJyunTehai(sutehaiIdx);
				activePlayer.getTehai().addJyunTehai(m_tsumoHai);
				activePlayer.getKawa().add(m_suteHai);
				activePlayer.getKawa().setTedashi(true);
				activePlayer.getKawa().setReach(true);
			}
			m_suteHais[m_suteHaisCount++] = new SuteHai(m_suteHai);

			activePlayer.reduceTenbou(1000);
			activePlayer.setReach(true);
			m_reachbou++;

			activePlayer.setIppatsu(true);

			// post an event
			retEid = notifyEvent(EventId.REACH, m_kazeFrom, m_kazeFrom);
			break;
		default:
			break;
		}

		return retEid;
	}

	/**
	 * will be notified of the event.
	 *
	 * @param a_eventId
	 *            Event ID
	 * @param a_kazeFrom
	 *            Wind that issued the event
	 * @param a_kazeTo
	 *            Wind became the events of interest
	 * @return Event ID
	 */
	private EventId notifyEvent(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		//  issue a UI event.
		m_view.event(a_eventId, a_kazeFrom, a_kazeTo);

		EventId ret = EventId.NAGASHI;
		int iSuteHai;

		switch (a_eventId) {
		case PON:
		case CHII_CENTER:
		case CHII_LEFT:
		case CHII_RIGHT:
		case DAIMINKAN:
		case SUTEHAI:
		case REACH:
			for (int i = 0, j = a_kazeFrom + 1; i < m_players.length - 1; i++, j++) {
				if (j >= m_players.length) {
					j = 0;
				}

				// set the active player
				activePlayer = m_players[m_kazeToPlayerIdx[j]];

				ret = activePlayer.getEventIf().event(EventId.RON_CHECK, a_kazeFrom, j);
				if (ret == EventId.RON_AGARI) {
					// set the active player.
					this.m_kazeFrom = j;
					this.m_kazeTo = a_kazeFrom;
					activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
					return ret;
				}
			}
			break;
		}

		//  post an event to each player
		NOTIFYLOOP: for (int i = 0, j = a_kazeFrom; i < m_players.length; i++, j++) {
			if (j >= m_players.length) {
				j = 0;
			}

			//  set the active player.
			activePlayer = m_players[m_kazeToPlayerIdx[j]];

			// raise an event.
			a_kazeTo = j;
			ret = activePlayer.getEventIf().event(a_eventId, a_kazeFrom, a_kazeTo);

			if (ret != EventId.NAGASHI) {
				for (int k = 0; k < 4; k++) {
					m_players[k].setIppatsu(false);
				}
			}

			//  handle the event
			switch (ret) {
			case TSUMO_AGARI:// rise Tsumo
				// set the active player
				this.m_kazeFrom = j;
				this.m_kazeTo = a_kazeTo;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				break NOTIFYLOOP;
			case RON_AGARI:// Ron
				// set the active player
				this.m_kazeFrom = a_kazeTo;
				this.m_kazeTo = a_kazeFrom;
//				this.m_kazeFrom = j;
//				this.m_kazeTo = toKaze;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				break NOTIFYLOOP;
			case PON:
				m_isChiihou = false;
				// set the active player
				this.m_kazeFrom = j;
				this.m_kazeTo = a_kazeFrom;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setPon(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// get the index of the discarded tileB
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);
				m_suteHais[m_suteHaisCount++] = new SuteHai(m_suteHai);

				// post an event
				ret = notifyEvent(EventId.PON, this.m_kazeFrom, this.m_kazeTo);
				break NOTIFYLOOP;
			case CHII_LEFT:
				m_isChiihou = false;
				//set the active player
				this.m_kazeFrom = j;
				this.m_kazeTo = a_kazeFrom;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setChiiLeft(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// get the index of the discarded tile.
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);
				m_suteHais[m_suteHaisCount++] = new SuteHai(m_suteHai);

				// post an event
				ret = notifyEvent(EventId.CHII_LEFT, this.m_kazeFrom, this.m_kazeTo);
				break NOTIFYLOOP;
			case CHII_CENTER:
				m_isChiihou = false;
				// set the active player
				this.m_kazeFrom = j;
				this.m_kazeTo = a_kazeFrom;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setChiiCenter(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				// get the index of the discarded tile.
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);
				m_suteHais[m_suteHaisCount++] = new SuteHai(m_suteHai);

				// post an event.
				ret = notifyEvent(EventId.CHII_CENTER, this.m_kazeFrom, this.m_kazeTo);
				break NOTIFYLOOP;
			case CHII_RIGHT:
				m_isChiihou = false;
				// set the active player
				this.m_kazeFrom = j;
				this.m_kazeTo = a_kazeFrom;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setChiiRight(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				notifyEvent(EventId.SELECT_SUTEHAI, this.m_kazeFrom, this.m_kazeTo);

				//get the index of the discarded tile.
				iSuteHai = activePlayer.getEventIf().getISutehai();
				activePlayer.getTehai().copyJyunTehaiIndex(m_suteHai, iSuteHai);
				activePlayer.getTehai().rmJyunTehai(iSuteHai);
				activePlayer.getKawa().add(m_suteHai);
				//activePlayer.getKawa().setNaki(true);
				activePlayer.getKawa().setTedashi(true);
				m_suteHais[m_suteHaisCount++] = new SuteHai(m_suteHai);

				// post an event.
				ret = notifyEvent(EventId.CHII_RIGHT, this.m_kazeFrom, this.m_kazeTo);
				break NOTIFYLOOP;
			case DAIMINKAN:
				m_isChiihou = false;
				// set the active player
				this.m_kazeFrom = j;
				this.m_kazeTo = a_kazeFrom;
				activePlayer = m_players[m_kazeToPlayerIdx[this.m_kazeFrom]];
				activePlayer.getTehai().setDaiMinKan(m_suteHai, getRelation(this.m_kazeFrom, this.m_kazeTo));
				m_players[m_kazeToPlayerIdx[this.m_kazeTo]].getKawa().setNaki(true);

				//  post an event.
				ret = notifyEvent(EventId.DAIMINKAN, this.m_kazeFrom, this.m_kazeTo);

				// issue a UI events (progression wait)
				m_view.event(EventId.UI_WAIT_PROGRESS, KAZE_NONE, KAZE_NONE);

				// get the Tsumo pie.
                String style = m_view.getStyle();

                if (style.equalsIgnoreCase("japan")) {
                    m_tsumoHai = m_yama.rinshanTsumo();
                }else if (style.equalsIgnoreCase("hongkong")) {
                    m_tsumoHai = m_yama.tsumo();
                }

				// will issue an event (Tsumo).
				m_isRinshan = true;
				ret = tsumoEvent();
				m_isRinshan = false;
				break NOTIFYLOOP;
			default:
				break;
			}

			if (a_eventId == EventId.SELECT_SUTEHAI) {
				return ret;
			}
		}

		// set the active player.
		activePlayer = m_players[m_kazeToPlayerIdx[a_kazeFrom]];

		return ret;
	}

	/*
	 *  Info, I define an API that provides the InfoUI.
	 */

	/**
	 * Table Dora, I get an array of 槓 Dora.
	 *
	 * @return Table Dora, an array of槓Dora
	 */
	Hai[] getDoras() {
		return getYama().getOmoteDoraHais();
	}

	/**
	 * Table Dora, I get an array of 槓 Dora.
	 *
	 * @return Table Dora, an array of槓Dora
	 */
	Hai[] getUraDoras() {
		return getYama().getUraDoraHais();
	}

	/**
	 * get the self-wind.
	 */
	int getJikaze() {
		return activePlayer.getJikaze();
	}

	/**
	 * 本場を取得
	 *
	 * @return 本場
	 */
	int getHonba() {
		return m_honba;
	}

	/**
	 * reach
	 *
	 * @param kaze
	 *            wind
	 * @return Reach`
	 */
	boolean isReach(int kaze) {
		return m_players[m_kazeToPlayerIdx[kaze]].isReach();
	}

	/**
	 * Tile hand I want to copy
	 *
	 * @param tehai
	 *            手牌
	 * @param kaze
	 *            風
	 */
	void copyTehai(Tehai tehai, int kaze) {
		if (activePlayer.getJikaze() == kaze) {
			Tehai.copy(tehai, activePlayer.getTehai(), true);
		} else {
			Tehai.copy(tehai, m_players[m_kazeToPlayerIdx[kaze]].getTehai(), false);
		}
	}

	/**
	 * Tile hand I want to copy
	 *
	 * @param tehai
	 *            手牌
	 * @param kaze
	 *            風
	 */
	void copyTehaiUi(Tehai tehai, int kaze) {
		Tehai.copy(tehai, m_players[m_kazeToPlayerIdx[kaze]].getTehai(), true);
	}

	/**
	 * copy the river
	 *
	 * @param kawa
	 *            河
	 * @param kaze
	 *            風
	 */
	void copyKawa(Hou kawa, int kaze) {
		Hou.copy(kawa, m_players[m_kazeToPlayerIdx[kaze]].getKawa());
	}

	/**
	 * remaining number of Tsumo.
	 *
	 * @return the number of remaining Tsumo
	 */
	int getTsumoRemain() {
		return m_yama.getTsumoNokori();
	}

	String getName(int kaze) {
		return m_players[m_kazeToPlayerIdx[kaze]].getEventIf().getName();
	}

	int getTenbou(int kaze) {
		return m_players[m_kazeToPlayerIdx[kaze]].getTenbou();
	}

	private Combi[] combis = new Combi[10];
	{
		for (int i = 0; i < combis.length; i++)
			combis[i] = new Combi();
	}

	AgariScore m_score;
	AgariInfo m_agariInfo = new AgariInfo();

	public AgariInfo getAgariInfo() {
		return m_agariInfo;
	}

	public int getBakaze() {
		if (m_kyoku <= KYOKU_TON_4) {
			return KAZE_TON;
		} else {
			return KAZE_NAN;
		}
	}

	AgariSetting m_setting;
	public int getAgariScore(Tehai tehai, Hai addHai) {
		AgariSetting setting = new AgariSetting(this);
		m_setting = setting;
		setting.setDoraHais(getDoras());
		if (activePlayer.isReach()) {
			if (activePlayer.isDoubleReach()) {
				setting.setYakuflg(YakuflgName.DOUBLEREACH.ordinal(), true);
			} else {
				setting.setYakuflg(YakuflgName.REACH.ordinal(), true);
			}
		}
		if (m_isTsumo) {
			setting.setYakuflg(YakuflgName.TUMO.ordinal(), true);
			if (m_isTenhou) {
				setting.setYakuflg(YakuflgName.TENHOU.ordinal(), true);
			} else if (m_isChiihou) {
				setting.setYakuflg(YakuflgName.TIHOU.ordinal(), true);
			}
		}
		if (m_isTsumo && m_isRinshan) {
			setting.setYakuflg(YakuflgName.RINSYAN.ordinal(), true);
		}
		if (m_isLast) {
			if (m_isTsumo) {
				setting.setYakuflg(YakuflgName.HAITEI.ordinal(), true);
			} else {
				setting.setYakuflg(YakuflgName.HOUTEI.ordinal(), true);
			}
		}
		if (activePlayer.isIppatsu()) {
			setting.setYakuflg(YakuflgName.IPPATU.ordinal(), true);
		}
		if (m_view.isKuitan()) {
			setting.setYakuflg(YakuflgName.KUITAN.ordinal(), true);
		}
		m_score = new AgariScore();
		int score = m_score.getAgariScore(tehai, addHai, combis, setting, m_agariInfo, m_view.getResources());
		return score;
	}

//	public String[] getYakuName(Tehai tehai, Hai addHai){
//		AgariSetting setting = new AgariSetting(this);
//		AgariScore score = new AgariScore();
//		return score.getYakuName(tehai, addHai, combis, setting, m_view.getResources());
//	}

	@Override
	public void run() {
		// start the game
		play();
	}

	public void setSutehaiIdx(int sutehaiIdx) {
		m_info.setSutehaiIdx(sutehaiIdx);
	}

	public void postUiEvent(EventId a_eventId, int a_kazeFrom, int a_kazeTo) {
		m_view.event(a_eventId, a_kazeFrom, a_kazeTo);
	}

    public boolean isSecondFan() {
        return m_view.isSecondFan();
    }
}
