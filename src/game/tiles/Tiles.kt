package game.tiles

import game.graphics.*

object Tiles {

    private val atlas = TextureAtlas(TextureAtlas.SUPER_SMALL)

    // New tiles go here
    val greenTile = Tile(atlas.addTexture("testImage", ImageManager.getImage("/grass_tile")))
    val treeTile = Tile(atlas.addTexture("Tree", ImageManager.getImage("/tree")))

    // Creating the tile SpriteBatch
    var spriteBatch = SpriteBatch(ShaderManager.NORMAL_TEXTURE, Texture(atlas), 600)
}
