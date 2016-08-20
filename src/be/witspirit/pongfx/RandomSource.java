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
        double baseAngle = random.nextDouble() - 0.5; // -0.5 to ensure we have between -45 and +45
        // This part is to ensure that we don't have too flat a ball launch as it leads to a boring game
        if (baseAngle < 0) {
            baseAngle = baseAngle - 0.1;
        } else {
            baseAngle = baseAngle + 0.1;
        }
        return baseAngle * (Math.PI/2);
    }

    public double angle() {
        return random.nextBoolean() ? leftAngle() : rightAngle();
    }
}
