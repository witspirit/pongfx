package be.witspirit.pongfx.keyboard;

import be.witspirit.pongfx.keyboard.KeyAction;

/**
 * Simple action with a function for press and a function for release
 */
public class PressAndReleaseAction implements KeyAction {

    private final Runnable pressAction;
    private final Runnable releaseAction;

    public PressAndReleaseAction(Runnable pressAction, Runnable releaseAction) {
        this.pressAction = pressAction;
        this.releaseAction = releaseAction;
    }

    @Override
    public void onPress() {
        pressAction.run();
    }

    @Override
    public void onRelease() {
        releaseAction.run();
    }
}
