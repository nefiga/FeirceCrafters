package game.entity

import game.GameLoop
import game.graphics.Sprite
import game.graphics.SpriteBatch
import game.math.Vector2
import game.world.World

class Player(private val sprite: Sprite, position: Vector2) : Entity(position), VisibleEntity {

    private var world: World? = null

    fun joinWorld(world: World) {
        this.world = world
    }

    override fun update(delta: Long) {

    }

    fun interact() {
        world?.interact(-x - 32, -y - 32)
    }

    override fun render(spriteBatch: SpriteBatch) {
        spriteBatch.draw((GameLoop.centerX - 32).toFloat(), (GameLoop.centerY - 32).toFloat(), sprite)
    }
}
