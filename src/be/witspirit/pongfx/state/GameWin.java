package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Player;
import javafx.beans.property.StringProperty;

/**
 * The game has been won by the provided player. We provide feedback and prepare for a fresh game.
 */
public class GameWin extends GameState {

    private Player winningPlayer;

    public GameWin(Player winningPlayer) {
        this.winningPlayer = winningPlayer;
    }

    @Override
    public void stateEntry() {
        StringProperty winner;
        StringProperty loser;
        if (winningPlayer == player1) {
            winner = stateMachine.p1FeedbackProperty();
            loser = stateMachine.p2FeedbackProperty();
        } else {
            winner = stateMachine.p2FeedbackProperty();
            loser = stateMachine.p1FeedbackProperty();
        }

        winner.set("Winner !");
        loser.set("Loser :-(");
    }

    @Override
    public void launch() {
        stateMachine.p1FeedbackProperty().set("");
        stateMachine.p2FeedbackProperty().set("");
        stateMachine.newState(new FreshGame());
    }
}
