package jp.sourceforge.andjong.mahjong;

import jp.sourceforge.andjong.mahjong.AgariScore.AgariInfo;

public class InfoUi extends Info {
	private PlayerAction mPlayerAction;

	public InfoUi(Mahjong game, PlayerAction playerAction) {
		super(game);
		this.setPlayerAction(playerAction);
	}

	public Hai[] getUraDoraHais() {
		return game.getYama().getUraDoraHais();
	}

	public int getManKaze() {
		return game.getManKaze();
	}

	/**
	 * Tile hand I will copy the.
	 *
	 * @param tehai
	 *            Hand
	 * @param kaze
	 *            Wind
	 */
	public void copyTehai(Tehai tehai, int kaze) {
		game.copyTehaiUi(tehai, kaze);
	}

	public void setPlayerAction(PlayerAction playerAction) {
		this.mPlayerAction = playerAction;
	}

	public PlayerAction getPlayerAction() {
		return mPlayerAction;
	}

	/**
	 * get the player index of Okoshi-ka. (Qi jia)
	 *
	 * @return player index of Okoshi-ka
	 */
	public int getChiichaIdx() {
		return game.getChiichaIdx();
	}

	public AgariInfo getAgariInfo() {
		return game.getAgariInfo();
	}

	public boolean[] getTenpai() {
		return game.getTenpai();
	}

    public String getCurrentPlayerName() {
        if (game!=null && game.getActivePlayer()!=null) {
            return game.getActivePlayer().getEventIf().getName();
        }
        return null;

    }

    public String getLastIssuedPlayer() {
        return lastIssuedPlayer;
    }

    public void setLastIssuedPlayer(String lastIssuedPlayer) {
        this.lastIssuedPlayer = lastIssuedPlayer;
    }

    private String lastIssuedPlayer;
}
