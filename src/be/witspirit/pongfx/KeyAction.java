package be.witspirit.pongfx;

/**
 * Simple interface to encapsulate an action for a certain key
 */
public interface KeyAction {

    void onPress();

    void onRelease();
}
