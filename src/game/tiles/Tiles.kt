package game.tiles

import game.graphics.*

object Tiles {

    val atlas = TextureAtlas(TextureAtlas.SUPER_SMALL)

    // New tiles go here
    val greenTile = GreenTile("Grass")
    val treeTile = TreeTile("Tree")

    // Creating the tile SpriteBatch
    var spriteBatch = SpriteBatch(ShaderManager.NORMAL_TEXTURE, Texture(atlas), 1000)
}
