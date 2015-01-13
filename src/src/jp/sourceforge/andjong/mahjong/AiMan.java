package jp.sourceforge.andjong.mahjong;

import android.util.Log;

public class AiMan implements EventIf {
	/** The information provided to the player */
	private Info m_info;

	/** Index of discarded tile  */
	private int m_iSutehai = 0;

	private String name;

	public String getName() {
		return name;
	}

	private PlayerAction m_playerAction;

	public AiMan(Info info, String name, PlayerAction playerAction) {
		this.m_info = info;
		this.name = name;
		this.m_playerAction = playerAction;
	}

	/** 手牌 */
	private Tehai m_tehai = new Tehai();
	private Hou m_kawa = new Hou();

	@Override
	public EventId event(EventId eid, int a_kazeFrom, int a_kazeTo) {
		int sutehaiIdx = 0;
		int agariScore = 0;
		Hai tsumoHai;
		Hai suteHai;
		int indexNum = 0;
		int[] indexs = new int[14];
		int menuNum = 0;
		EventId eventId[] = new EventId[4];
		int jyunTehaiLength;

		Hai[] kanHais = new Hai[4];
		int kanNum = 0;
		Hai[] sarashiHaiLeft = new Hai[2];
		Hai[] sarashiHaiCenter = new Hai[2];
		Hai[] sarashiHaiRight = new Hai[2];
		//boolean isChii = false;
		int chiiCount = 0;
		int iChii = 0;
		int relation = a_kazeFrom - a_kazeTo;
		boolean furiten = false;
		Hai[] a_hais = new Hai[Hai.ID_ITEM_MAX];

		switch (eid) {
		case TSUMO:
			// I want to copy the tile hand
			m_info.copyTehai(m_tehai, m_info.getJikaze());
			// get the Tsumo pie.
			tsumoHai = m_info.getTsumoHai();
           // int han = m_info.getHan();
			if (!m_info.isReach() && (m_info.getTsumoRemain() >= 4)) {
				indexNum = m_info.getReachIndexs(m_tehai, tsumoHai, indexs);
				if (indexNum > 0) {
					m_playerAction.setValidReach(true);
					m_playerAction.m_indexs = indexs;
					m_playerAction.m_indexNum = indexNum;
					//eventId[menuNum] = EventId.REACH;
					//menuNum++;
                    return EventId.REACH;
				}
			}

			agariScore = m_info.getAgariScore(m_tehai, tsumoHai);
            int han = m_info.getHan();
            if ((m_info.isSecondFan() && agariScore > 0 && han > 1) ||(!m_info.isSecondFan() && agariScore > 0)) {
                Log.d("Man", "han = " + han);
                m_playerAction.setValidTsumo(true);
                m_playerAction.setDispMenu(true);
               // eventId[menuNum] = EventId.TSUMO_AGARI;
               // menuNum++;
                return EventId.TSUMO_AGARI;
            }
//            if (agariScore > 0) {
//					}

			// ���������B���[�`��̃J���������Ȃ��B
			//by gary cannot deal with ankan for now.. skip should fix it
			/*if (!m_info.isReach()) {
				kanNum = m_tehai.validKan(tsumoHai, kanHais);
				if (kanNum > 0) {
					m_playerAction.setValidKan(true, kanHais, kanNum);
					//eventId[menuNum] = EventId.ANKAN;
					//menuNum++;
                    return EventId.ANKAN;
				}
			}*/

			if (m_info.isReach()) {
				if (menuNum == 0) {
					m_playerAction.init();
					this.m_iSutehai = 13;
					return EventId.SUTEHAI;
				}
			}

			//m_playerAction.setMenuNum(menuNum);
			/*while (true) {
				//I wait for input.
				m_playerAction.setState(PlayerAction.STATE_SUTEHAI_SELECT);
				m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
				m_playerAction.actionWait();
				if (m_playerAction.isDispMenu()) {

					int menuSelect = m_playerAction.getMenuSelect();
					if ((menuSelect >= 0) && (menuSelect < menuNum)) {
						m_playerAction.init();
						if (eventId[menuSelect] == EventId.REACH) {
							m_playerAction.m_indexs = indexs;
							m_playerAction.m_indexNum = indexNum;
							m_playerAction.setReachSelect(0);
							while (true) {
								// ��͂�҂B
								m_playerAction.setState(PlayerAction.STATE_REACH_SELECT);
								m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
								m_playerAction.actionWait();
								sutehaiIdx = m_playerAction.getReachSelect();
								if (sutehaiIdx != Integer.MAX_VALUE) {
									if (sutehaiIdx >= 0 && sutehaiIdx < indexNum) {
										break;
									}
								}
							}
							m_playerAction.init();
							this.m_iSutehai = indexs[sutehaiIdx];
						} else if ((eventId[menuSelect] == EventId.ANKAN) ||
								(eventId[menuSelect] == EventId.KAN)) {
							if (kanNum > 1) {
								while (true) {
									m_playerAction.init();
									// I wait for input.
									m_playerAction.setValidKan(false, kanHais, kanNum);
									//m_playerAction.setChiiEventId(eventId[iChii]);
									m_playerAction.setState(PlayerAction.STATE_KAN_SELECT);
									m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
									m_playerAction.actionWait();
									int kanSelect = m_playerAction.getKanSelect();
									m_playerAction.init();
									this.m_iSutehai = kanSelect;
									return eventId[menuSelect];
								}
							} else {
								this.m_iSutehai = 0;
							}
						}
						return eventId[menuSelect];
					}
					m_playerAction.init();
				} else {
					sutehaiIdx = m_playerAction.getSutehaiIdx();
					if (sutehaiIdx != Integer.MAX_VALUE) {
						if (sutehaiIdx >= 0 && sutehaiIdx <= 13) {
							break;
						}
					}
				}
			}*/
			m_playerAction.init();
			this.m_iSutehai = sutehaiIdx;
			return EventId.SUTEHAI;
		case SELECT_SUTEHAI:
			m_info.copyTehai(m_tehai, m_info.getJikaze());
			/*jyunTehaiLength = m_tehai.getJyunTehaiLength();
			while (true) {
				// I wait for input.
				m_playerAction.setState(PlayerAction.STATE_SUTEHAI_SELECT);
				m_playerAction.actionWait();
				sutehaiIdx = m_playerAction.getSutehaiIdx();
				if (sutehaiIdx != Integer.MAX_VALUE) {
					if (sutehaiIdx >= 0 && sutehaiIdx <= jyunTehaiLength) {
						break;
					}
				}
			}
			Log.d("Man", "sutehaiIdx = " + sutehaiIdx);*/

			m_playerAction.init();
			this.m_iSutehai =  thinkSutehai(null);
			return EventId.SUTEHAI;
		case RON_CHECK:
			m_info.copyTehai(m_tehai, m_info.getJikaze());
			suteHai = m_info.getSuteHai();

			indexNum = m_info.getMachiIndexs(m_tehai, a_hais);
			if (indexNum > 0) {
				m_info.copyKawa(m_kawa, m_info.getJikaze());
				SuteHai suteHaiTemp = new SuteHai();
				SuteHai[] suteHais = m_kawa.getSuteHais();
				int kawaLength = m_kawa.getSuteHaisLength();
				FURITENLOOP: for (int i = 0; i < kawaLength; i++) {
					suteHaiTemp = suteHais[i];
					for (int j = 0; j < indexNum; j++) {
						if (suteHaiTemp.getId() == a_hais[j].getId()) {
							furiten = true;
							break FURITENLOOP;
						}
					}
				}

				if (!furiten) {
					suteHais = m_info.getSuteHais();
					int suteHaisCount = m_info.getSuteHaisCount();
					int playerSuteHaisCount = m_info.getPlayerSuteHaisCount();
					FURITENLOOP: for (; playerSuteHaisCount < suteHaisCount - 1; playerSuteHaisCount++) {
						suteHaiTemp = suteHais[playerSuteHaisCount];
						for (int j = 0; j < indexNum; j++) {
							if (suteHaiTemp.getId() == a_hais[j].getId()) {
								furiten = true;
								break FURITENLOOP;
							}
						}
					}
				}
			}

			if (!furiten) {
				agariScore = m_info.getAgariScore(m_tehai, suteHai);
                han = m_info.getHan();
				if ((m_info.isSecondFan()  && agariScore > 0&& han > 1) ||(!m_info.isSecondFan() && agariScore > 0)) {
				/*	m_playerAction.setDispMenu(true);
					m_playerAction.setValidRon(true);
					m_playerAction.setMenuNum(1);
					m_playerAction.setMenuSelect(5);
					m_playerAction.setState(PlayerAction.STATE_RON_SELECT);
					m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
					m_playerAction.actionWait();
					int menuSelect = m_playerAction.getMenuSelect();
					if (menuSelect < 1) {*/
						m_playerAction.init();
						return EventId.RON_AGARI;
				//	}
				//	m_playerAction.init();
				}
			}
			break;
		case SUTEHAI:
		case REACH:
			if (a_kazeFrom == m_info.getJikaze()) {
				return EventId.NAGASHI;
			}
			Log.e("SUTEHAI", "fromKaze = " + a_kazeFrom + ", toKaze = " + a_kazeTo);

			m_info.copyTehai(m_tehai, m_info.getJikaze());
			suteHai = m_info.getSuteHai();

			indexNum = m_info.getMachiIndexs(m_tehai, a_hais);
			if (indexNum > 0) {
				m_info.copyKawa(m_kawa, m_info.getJikaze());
				SuteHai suteHaiTemp = new SuteHai();
				SuteHai[] suteHais = m_kawa.getSuteHais();
				int kawaLength = m_kawa.getSuteHaisLength();
				FURITENLOOP: for (int i = 0; i < kawaLength; i++) {
					suteHaiTemp = suteHais[i];
					for (int j = 0; j < indexNum; j++) {
						if (suteHaiTemp.getId() == a_hais[j].getId()) {
							furiten = true;
							break FURITENLOOP;
						}
					}
				}

				if (!furiten) {
					suteHais = m_info.getSuteHais();
					int suteHaisCount = m_info.getSuteHaisCount();
					int playerSuteHaisCount = m_info.getPlayerSuteHaisCount();
					FURITENLOOP: for (; playerSuteHaisCount < suteHaisCount - 1; playerSuteHaisCount++) {
						suteHaiTemp = suteHais[playerSuteHaisCount];
						for (int j = 0; j < indexNum; j++) {
							if (suteHaiTemp.getId() == a_hais[j].getId()) {
								furiten = true;
								break FURITENLOOP;
							}
						}
					}
				}
			}

			if (!furiten) {
				agariScore = m_info.getAgariScore(m_tehai, suteHai);
                han = m_info.getHan();
				if ((m_info.isSecondFan()  && agariScore > 0&& han > 1) ||(!m_info.isSecondFan() && agariScore > 0)) {
					Log.d("Man", "agariScore = " + agariScore);
					m_playerAction.setValidRon(true);
					//eventId[menuNum] = EventId.RON_AGARI;
					//menuNum++;

                    return  EventId.RON_AGARI;
				}
			}

			if (!m_info.isReach() && (m_info.getTsumoRemain() > 0)) {
				if (m_tehai.validPon(suteHai)) {
					m_playerAction.setValidPon(true);
					//eventId[menuNum] = EventId.PON;
					//menuNum++;
                    return EventId.PON;
				}

				if ((relation == -1) || (relation == 3)) {
					if (m_tehai.validChiiRight(suteHai, sarashiHaiRight)) {
						m_playerAction.setValidChiiRight(true, sarashiHaiRight);
						if (chiiCount == 0) {
							iChii = menuNum;
							//eventId[menuNum] = EventId.CHII_RIGHT;
							//menuNum++;
                            return EventId.CHII_RIGHT;
						}
						chiiCount++;
					}

					if (m_tehai.validChiiCenter(suteHai, sarashiHaiCenter)) {
						m_playerAction.setValidChiiCenter(true, sarashiHaiCenter);
						if (chiiCount == 0) {
							iChii = menuNum;
							//eventId[menuNum] = EventId.CHII_CENTER;
							//menuNum++;
                            return EventId.CHII_CENTER;
						}
						chiiCount++;
					}

					if (m_tehai.validChiiLeft(suteHai, sarashiHaiLeft)) {
						m_playerAction.setValidChiiLeft(true, sarashiHaiLeft);
						if (chiiCount == 0) {
							iChii = menuNum;
						//	eventId[menuNum] = EventId.CHII_LEFT;
					//		menuNum++;
                            return EventId.CHII_LEFT;
						}
						chiiCount++;
					}
				}

				if (m_tehai.validDaiMinKan(suteHai)) {
					m_playerAction.setValidDaiMinKan(true);
				//	eventId[menuNum] = EventId.DAIMINKAN;
			//		menuNum++;
                    return  EventId.DAIMINKAN;
				}
			}

		/*	if (menuNum > 0) {
				m_playerAction.setMenuNum(menuNum);
				m_playerAction.setMenuSelect(5);
				m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
				m_playerAction.actionWait();
				int menuSelect = m_playerAction.getMenuSelect();
				if (menuSelect < menuNum) {
					if ((eventId[menuSelect] == EventId.CHII_LEFT) ||
							(eventId[menuSelect] == EventId.CHII_CENTER) ||
							(eventId[menuSelect] == EventId.CHII_RIGHT)) {
						if (chiiCount > 1) {
							while (true) {
								m_playerAction.init();
								// ��͂�҂B
								m_playerAction.setChiiEventId(eventId[iChii]);
								m_playerAction.setState(PlayerAction.STATE_CHII_SELECT);
								m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
								m_playerAction.actionWait();
								EventId chiiEventId = m_playerAction.getChiiEventId();
								m_playerAction.init();
								return chiiEventId;
							}
						}
					}
					m_playerAction.init();
					return eventId[menuSelect];
				}*/
				m_playerAction.init();
		//	}
			break;
		default:
			break;
		}

		return EventId.NAGASHI;
	}

