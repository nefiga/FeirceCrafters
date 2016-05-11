package game.entity.item

import game.graphics.ImageManager
import game.graphics.TextureAtlas

/**
 * Created by thewa on 5/11/2016.
 */
object ItemEntitySprites {

    val itemEntityAtlas = TextureAtlas(TextureAtlas.LARGE)

    val logSprite = itemEntityAtlas.addTexture("Log", ImageManager.getImage("/entities/log"))
}