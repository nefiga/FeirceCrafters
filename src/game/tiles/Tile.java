package game.tiles;

import game.graphics.*;

public class Tile {
    public static final int DEFAULT_SIZE = 64;

    private Sprite sprite;

    private int durability;

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
        spriteBatch.draw(xPosition, yPosition, width, height, sprite, SpriteBatch.NO_ROTATE);
    }

    public void render(int xPosition, int yPosition, int width, int height, SpriteBatch spriteBatch, int rotation) {
        spriteBatch.draw(xPosition, yPosition, width, height, sprite, rotation);
    }

    public void update(long delta) {

    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
