package game.entity

import game.GameLoop
import game.graphics.Sprite
import game.graphics.SpriteBatch
import game.math.TilePosition
import game.math.Vector2
import game.world.World

class Player() : Entity(Vector2(0F, 0F)), VisibleEntity {

    private var sprite: Sprite
    private var world: World? = null

    init {
        sprite = EntitySprites.playerSprite
    }

    fun joinWorld(world: World) {
        this.world = world
    }

    override fun update(delta: Long) {

    }

    fun harvest() {
        world?.harvest(this, TilePosition.fromPixels(x - 32, y -90))
    }

    override fun render(spriteBatch: SpriteBatch) {
        spriteBatch.draw((GameLoop.centerX - 32).toFloat(), (GameLoop.centerY - 32).toFloat(), sprite)
    }
}
