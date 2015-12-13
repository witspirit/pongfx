package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.function.Function;

/**
 * Represents a Player
 */
public class Player {
    public static final int PLAYER_WIDTH = 50;
    public static final int PLAYER_HEIGHT = 200;
    public static final Paint PLAYER_COLOR = Paint.valueOf("WHITE");
    public static final double PLAYER_SCREEN_OFFSET = 100;
    public static final int MOVE_STEP = 20;

    public enum Position {
        LEFT(bounds -> PLAYER_SCREEN_OFFSET),
        RIGHT(bounds -> bounds.getWidth()- PLAYER_SCREEN_OFFSET -PLAYER_WIDTH);

        private Function<Rectangle2D, Double> computeX;

        Position(Function<Rectangle2D, Double> computeX) {
            this.computeX = computeX;
        }

        public double x(Rectangle2D bounds) {
            return computeX.apply(bounds);
        }
    }

    private Rectangle node;
    private Rectangle2D bounds;


    private Player(Position position, Rectangle2D bounds) {
        node = new Rectangle(PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_COLOR);
        node.setX(position.x(bounds));
        node.setY(bounds.getHeight()/2-PLAYER_HEIGHT/2);

        this.bounds = bounds;
    }

    public static Player left(Rectangle2D bounds) {
        return new Player(Position.LEFT, bounds);
    }

    public static Player right(Rectangle2D bounds) {
        return new Player(Position.RIGHT, bounds);
    }

    public void up() {
        if (node.getY() <= 0) {
            node.setY(0);
        } else {
            node.setY(node.getY()- MOVE_STEP);
        }

    }

    public void down() {
        if (node.getY()+PLAYER_HEIGHT >= bounds.getHeight() ) {
            node.setY(bounds.getHeight()-PLAYER_HEIGHT);
        } else {
            node.setY(node.getY() + MOVE_STEP);
        }

    }

    public Rectangle getNode() {
        return node;
    }
}
