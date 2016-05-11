package game.map

import game.tiles.Tiles

import java.util.HashMap

class Map {

    private val layers: HashMap<String, Layer>? = null

    private var currentLayer: Layer? = null

    init {
        currentLayer = Layer(Tiles.spriteBatch, 10, 10)
    }

    fun renderMap(offsetX: Int, offsetY: Int) {
        currentLayer!!.render(offsetX, offsetY)
    }

    fun update(xPosition: Int, yPosition: Int, delta: Long) {
        currentLayer!!.update(xPosition, yPosition, delta)
    }

    fun remove(xPosition: Int, yPosition: Int) {

    }

    //Should take an entity once they have been set up
    fun interact(xPosition: Int, yPosition: Int) {
        currentLayer!!.interact(xPosition, yPosition)
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
