package game.input

import org.lwjgl.input.Keyboard

import java.util.HashMap

/**
 * Created by thewa on 5/11/2016.
 */
object InputHandler {

    private var key: Int = 0

    private var actions: HashMap<Int, Action>? = null

    fun update() {
        while (Keyboard.next()) {
            key = Keyboard.getEventKey()

            if (actions != null) {
                if (actions!!.containsKey(key)) {
                    val action = actions!![key]

                    // Key is Pressed
                    if (Keyboard.getEventKeyState()) {
                        action?.press()
                    } else {
                        action?.release()
                    }
                }
            }
        }

        if (actions != null) {
            for (action in actions!!.values) {
                action.handleInput()
            }
        }
    }

    fun setActions(actions: HashMap<Int, Action>) {
        InputHandler.actions = actions
    }
}
