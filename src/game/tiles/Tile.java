package game.tiles;

import game.graphics.*;

public class Tile {

    private static TextureAtlas atlas = new TextureAtlas(TextureAtlas.SUPER_SMALL);

    public static final Tile greenTile = new Tile(atlas.addTexture("testImage", ImageManager.getImage("/grass_tile")));

    public static SpriteBatch spriteBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 600);

    private final int DEFAULT_SIZE = 64;

    private Sprite sprite;

    public Tile(Sprite sprite) {
        this.sprite = sprite;
    }

    public void render(int xPosition, int yPosition, SpriteBatch spriteBatch) {
        render(xPosition, yPosition, DEFAULT_SIZE, DEFAULT_SIZE, spriteBatch, SpriteBatch.NO_ROTATE);
    }

    public void render(int xPosition, int yPosition, SpriteBatch spriteBatch, int rotation) {
        render(xPosition, yPosition, DEFAULT_SIZE, DEFAULT_SIZE, spriteBatch, rotation);
    }

    public void render(int xPosition, int yPosition, int width, int height, SpriteBatch spriteBatch) {
        spriteBatch.draw(xPosition, yPosition, width, height, sprite.startX, sprite.startY, sprite.width, sprite.height, SpriteBatch.NO_ROTATE);
    }

    public void render(int xPosition, int yPosition, int width, int height, SpriteBatch spriteBatch, int rotation) {
        spriteBatch.draw(xPosition, yPosition, width, height, sprite.startX, sprite.startY, sprite.width, sprite.height, rotation);
    }

    public void update(long delta) {

    }
}
