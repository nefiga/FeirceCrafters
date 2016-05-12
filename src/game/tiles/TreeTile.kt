package game.tiles

import game.entity.item.LogItemEntity
import game.math.Vector2
import game.world.World
import java.util.*

/**
 * Created by thewa on 5/11/2016.
 */
class TreeTile(name: String) : Tile(name) {

    val random = Random()
    override fun brake(world: World, position: Vector2) {
        position.translateX(random.nextInt(45).toFloat())
        position.translateY(random.nextInt(45).toFloat())
        world.addItemEntity(LogItemEntity(position))
    }
}
