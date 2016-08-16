package be.witspirit.pongfx.state;

/**
 * A fresh game. All players have 0 score and the ball isn't yet moving.
 */
public class FreshGame extends GameState {

    @Override
    public void stateEntry() {
        resetGame();
    }

    private void resetGame() {
        player1.scoreProperty().set(0);
        player2.scoreProperty().set(0);
        resetToStartPosition();
    }

    private void resetToStartPosition() {
        ball.freeze();
        ball.reset();
        player1.reset();
        player2.reset();
    }

    @Override
    public void launch() {
        ball.launch(random.angle());
        stateMachine.newState(new Battle());
    }
}
