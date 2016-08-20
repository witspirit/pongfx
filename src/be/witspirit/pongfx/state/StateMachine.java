package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Ball;
import be.witspirit.pongfx.Player;
import be.witspirit.pongfx.keyboard.KeyAction;
import be.witspirit.pongfx.keyboard.NoOpKeyAction;
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

    private KeyAction launchAction = NoOpKeyAction.INSTANCE;
    private KeyAction p1MoveUpAction = NoOpKeyAction.INSTANCE;
    private KeyAction p1MoveDownAction = NoOpKeyAction.INSTANCE;
    private KeyAction p2MoveUpAction = NoOpKeyAction.INSTANCE;
    private KeyAction p2MoveDownAction = NoOpKeyAction.INSTANCE;

    // Special operations that perhaps should not be allowed, but offer a troubleshooting functionality
    private KeyAction ballResetAction = NoOpKeyAction.INSTANCE;
    private KeyAction ballFreezeAction = NoOpKeyAction.INSTANCE;

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

    public KeyAction getLaunchAction() {
        return launchAction;
    }

    public StateMachine setLaunchAction(KeyAction launchAction) {
        this.launchAction = launchAction;
        return this;
    }

    public KeyAction getP1MoveUpAction() {
        return p1MoveUpAction;
    }

    public StateMachine setP1MoveUpAction(KeyAction p1MoveUpAction) {
        this.p1MoveUpAction = p1MoveUpAction;
        return this;
    }

    public KeyAction getP1MoveDownAction() {
        return p1MoveDownAction;
    }

    public StateMachine setP1MoveDownAction(KeyAction p1MoveDownAction) {
        this.p1MoveDownAction = p1MoveDownAction;
        return this;
    }

    public KeyAction getP2MoveUpAction() {
        return p2MoveUpAction;
    }

    public StateMachine setP2MoveUpAction(KeyAction p2MoveUpAction) {
        this.p2MoveUpAction = p2MoveUpAction;
        return this;
    }

    public KeyAction getP2MoveDownAction() {
        return p2MoveDownAction;
    }

    public StateMachine setP2MoveDownAction(KeyAction p2MoveDownAction) {
        this.p2MoveDownAction = p2MoveDownAction;
        return this;
    }

    public KeyAction getBallResetAction() {
        return ballResetAction;
    }

    public StateMachine setBallResetAction(KeyAction ballResetAction) {
        this.ballResetAction = ballResetAction;
        return this;
    }

    public KeyAction getBallFreezeAction() {
        return ballFreezeAction;
    }

    public StateMachine setBallFreezeAction(KeyAction ballFreezeAction) {
        this.ballFreezeAction = ballFreezeAction;
        return this;
    }
}
