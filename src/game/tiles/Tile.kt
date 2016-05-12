package game.tiles

import game.graphics.*
import game.math.Vector2
import game.world.World

abstract class Tile(val name: String) {

    private val sprite: Sprite

    init {
        sprite = Tiles.atlas.addTexture(name, ImageManager.getImage("/tiles/${name.toLowerCase()}_tile"))
    }

    open fun update(delta: Long) {

    }

    open fun brake(world: World, position: Vector2) {

    }

    fun render(xPosition: Int, yPosition: Int, spriteBatch: SpriteBatch) {
        render(xPosition, yPosition, DEFAULT_SIZE, DEFAULT_SIZE, spriteBatch, SpriteBatch.Rotation.NO_ROTATE)
    }

    fun render(xPosition: Int, yPosition: Int, spriteBatch: SpriteBatch, rotation: SpriteBatch.Rotation) {
        render(xPosition, yPosition, DEFAULT_SIZE, DEFAULT_SIZE, spriteBatch, rotation)
    }

    fun render(xPosition: Int, yPosition: Int, width: Int, height: Int, spriteBatch: SpriteBatch) {
        spriteBatch.draw(xPosition.toFloat(), yPosition.toFloat(), width, height, sprite, SpriteBatch.Rotation.NO_ROTATE)
    }

    fun render(xPosition: Int, yPosition: Int, width: Int, height: Int, spriteBatch: SpriteBatch, rotation: SpriteBatch.Rotation) {
        spriteBatch.draw(xPosition.toFloat(), yPosition.toFloat(), width, height, sprite, rotation)
    }

    companion object {
        val DEFAULT_SIZE = 64
    }
}
