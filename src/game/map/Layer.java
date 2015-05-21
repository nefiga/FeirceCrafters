package game.map;

import game.graphics.SpriteBatch;
import game.tiles.Tile;

public class Layer {

    private int mapWidth;
    private int mapHeight;

    private Tile[] tiles;
    private int[] durabilty;
    private int[] direction;

    private SpriteBatch spriteBatch;


    //TODO because we are going to procedurally generate the maps we need some kind of map loader/creator
    public Layer(SpriteBatch spriteBatch, int width, int height) {
        this.spriteBatch = spriteBatch;
        mapWidth = width;
        mapHeight = height;
        tiles = new Tile[width * height];

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tile.greenTile;
        }

        durabilty = new int[width * height];
        direction = new int[width * height];
    }

    //TODO We might want to add more options that just render at x/y like zoom, transparency...
    public void render(int offsetX, int offsetY) {
        int startX = offsetX;
        int startY = offsetY;
        int endX = 30;
        int endY = 20;

        spriteBatch.begin();
        for (int y = startY; y < endY; y++) {
            for (int x = startX; x < endX; x++) {
                tiles[x + y * mapWidth].render(tileToPixel(x), tileToPixel(y), spriteBatch);
            }
        }
        spriteBatch.end();
    }

    public void update(int xPosition, int yPosition, long delta) {
        tiles[xPosition + yPosition * mapWidth].update(delta);
    }

    public void remove(int xPosition, int yPosition) {

    }

    public void interact(int xPosition, int yPosition) {

    }

    private int tileToPixel(int tile) {
        return tile << 6;
    }

    private float pixelToTile(int pixel) {
        return pixel >> 6;
    }
}
