package sample;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Tiny wrapper around the AnimationTimer
 */
public class AnimationLoop {
    private long lastUpdate = System.nanoTime();
    private AnimationTimer timer;

    private Runnable evaluator;
    private List<Renderer> renderers = new ArrayList<>();


    public AnimationLoop(Runnable evaluator) {
        this.evaluator = evaluator == null ? () -> {} : evaluator;
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedSeconds = (now - lastUpdate) / 1000000000.0;
                render(elapsedSeconds);
                lastUpdate = now;
            }
        };
    }

    public void start() {
        timer.start();
    }

    public void register(Renderer renderer) {
        renderers.add(renderer);
    }

    private void render(double elapsedSeconds) {
        renderers.forEach(c -> c.update(elapsedSeconds));
        evaluator.run();
    }
}
