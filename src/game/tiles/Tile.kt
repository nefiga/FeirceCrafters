package game.tiles

import game.graphics.*

open class Tile(private val sprite: Sprite) {

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

    fun update(delta: Long) {

    }

    companion object {
        val DEFAULT_SIZE = 64
    }
}
