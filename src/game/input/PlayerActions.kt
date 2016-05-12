package game.input

import game.entity.Player
import org.lwjgl.input.Keyboard.*

import java.util.HashMap

/**
 * Created by thewa on 5/11/2016.
 */
class PlayerActions(private val player: Player) {

    val actions = HashMap<Int, Action>()

    init {
        loadActions()
    }

    /**
     * Be sure to set the actions listener!!!
     */
    fun addAction(key: Int, action: Action) {
        if (actions.containsKey(key)) {
            System.err.print("\"PlayerActions\" A action was already mapped to $key")
        } else {
            actions.put(key, action)
        }
    }

    private fun loadActions() {
        val up = Action("UP")
        up.setOnHoldListener { player.translateY(-3f) }

        val down = Action()
        down.setOnHoldListener { player.translateY(3f) }

        val left = Action()
        left.setOnHoldListener { player.translateX(-3f) }

        val right = Action()
        right.setOnHoldListener { player.translateX(3f) }

        val action = Action()
        action.setOnClickListener { player.harvest() }

        actions.put(KEY_W, up)
        actions.put(KEY_UP, up)
        actions.put(KEY_S, down)
        actions.put(KEY_DOWN, down)
        actions.put(KEY_A, left)
        actions.put(KEY_LEFT, left)
        actions.put(KEY_D, right)
        actions.put(KEY_RIGHT, right)
        actions.put(KEY_SPACE, action)
    }
}
