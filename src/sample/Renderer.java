package sample;

/**
 * Interface to mark something as offering a render method
 */
@FunctionalInterface
public interface Renderer {

    void update(double elapsedSeconds);
}
