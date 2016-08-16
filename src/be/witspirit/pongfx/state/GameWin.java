package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Player;

/**
 * The game has been won by the provided player. We provide feedback and prepare for a fresh game.
 */
public class GameWin extends GameState {

    private Player winningPlayer;

    public GameWin(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    @Override
    public void launch() {
        stateMachine.newState(new FreshGame());
    }
}
