package be.witspirit.pongfx;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Allows to map keys to actions
 */
public class KeyMap {
    private final KeyAction NO_OP = new KeyAction() {
        @Override
        public void onPress() {
            // NO-OP
        }

        @Override
        public void onRelease() {
            // NO-OP
        }
    };

    private final Map<KeyCode, KeyAction> actions = new HashMap<>();


    public KeyMap register(KeyCode keyCode, KeyAction action) {
        actions.put(keyCode, action);
        return this;
    }

    public KeyMap listenOn(Scene scene) {
        scene.setOnKeyPressed(handle(a -> a.onPress()));
        scene.setOnKeyReleased(handle(a -> a.onRelease()));
        return this;
    }

    private EventHandler<KeyEvent> handle(Consumer<KeyAction> actionInvoker) {
        return ke -> actionInvoker.accept(actions.getOrDefault(ke.getCode(), NO_OP));
    }
}
