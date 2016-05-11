package game.graphics

/**
 * Location of the image in the atlas
 */
class Sprite(val startX: Int, val startY: Int, val width: Int, val height: Int) {
    val endX: Int
    val endY: Int

    init {
        endX = startX + width
        endY = startY + height
    }
}
