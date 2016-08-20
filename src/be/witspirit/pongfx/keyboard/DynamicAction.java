package be.witspirit.pongfx.keyboard;

/**
 * Delegates to another key-action that is resolved at runtime by the KeyActionProvider
 */
public class DynamicAction implements KeyAction {
    private final KeyActionProvider actionProvider;

    public DynamicAction(KeyActionProvider actionProvider) {
        this.actionProvider = actionProvider;
    }

    @Override
    public void onPress() {
        actionProvider.keyAction().onPress();
    }

    @Override
    public void onRelease() {
        actionProvider.keyAction().onRelease();
    }
}
