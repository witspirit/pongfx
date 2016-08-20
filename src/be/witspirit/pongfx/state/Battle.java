package be.witspirit.pongfx.state;

import be.witspirit.pongfx.Player;


/**
 * A single battle is ongoing, the ball is moving...
 */
public class Battle extends GameState {

    @Override
    public void evaluateGame() {
        if (ball.getBounds().getMinX() <= screenBounds.getMinX()) {
            // Bounce left
            System.out.println("Left hit detected");

            stateMachine.newState(new BattleWin(player2));
        } else if (ball.getBounds().getMaxX() >= screenBounds.getMaxX()) {
            // Bounce right
            System.out.println("Right hit detected");

            stateMachine.newState(new BattleWin(player1));
        } else if (ball.getBounds().getMinY() <= screenBounds.getMinY()) {
            // Bounce top
            System.out.println("Top hit detected");

            ball.goDown();
        } else if (ball.getBounds().getMaxY() >= screenBounds.getMaxY()) {
            // Bounce bottom
            System.out.println("Bottom hit detected");

            ball.goUp();
        }

        onBounce(player1, ball::goRight);
        onBounce(player2, ball::goLeft);

        checkScreenBounds(player1);
        checkScreenBounds(player2);
    }

    private void onBounce(Player player, Runnable action) {
        if (player.getBounds().intersects(ball.getBounds())) {
            action.run();
        }
    }

    private void checkScreenBounds(Player player) {
        if (player.getBounds().getMinY() <= screenBounds.getMinY()) {
            player.setMinYPosition(screenBounds.getMinY());
        } else if (player.getBounds().getMaxY() >= screenBounds.getMaxY()) {
            player.setMaxYPosition(screenBounds.getMaxY());
        }
    }
}
