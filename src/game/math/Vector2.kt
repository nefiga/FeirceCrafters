package game.math

class Vector2(var x: Float, var y: Float) {

    fun translateX(x: Float) {
        this.x = this.x + x
    }

    fun translateY(y: Float) {
        this.y = this.y + y
    }
}
