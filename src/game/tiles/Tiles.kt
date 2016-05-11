package game.tiles

import game.graphics.*

object Tiles {

    private val atlas = TextureAtlas(TextureAtlas.SUPER_SMALL)

    // New tiles go here
    val greenTile = Tile(atlas.addTexture("Grass", ImageManager.getImage("/tiles/grass_tile")))
    val treeTile = Tile(atlas.addTexture("Tree", ImageManager.getImage("/tiles/tree_tile")))

    // Creating the tile SpriteBatch
    var spriteBatch = SpriteBatch(ShaderManager.NORMAL_TEXTURE, Texture(atlas), 600)
}
