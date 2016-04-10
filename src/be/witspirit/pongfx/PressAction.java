package be.witspirit.pongfx;

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
