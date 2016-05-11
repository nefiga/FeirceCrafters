package game.graphics

object TileAtlas {

    private val atlas = TextureAtlas(TextureAtlas.SUPER_SMALL)

    var testImage = atlas.addTexture("testImage", ImageManager.getImage("/grass_tile"))
}
