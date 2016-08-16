package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Ball;
import be.witspirit.pongfx.Player;
import be.witspirit.pongfx.RandomSource;
import javafx.geometry.Rectangle2D;

/**
 * Base class for all Game States, defines the basic contract for all GameStates
 */
public abstract class GameState {

    protected StateMachine stateMachine;

    protected Ball ball;
    protected Player player1;
    protected Player player2;
    protected Rectangle2D screenBounds;

    protected RandomSource random = new RandomSource();

    public StateMachine getStateMachine() {
        return stateMachine;
    }

    public GameState setStateMachine(StateMachine stateMachine) {
        this.stateMachine = stateMachine;
        this.ball = stateMachine.getBall();
        this.player1 = stateMachine.getPlayer1();
        this.player2 = stateMachine.getPlayer2();
        this.screenBounds = stateMachine.getScreenBounds();
        return this;
    }

    public void stateEntry() {
        // Default no-op
    }

    public void evaluateGame() {
        // Default no-op
    }

    public void launch() {
        // Default no-op
    }

}
