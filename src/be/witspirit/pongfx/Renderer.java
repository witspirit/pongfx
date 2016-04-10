package be.witspirit.pongfx;

/**
 * Interface to mark something as offering a render method
 */
@FunctionalInterface
public interface Renderer {

    void update(double elapsedSeconds);
}
