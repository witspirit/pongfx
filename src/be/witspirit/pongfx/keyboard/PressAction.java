package be.witspirit.pongfx.keyboard;

import be.witspirit.pongfx.keyboard.KeyAction;

/**
 * Only does something on Press
 */
public class PressAction implements KeyAction {

    private final Runnable action;

    public PressAction(Runnable action) {
        this.action = action;
    }

    @Override
    public void onPress() {
        action.run();
    }

    @Override
    public void onRelease() {
        // No-Op
    }
}
