package game.util

/**
 * Created by thewa on 5/11/2016.
 */
object Util {
    fun tileToPixel(tile: Int): Float {
        return (tile shl 6).toFloat()
    }

    fun pixelToTile(pixel: Float): Int {
        return pixel.toInt() shr 6
    }

    fun pixelToTile(pixel: Int): Int {
        return pixel shr 6
    }
}