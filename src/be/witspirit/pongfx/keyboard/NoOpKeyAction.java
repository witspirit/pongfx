package be.witspirit.pongfx.keyboard;

/**
 * Just a dud implementation of the KeyAction
 */
public class NoOpKeyAction implements KeyAction {
    public static final NoOpKeyAction INSTANCE = new NoOpKeyAction();

    @Override
    public void onPress() {
        // NO-OP
    }

    @Override
    public void onRelease() {
        // NO-OP
    }
}
