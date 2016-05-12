package game.map

import game.entity.Player
import game.graphics.SpriteBatch
import game.math.TilePosition
import game.math.Vector2
import game.tiles.Tile
import game.tiles.Tiles
import game.util.Util.pixelToTile
import game.util.Util.tileToPixel
import game.world.World
import org.lwjgl.opengl.Display

//TODO because we are going to procedurally generate the maps we need some kind of map loader/creator
class Layer(private val world: World, private val spriteBatch: SpriteBatch, private val mapWidth: Int, private val mapHeight: Int) {

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

    //TODO We might want to add more options that just render at x/y like zoom
    fun render(offsetX: Int, offsetY: Int) {
        var endX = (pixelToTile(Display.getWidth()) - pixelToTile(World.playerXOffset) + 1).toInt()
        var endY = (pixelToTile(Display.getHeight()) - pixelToTile(World.playerYOffset) + 1).toInt()

        if (offsetX > 0)
            endX += offsetX
        if (offsetY > 0)
            endY += offsetY

        spriteBatch.begin()
        spriteBatch.setOffsets(World.playerXOffset, World.playerYOffset)
        for (y in offsetY..endY) {
            for (x in offsetX..endX - 1) {
                if (inBounds(x, y))
                    tiles[getTileIndex(x, y)]?.render(tileToPixel(x).toInt(), tileToPixel(y).toInt(), spriteBatch)
            }
        }
        spriteBatch.end()
    }

    fun harvest(player: Player, position: TilePosition) {
        if (inBounds(position)) {
            val index = position.index(mapWidth)

            durability[index] -= 20

            if (durability[index] <= 0) {
                tiles[index] = Tiles.greenTile
                tiles[index]?.brake(world, Vector2(position.pixelX(), position.pixelY()))
            }
        }
    }

    private fun inBounds(x: Int, y: Int): Boolean {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight
    }

    private fun inBounds(tilePosition: TilePosition): Boolean {
        return tilePosition.x >= 0 && tilePosition.y >= 0 && tilePosition.x < mapWidth && tilePosition.y < mapHeight
    }

    private fun getTileIndex(x: Int, y: Int): Int {
        return x + y * mapWidth
    }

    private fun getTileIndex(tilePosition: TilePosition): Int {
        return (tilePosition.x + tilePosition.y * mapWidth).toInt()
    }

    companion object {
    }
}