	public int getISutehai() {
		return m_iSutehai;
	}



    //should be refrector
    /**Count format  */
    private CountFormat countFormat = new CountFormat();

    private final static int HYOUKA_SHUU = 1;

    private CountFormat.Combi[] combis = new CountFormat.Combi[10];
    {
        for (int i = 0; i < combis.length; i++)
            combis[i] = new CountFormat.Combi();
    }

    private int thinkSutehai(Hai addHai) {
        int score = 0;
        int maxScore = 0;

        int _m_iSutehai = 13;
        countFormat.setCountFormat(m_tehai, null);
        maxScore = getCountFormatScore(countFormat);
        // System.out.println("score:" + score + ",maxScore:" + maxScore +
        // ",hai:" + UI.idToString(tsumoHai.getId()));
        Hai hai = new Hai();

        Hai[] jyunTehai = new Hai[Tehai.JYUN_TEHAI_LENGTH_MAX];
        for (int i = 0; i < Tehai.JYUN_TEHAI_LENGTH_MAX; i++)
            jyunTehai[i] = new Hai();
        int jyunTehaiLength = m_tehai.getJyunTehaiLength();
        Tehai.copyJyunTehai(jyunTehai, m_tehai.getJyunTehai(), jyunTehaiLength);

        for (int i = 0; i < jyunTehaiLength; i++) {
            m_tehai.copyJyunTehaiIndex(hai, i);
            m_tehai.rmJyunTehai(i);
            countFormat.setCountFormat(m_tehai, addHai);
            score = getCountFormatScore(countFormat);
            System.out.println("score:" + score + ",maxScore:" + maxScore +
                    ",hai: " + hai.getId());
            if (score > maxScore) {
                maxScore = score;
                System.out.println("setSutehaiIdx:" + i);
                _m_iSutehai = i;
            }
            m_tehai.addJyunTehai(hai);
        }

        return _m_iSutehai;
    }

