package game.entity.item

import game.graphics.Sprite
import game.math.Vector2

/**
 * Created by thewa on 5/11/2016.
 */
class LogItemEntity(position: Vector2) : ItemEntity(position){
    override val sprite: Sprite
        get() = ItemEntitySprites.logSprite
}