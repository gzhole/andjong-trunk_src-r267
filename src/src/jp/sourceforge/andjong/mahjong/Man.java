package jp.sourceforge.andjong.mahjong;

import android.util.Log;

public class Man implements EventIf {
	/** The information provided to the player */
	private Info m_info;

	/** Index of discarded tile  */
	private int m_iSutehai = 0;

	private String name;

	public String getName() {
		return name;
	}

	private PlayerAction m_playerAction;

	public Man(Info info, String name, PlayerAction playerAction) {
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

			if (!m_info.isReach() && (m_info.getTsumoRemain() >= 4)) {
				indexNum = m_info.getReachIndexs(m_tehai, tsumoHai, indexs);
				if (indexNum > 0) {
					m_playerAction.setValidReach(true);
					m_playerAction.m_indexs = indexs;
					m_playerAction.m_indexNum = indexNum;
					eventId[menuNum] = EventId.REACH;
					menuNum++;
				}
			}

			agariScore = m_info.getAgariScore(m_tehai, tsumoHai);
			if (agariScore > 0) {
				Log.d("Man", "agariScore = " + agariScore);
				m_playerAction.setValidTsumo(true);
				m_playerAction.setDispMenu(true);
				eventId[menuNum] = EventId.TSUMO_AGARI;
				menuNum++;
			}

			// ���������B���[�`��̃J���������Ȃ��B
			if (!m_info.isReach()) {
				kanNum = m_tehai.validKan(tsumoHai, kanHais);
				if (kanNum > 0) {
					m_playerAction.setValidKan(true, kanHais, kanNum);
					eventId[menuNum] = EventId.ANKAN;
					menuNum++;
				}
			}

			if (m_info.isReach()) {
				if (menuNum == 0) {
					m_playerAction.init();
					this.m_iSutehai = 13;
					return EventId.SUTEHAI;
				}
			}

			m_playerAction.setMenuNum(menuNum);
			while (true) {
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
			}
			m_playerAction.init();
			this.m_iSutehai = sutehaiIdx;
			return EventId.SUTEHAI;
		case SELECT_SUTEHAI:
			m_info.copyTehai(m_tehai, m_info.getJikaze());
			jyunTehaiLength = m_tehai.getJyunTehaiLength();
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
			Log.d("Man", "sutehaiIdx = " + sutehaiIdx);
			m_playerAction.init();
			this.m_iSutehai = sutehaiIdx;
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
				if (agariScore > 0) {
					m_playerAction.setDispMenu(true);
					m_playerAction.setValidRon(true);
					m_playerAction.setMenuNum(1);
					m_playerAction.setMenuSelect(5);
					m_playerAction.setState(PlayerAction.STATE_RON_SELECT);
					m_info.postUiEvent(EventId.UI_INPUT_PLAYER_ACTION, a_kazeFrom, a_kazeTo);
					m_playerAction.actionWait();
					int menuSelect = m_playerAction.getMenuSelect();
					if (menuSelect < 1) {
						m_playerAction.init();
						return EventId.RON_AGARI;
					}
					m_playerAction.init();
				}
			}
			break;
		case SUTEHAI:
		case REACH:
			if (a_kazeFrom == m_info.getJikaze()) {
				return EventId.NAGASHI;
			}
			//Log.e("SUTEHAI", "fromKaze = " + a_kazeFrom + ", toKaze = " + a_kazeTo);

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
				if (agariScore > 0) {
					Log.d("Man", "agariScore = " + agariScore);
					m_playerAction.setValidRon(true);
					eventId[menuNum] = EventId.RON_AGARI;
					menuNum++;
				}
			}

			if (!m_info.isReach() && (m_info.getTsumoRemain() > 0)) {
				if (m_tehai.validPon(suteHai)) {
					m_playerAction.setValidPon(true);
					eventId[menuNum] = EventId.PON;
					menuNum++;
				}

				if ((relation == -1) || (relation == 3)) {
					if (m_tehai.validChiiRight(suteHai, sarashiHaiRight)) {
						m_playerAction.setValidChiiRight(true, sarashiHaiRight);
						if (chiiCount == 0) {
							iChii = menuNum;
							eventId[menuNum] = EventId.CHII_RIGHT;
							menuNum++;
						}
						chiiCount++;
					}

					if (m_tehai.validChiiCenter(suteHai, sarashiHaiCenter)) {
						m_playerAction.setValidChiiCenter(true, sarashiHaiCenter);
						if (chiiCount == 0) {
							iChii = menuNum;
							eventId[menuNum] = EventId.CHII_CENTER;
							menuNum++;
						}
						chiiCount++;
					}

					if (m_tehai.validChiiLeft(suteHai, sarashiHaiLeft)) {
						m_playerAction.setValidChiiLeft(true, sarashiHaiLeft);
						if (chiiCount == 0) {
							iChii = menuNum;
							eventId[menuNum] = EventId.CHII_LEFT;
							menuNum++;
						}
						chiiCount++;
					}
				}

				if (m_tehai.validDaiMinKan(suteHai)) {
					m_playerAction.setValidDaiMinKan(true);
					eventId[menuNum] = EventId.DAIMINKAN;
					menuNum++;
				}
			}

			if (menuNum > 0) {
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
				}
				m_playerAction.init();
			}
			break;
		default:
			break;
		}

		return EventId.NAGASHI;
	}

	public int getISutehai() {
		return m_iSutehai;
	}
}
