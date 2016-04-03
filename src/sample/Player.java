package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.function.Function;

/**
 * Represents a Player
 */
public class Player implements Renderer {
    private static final int PLAYER_WIDTH = 50;
    private static final int PLAYER_HEIGHT = 200;
    private static final Paint PLAYER_COLOR = Paint.valueOf("WHITE");

    private static final int SPEED = 20; // Pixels / Second

    private Point2D startPosition;
    private Rectangle node;

    private double velocity = 0.0;

    private IntegerProperty score = new SimpleIntegerProperty(0);

    public Player(Point2D startPosition) {
        this.startPosition = startPosition;

        node = new Rectangle(PLAYER_WIDTH, PLAYER_HEIGHT, PLAYER_COLOR);
        setCenterPosition(startPosition);
    }

    public void up() {
        velocity = -SPEED;
    }

    public void down() {
        velocity = +SPEED;
    }

    public void stopUp() {
        if (velocity <= -SPEED) { // Only when still going up we will stop
            velocity = 0.0;
        }
    }

    public void stopDown() {
        if (velocity >= +SPEED) { // Only when still going down we will stop
            velocity = 0.0;
        }
    }

    public void reset() {
        stopUp();
        stopDown();
        setCenterPosition(startPosition);
    }

    public void setCenterPosition(Point2D position) {
        node.setX(position.getX()-(PLAYER_WIDTH/2));
        node.setY(position.getY()-(PLAYER_HEIGHT/2));
    }

    public void setMinYPosition(double minPosition) {
        node.setY(minPosition);
    }

    public void setMaxYPosition(double maxPosition) {
        node.setY(maxPosition-PLAYER_HEIGHT);
    }

    @Override
    public void update(double elapsedSeconds) {
        double newY = node.getY() + velocity;
        node.setY(newY);
    }

    public Bounds getBounds() {
        return node.getBoundsInParent();
    }

    public Rectangle getNode() {
        return node;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }
}
