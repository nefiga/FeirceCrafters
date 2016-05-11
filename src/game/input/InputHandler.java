package game.input;

import org.lwjgl.input.Keyboard;

import java.util.HashMap;

/**
 * Created by thewa on 5/11/2016.
 */
public class InputHandler {

    private static int key;

    private static HashMap<Integer, Action> actions;

    public static void update() {
        while (Keyboard.next()) {
            key = Keyboard.getEventKey();

            if (actions != null && actions.containsKey(key)) {
                Action action = actions.get(key);

                // Key is Pressed
                if (Keyboard.getEventKeyState()) {
                    action.press();
                } else {
                    action.release();
                }
            }
        }

        for (Action action : actions.values()) {
            action.handleInput();
        }
    }

    public static void setActions(HashMap<Integer, Action> actions) {
        InputHandler.actions = actions;
    }
}
