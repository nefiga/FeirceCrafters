package game.entity

import game.graphics.ImageManager
import game.graphics.Sprite
import game.graphics.TextureAtlas

/**
 * Created by thewa on 5/11/2016.
 */
object EntitySprites {

    val entitySpriteAtlas = TextureAtlas(TextureAtlas.LARGE)

    val playerSprite = entitySpriteAtlas.addTexture("faded", ImageManager.getImage("/faded"))
}
