package be.witspirit.pongfx;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Representation of the Pong Ball
 */
public class Ball implements Renderer {

    private static final double SPEED = 1500.0; // Pixels / Second
    private static final int RADIUS = 25;

    private Point2D startPosition;

    private Circle node;

    private DoubleProperty xVelocity = new SimpleDoubleProperty(0.0);
    private DoubleProperty yVelocity = new SimpleDoubleProperty(0.0);

    public Ball(Point2D startPosition) {
        this.startPosition = startPosition;

        node = new Circle(startPosition.getX(), startPosition.getY(), RADIUS, Paint.valueOf("WHITE"));
    }

    public Circle getNode() {
        return node;
    }

    public void launch(double angle) {
        xVelocity.set(SPEED * Math.cos(angle));
        yVelocity.set(SPEED * Math.sin(angle));
    }

    public void freeze() {
        xVelocity.set(0.0);
        yVelocity.set(0.0);
    }

    public void reset() {
        node.setCenterX(startPosition.getX());
        node.setCenterY(startPosition.getY());
    }

    @Override
    public void update(double elapsedSeconds) {
        double vX = xVelocity.doubleValue();
        double vY = yVelocity.doubleValue();

        double newX = node.getCenterX() + (vX * elapsedSeconds);
        double newY = node.getCenterY() + (vY * elapsedSeconds);

        node.setCenterX(newX);
        node.setCenterY(newY);
    }

    public void goRight() {
        double vX = xVelocity.doubleValue();
        if (vX <= 0) {
            xVelocity.set(-vX);
        }
    }

    public void goLeft() {
        double vX = xVelocity.doubleValue();
        if (vX >= 0) {
            xVelocity.set(-vX);
        }
    }

    public void goUp() {
        double vY = yVelocity.doubleValue();
        if (vY >= 0) {
            yVelocity.set(-vY);
        }
    }

    public void goDown() {
        double vY = yVelocity.doubleValue();
        if (vY <= 0) {
            yVelocity.set(-vY);
        }
    }

    public Bounds getBounds() {
        return node.getBoundsInParent();
    }

    public DoubleProperty xVelocityProperty() {
        return xVelocity;
    }

    public DoubleProperty yVelocityProperty() {
        return yVelocity;
    }
}
