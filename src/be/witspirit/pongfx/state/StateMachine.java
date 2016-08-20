package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Ball;
import be.witspirit.pongfx.Player;
import javafx.beans.property.StringProperty;
import javafx.geometry.Rectangle2D;

/**
 * Keeps track of the Current Game State, manages state transitions and acts as a gateway to the game entities
 */
public class StateMachine {

    private Rectangle2D screenBounds;

    private final Ball ball;
    private final Player player1;
    private final Player player2;
    private final StringProperty p1Feedback;
    private final StringProperty p2Feedback;

    private GameState currentState;

    public StateMachine(Rectangle2D screenBounds, Ball ball, Player player1, Player player2, StringProperty p1Feedback, StringProperty p2Feedback) {
        this.screenBounds = screenBounds;
        this.ball = ball;
        this.player1 = player1;
        this.player2 = player2;
        this.p1Feedback = p1Feedback;
        this.p2Feedback = p2Feedback;

        newState(new FreshGame());
    }

    public void newState(GameState newState) {
        if (newState != currentState) {
            // Actual state change
            newState.setStateMachine(this);
            this.currentState = newState;
            this.currentState.stateEntry();
        }
    }

    public void launch() {
        currentState.launch();
    }

    public void evaluateGame() {
        currentState.evaluateGame();
    }


    public Rectangle2D getScreenBounds() {
        return screenBounds;
    }

    public Ball getBall() {
        return ball;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public StringProperty p1FeedbackProperty() {
        return p1Feedback;
    }

    public StringProperty p2FeedbackProperty() {
        return p2Feedback;
    }
}
