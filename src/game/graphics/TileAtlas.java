package game.graphics;

public class TileAtlas {

    private static TextureAtlas atlas = new TextureAtlas(TextureAtlas.SUPER_SMALL);

    public static Sprite testImage = atlas.addTexture("testImage", ImageManager.getImage("/grass_tile"));
}
