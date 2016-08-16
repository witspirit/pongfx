package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Player;

/**
 * Passed in player as won a battle. Time to evaluate whether the player has won the entire game. Prepare
 * for the next battle or game.
 */
public class BattleWin extends GameState {
    private int targetScore = 5;

    private Player winningPlayer;

    public BattleWin(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    @Override
    public void stateEntry() {
        winningPlayer.scoreProperty().set(winningPlayer.scoreProperty().get()+1);

        if (winningPlayer.scoreProperty().get() >= targetScore) {
            stateMachine.newState(new GameWin(winningPlayer));
        }
    }

    @Override
    public void launch() {
        resetToStartPosition();
        ball.launch(winningPlayer == player1 ? random.leftAngle() : random.rightAngle());
        stateMachine.newState(new Battle());
    }

    private void resetToStartPosition() {
        ball.freeze();
        ball.reset();
        player1.reset();
        player2.reset();
    }
}