    private final static Hai[] haiTable = new Hai[] {
            new Hai(Hai.ID_WAN_1), new Hai(Hai.ID_WAN_2),
            new Hai(Hai.ID_WAN_3), new Hai(Hai.ID_WAN_4),
            new Hai(Hai.ID_WAN_5), new Hai(Hai.ID_WAN_6),
            new Hai(Hai.ID_WAN_7), new Hai(Hai.ID_WAN_8),
            new Hai(Hai.ID_WAN_9),
            new Hai(Hai.ID_PIN_1), new Hai(Hai.ID_PIN_2),
            new Hai(Hai.ID_PIN_3), new Hai(Hai.ID_PIN_4),
            new Hai(Hai.ID_PIN_5), new Hai(Hai.ID_PIN_6),
            new Hai(Hai.ID_PIN_7), new Hai(Hai.ID_PIN_8),
            new Hai(Hai.ID_PIN_9),
            new Hai(Hai.ID_SOU_1), new Hai(Hai.ID_SOU_2),
            new Hai(Hai.ID_SOU_3), new Hai(Hai.ID_SOU_4),
            new Hai(Hai.ID_SOU_5), new Hai(Hai.ID_SOU_6),
            new Hai(Hai.ID_SOU_7), new Hai(Hai.ID_SOU_8),
            new Hai(Hai.ID_SOU_9),
            new Hai(Hai.ID_TON), new Hai(Hai.ID_NAN),
            new Hai(Hai.ID_SHA), new Hai(Hai.ID_PE),
            new Hai(Hai.ID_HAKU), new Hai(Hai.ID_HATSU),
            new Hai(Hai.ID_CHUN) };

