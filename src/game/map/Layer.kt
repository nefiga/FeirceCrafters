package game.map

import game.graphics.SpriteBatch
import game.tiles.Tile
import game.tiles.Tiles
import game.world.World

class Layer//TODO because we are going to procedurally generate the maps we need some kind of map loader/creator
(private val spriteBatch: SpriteBatch, private val mapWidth: Int, private val mapHeight: Int) {

    private val tiles: Array<Tile?>
    private val durability: IntArray
    private val direction: IntArray


    init {
        tiles = arrayOfNulls<Tile>(mapWidth * mapHeight)
        durability = IntArray(mapWidth * mapHeight)
        direction = IntArray(mapWidth * mapHeight)

        for (i in 0..tiles.size - 1) {
            tiles[i] = Tiles.treeTile
            durability[i] = 100
        }
    }

    //TODO We might want to add more options that just render at x/y like zoom, transparency...
    fun render(offsetX: Int, offsetY: Int) {
        val endX = mapWidth
        val endY = mapHeight

        spriteBatch.begin()
        spriteBatch.setOffsets(World.playerXOffset, World.playerYOffset)
        for (y in offsetY..endY - 1) {
            for (x in offsetX..endX - 1) {
                tiles[getPosition(x, y)]?.render(tileToPixel(x), tileToPixel(y), spriteBatch)
            }
        }
        spriteBatch.end()
    }

    fun update(xPosition: Int, yPosition: Int, delta: Long) {
        tiles[getPosition(xPosition, yPosition)]?.update(delta)
    }

    fun remove(xPosition: Int, yPosition: Int) {

    }

    fun interact(xPosition: Int, yPosition: Int) {
        if (inBounds(xPosition, yPosition)) {
            val position = getPosition(xPosition, yPosition)

            durability[position] -= 20

            if (durability[position] <= 0)
                tiles[position] = Tiles.greenTile
        }
    }

    private fun inBounds(x: Int, y: Int): Boolean {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight
    }

    private fun getPosition(x: Int, y: Int): Int {
        return x + y * mapWidth
    }

    companion object {

        fun tileToPixel(tile: Int): Int {
            return tile shl 6
        }

        fun pixelToTile(pixel: Int): Float {
            return (pixel shr 6).toFloat()
        }
    }
}
