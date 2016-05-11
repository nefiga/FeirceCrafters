package game.entity.item

import game.entity.Entity
import game.entity.VisibleEntity
import game.graphics.Sprite
import game.graphics.SpriteBatch
import game.math.Vector2

/**
 * Created by thewa on 5/11/2016.
 */
open class ItemEntity(position: Vector2) : Entity(position), VisibleEntity{

    val sprite: Sprite

    init {
        sprite = ItemEntitySprites.logSprite
    }

    override fun render(spriteBatch: SpriteBatch) {
        spriteBatch.draw(position, sprite)
    }
}