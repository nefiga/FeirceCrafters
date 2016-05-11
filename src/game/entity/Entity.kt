package game.entity

import game.math.Vector2

open class Entity(internal var position: Vector2) {

    open fun update(delta: Long) {

    }

    val x: Float
        get() = position.x

    val y: Float
        get() = position.y

    fun translateX(x: Float) {
        position.translateX(x)
    }

    fun translateY(y: Float) {
        position.translateY(y)
    }
}
