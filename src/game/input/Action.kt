package game.input

/**
 * Created by thewa on 5/11/2016.
 */
class Action {
    private var onClickListener: (() -> Unit)? = null
    private var onHoldListener: (() -> Unit)? = null

    var isHolding: Boolean = false
        private set
    private var pressed: Boolean = false
    private var clicked: Boolean = false

    val name: String

    constructor(name: String) {
        this.name = name
    }

    constructor() {
        name = ""
    }

    fun handleInput() {
        if (isHolding)
            onHoldListener?.invoke()
        if (isClicked)
            onClickListener?.invoke()
    }

    fun press() {
        if (!isHolding) {
            pressed = true
            isHolding = true
        } else
            pressed = false
    }

    fun release() {
        if (isHolding || pressed)
            clicked = true

        isHolding = false
        pressed = false
    }

    val isPressed: Boolean
        get() {
            if (pressed) {
                pressed = false
                return true
            }
            return false
        }

    val isClicked: Boolean
        get() {
            if (clicked) {
                clicked = false
                return true
            }

            return false
        }

    fun setOnClickListener(onClickListener: () -> Unit) {
        this.onClickListener = onClickListener
    }

    fun setOnHoldListener(onHoldListener: () -> Unit) {
        this.onHoldListener = onHoldListener
    }
}