    private boolean thinkReach(Tehai tehai) {
        if (m_info.getTsumoRemain() >= 4) {
            for (Hai hai : haiTable) {
                countFormat.setCountFormat(tehai, hai);
                int han = m_info.getHan();
                if ((m_info.isSecondFan() && countFormat.getCombis(combis) > 0 && han>1) ||(!m_info.isSecondFan() && countFormat.getCombis(combis) > 0)) {
                    //if (countFormat.getCombis(combis) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private int getCountFormatScore(CountFormat countFormat) {
        int score = 0;

        for (int i = 0; i < countFormat.m_countNum; i++) {
            if ((countFormat.m_counts[i].m_noKind & Hai.KIND_SHUU) != 0) {
                score += countFormat.m_counts[i].m_num * HYOUKA_SHUU;
            }

            if (countFormat.m_counts[i].m_num == 2) {
                score += 4;
            }

            if (countFormat.m_counts[i].m_num >= 3) {
                score += 8;
            }

            if ((countFormat.m_counts[i].m_noKind & Hai.KIND_SHUU) > 0) {
                if ((countFormat.m_counts[i].m_noKind + 1) == countFormat.m_counts[i + 1].m_noKind) {
                    score += 4;
                }

                if ((countFormat.m_counts[i].m_noKind + 2) == countFormat.m_counts[i + 2].m_noKind) {
                    score += 4;
                }
            }
        }

        return score;
    }

}
