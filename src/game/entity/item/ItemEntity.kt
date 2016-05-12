package game.entity.item

import game.entity.Entity
import game.entity.VisibleEntity
import game.graphics.Sprite
import game.graphics.SpriteBatch
import game.math.Vector2

/**
 * Created by thewa on 5/11/2016.
 */
open abstract class ItemEntity(position: Vector2) : Entity(position), VisibleEntity{

    abstract val sprite: Sprite

    override fun render(spriteBatch: SpriteBatch) {
        spriteBatch.draw(position, sprite)
    }
}