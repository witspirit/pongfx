package sample;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Representation of the Pong Ball
 */
public class Ball implements Renderer {

    private static final double SPEED = 600.0; // Pixels / Second
    private static final int RADIUS = 25;

    private Circle node;
    private Rectangle2D bounds;
    private Random random;

    private DoubleProperty xVelocity = new SimpleDoubleProperty(0.0);
    private DoubleProperty yVelocity = new SimpleDoubleProperty(0.0);

    private Ball(Rectangle2D bounds) {
        node = new Circle(bounds.getWidth()/2, bounds.getHeight()/2, RADIUS, Paint.valueOf("WHITE"));
        this.bounds = bounds;

        random = new Random();
    }

    public static Ball create(Rectangle2D bounds) {
        return new Ball(bounds);
    }

    public Circle getNode() {
        return node;
    }

    public void launch() {
        double randomAngle = random.nextDouble() * (Math.PI * 2);

        xVelocity.set(SPEED * Math.cos(randomAngle));
        yVelocity.set(SPEED * Math.sin(randomAngle));
    }

    public void freeze() {
        xVelocity.set(0.0);
        yVelocity.set(0.0);
    }

    public void reset() {
        node.setCenterX(bounds.getWidth()/2);
        node.setCenterY(bounds.getHeight()/2);
    }

    @Override
    public void update(double elapsedSeconds) {
        double vX = xVelocity.doubleValue();
        double vY = yVelocity.doubleValue();

        double newX = node.getCenterX() + (vX * elapsedSeconds);
        double newY = node.getCenterY() + (vY * elapsedSeconds);

        if (newX-RADIUS <= bounds.getMinX()) {
            // Bounce left
            System.out.println("Left hit detected");

            goRight();
        } else if (newX+RADIUS >= bounds.getMaxX()) {
            // Bounce right
            System.out.println("Right hit detected");

            goLeft();
        } else if (newY-RADIUS <= bounds.getMinY()) {
            // Bounce top
            System.out.println("Top hit detected");

            goDown();

        } else if (newY+RADIUS >= bounds.getMaxY()) {
            // Bounce bottom
            System.out.println("Bottom hit detected");

            goUp();
        }

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

    private void goUp() {
        double vY = yVelocity.doubleValue();
        if (vY >= 0) {
            yVelocity.set(-vY);
        }
    }

    private void goDown() {
        double vY = yVelocity.doubleValue();
        if (vY <= 0) {
            yVelocity.set(-vY);
        }
    }

    public Bounds getBounds() {
        return node.getBoundsInParent();
    }

    public double getxVelocity() {
        return xVelocity.get();
    }

    public DoubleProperty xVelocityProperty() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity.get();
    }

    public DoubleProperty yVelocityProperty() {
        return yVelocity;
    }
}
