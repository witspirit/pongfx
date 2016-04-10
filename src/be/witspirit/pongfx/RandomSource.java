package be.witspirit.pongfx;

import java.util.Random;

/**
 * Small class to provide random values for the game
 */
public class RandomSource {

    private Random random = new Random();

    public double leftAngle() {
        return Math.PI + rightAngle();
    }

    public double rightAngle() {
        return (random.nextDouble()-0.5) * (Math.PI/2);
    }

    public double angle() {
        return random.nextBoolean() ? leftAngle() : rightAngle();
    }
}
