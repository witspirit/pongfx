package sample;

import javafx.animation.AnimationTimer;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Representation of the Pong Ball
 */
public class Ball implements Renderer {

    private static final double SPEED = 200.0; // Pixels / Second

    private Circle node;
    private Rectangle2D bounds;
    private DoubleProperty direction = new SimpleDoubleProperty(0.0);
    private double velocity = 0.0; // Pixels / Second
    private Random random;

    private Ball(Rectangle2D bounds) {
        node = new Circle(bounds.getWidth()/2, bounds.getHeight()/2, 25, Paint.valueOf("WHITE"));
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
        direction.set(random.nextDouble() * (Math.PI * 2));
        velocity = SPEED;
    }

    public void freeze() {
        direction.set(0.0);
        velocity = 0.0;
    }

    public void reset() {
        node.setCenterX(bounds.getWidth()/2);
        node.setCenterY(bounds.getHeight()/2);
    }

    @Override
    public void update(double elapsedSeconds) {
        double newX = node.getCenterX() + Math.cos(direction.doubleValue()) * (velocity * elapsedSeconds);
        double newY = node.getCenterY() + Math.sin(direction.doubleValue()) * (velocity * elapsedSeconds);

        if (newX <= 0.0) {
            // Bounce left
            System.out.println("Left hit detected: "+direction);
        } else if (newX >= bounds.getWidth()) {
            // Bounce right
            System.out.println("Right hit detected: "+direction);
        } else if (newY <= 0.0) {
            // Bounce top
            System.out.println("Top hit detected: "+direction);
        } else if (newY >= bounds.getHeight()) {
            // Bounce bottom
            System.out.println("Bottom hit detected: "+direction);
        }


        node.setCenterX(newX);
        node.setCenterY(newY);
    }

    public double getDirection() {
        return direction.get();
    }

    public DoubleProperty directionProperty() {
        return direction;
    }
}
