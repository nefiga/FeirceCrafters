package game.tiles;

import game.graphics.*;

public class Tiles {

    private static TextureAtlas atlas = new TextureAtlas(TextureAtlas.SUPER_SMALL);

    // New tiles go here
    public static final Tile greenTile = new Tile(atlas.addTexture("testImage", ImageManager.getImage("/grass_tile")));
    public static final Tile treeTile = new Tile(atlas.addTexture("Tree", ImageManager.getImage("/tree")));

    // Creating the tile SpriteBatch
    public static SpriteBatch spriteBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 600);
}