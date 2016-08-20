package be.witspirit.pongfx.keyboard;

/**
 * Simple interface to encapsulate an action for a certain key
 */
public interface KeyAction {

    void onPress();

    void onRelease();
}
