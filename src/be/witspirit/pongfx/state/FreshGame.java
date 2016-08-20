package be.witspirit.pongfx.state;

import be.witspirit.pongfx.keyboard.PressAction;
import be.witspirit.pongfx.keyboard.PressAndReleaseAction;

/**
 * A fresh game. All players have 0 score and the ball isn't yet moving.
 */
public class FreshGame extends GameState {

    @Override
    public void stateEntry() {
        resetGame();
    }

    private void resetGame() {
        // Reset key setup
        stateMachine.setLaunchAction(new PressAction(stateMachine::launch));
        stateMachine.setP1MoveUpAction(new PressAndReleaseAction(player1::up, player1::stopUp));
        stateMachine.setP1MoveDownAction(new PressAndReleaseAction(player1::down, player1::stopDown));
        stateMachine.setP2MoveUpAction(new PressAndReleaseAction(player2::up, player2::stopUp));
        stateMachine.setP2MoveDownAction(new PressAndReleaseAction(player2::down, player2::stopDown));
        stateMachine.setBallResetAction(new PressAction(ball::reset));
        stateMachine.setBallFreezeAction(new PressAction(ball::freeze));

        // Reset scores
        player1.scoreProperty().set(0);
        player2.scoreProperty().set(0);

        // Make sure we have every game entity in its start location
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
