package sample;

import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.util.Random;

/**
 * Representation of the Pong Ball
 */
public class Ball {

    private static final double SPEED = 200.0; // Pixels / Second

    private Circle node;
    private Rectangle2D bounds;
    private AnimationTimer timer;
    private long lastUpdate = System.nanoTime();
    private double direction = 0.0;
    private double velocity = 0.0; // Pixels / Second
    private Random random;

    private Ball(Rectangle2D bounds) {
        node = new Circle(bounds.getWidth()/2, bounds.getHeight()/2, 25, Paint.valueOf("WHITE"));
        this.bounds = bounds;

        random = new Random();

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedSeconds = (now - lastUpdate) / 1000000000.0;
                move(elapsedSeconds);
                lastUpdate = now;
            }
        };
        timer.start();


    }

    private void move(double elapsedSeconds) {
        double newX = node.getCenterX() + Math.cos(direction) * (velocity * elapsedSeconds);
        double newY = node.getCenterY() + Math.sin(direction) * (velocity * elapsedSeconds);

        if (newX <= 0.0) {
            // Bounce left
        } else if (newX >= bounds.getWidth()) {
            // Bounce right
        } else if (newY <= 0.0) {
            // Bounce top
        } else if (newY >= bounds.getHeight()) {
            // Bounce bottom
        }


        node.setCenterX(newX);
        node.setCenterY(newY);
    }

    public static Ball create(Rectangle2D bounds) {
        return new Ball(bounds);
    }

    public Circle getNode() {
        return node;
    }

    public void launch() {
        direction = random.nextDouble() * (Math.PI*2);
        velocity = SPEED;
    }

    public void freeze() {
        direction = 0.0;
        velocity = 0.0;
    }

    public void reset() {
        node.setCenterX(bounds.getWidth()/2);
        node.setCenterY(bounds.getHeight()/2);
    }
}
