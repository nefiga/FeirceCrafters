package game.map

import game.entity.Player
import game.math.TilePosition
import game.math.Vector2
import game.tiles.Tiles
import game.world.World

import java.util.HashMap

class Map(val world: World) {

    private val layers: HashMap<String, Layer>? = null

    private var currentLayer: Layer? = null

    init {
        currentLayer = Layer(world, Tiles.spriteBatch, 10000, 10000)
    }

    fun renderMap(offsetX: Int, offsetY: Int) {
        currentLayer!!.render(offsetX, offsetY)
    }

    fun remove(xPosition: Int, yPosition: Int) {

    }

    fun harvest(player: Player, tilePosition: TilePosition) {
        currentLayer?.harvest(player, tilePosition)
    }

    //Should take an entity once they have been set up
    fun interact(xPosition: Int, yPosition: Int) {

    }

    fun addLayer(name: String, layer: Layer) {
        layers!!.put(name, layer)
    }

    fun setCurrentLayer(name: String) {
        if (layers!!.containsKey(name))
            currentLayer = layers[name]
        else
            System.err.println("Could not set current layer to $name because it does not exist")
    }
}
