package game.math

import game.util.Util.pixelToTile
import game.util.Util.tileToPixel

/**
 * Created by thewa on 5/11/2016.
 */
class TilePosition(val x: Int, val y: Int) {

    /**
     * Returns the index in an array based of the width passed in
     */
    fun index(width: Int): Int {
        return x + y * width
    }

    fun pixelX(): Float {
        return tileToPixel(x)
    }

    fun pixelY(): Float {
        return tileToPixel(y)
    }

    companion object {
        fun fromPixels(xPixels: Float, yPixels: Float) : TilePosition {
            return TilePosition(pixelToTile(xPixels), pixelToTile(yPixels))
        }
    }
}