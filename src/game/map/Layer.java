package game.map;

import game.graphics.SpriteBatch;
import game.tiles.Tile;
import game.tiles.Tiles;
import game.world.World;

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
        durabilty = new int[width * height];
        direction = new int[width * height];

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = Tiles.treeTile;
            durabilty[i] = 100;
        }
    }

    //TODO We might want to add more options that just render at x/y like zoom, transparency...
    public void render(int offsetX, int offsetY) {
        int endX = mapWidth;
        int endY = mapHeight;

        spriteBatch.begin();
        spriteBatch.setOffsets(World.getPlayerXOffset(), World.getPlayerYOffset());
        for (int y = offsetY; y < endY; y++) {
            for (int x = offsetX; x < endX; x++) {
                tiles[getPosition(x, y)].render(tileToPixel(x), tileToPixel(y), spriteBatch);
            }
        }
        spriteBatch.end();
    }

    public void update(int xPosition, int yPosition, long delta) {
        tiles[getPosition(xPosition, yPosition)].update(delta);
    }

    public void remove(int xPosition, int yPosition) {

    }

    public void interact(int xPosition, int yPosition) {
        if (inBounds(xPosition, yPosition)) {
            int position = getPosition(xPosition, yPosition);

            durabilty[position] -= 20;

            if (durabilty[position] <= 0)
                tiles[position] = Tiles.greenTile;
        }
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < mapWidth && y < mapHeight;
    }

    private int getPosition(int x, int y) {
        return x + y * mapWidth;
    }

    public static int tileToPixel(int tile) {
        return tile << 6;
    }

    public static float pixelToTile(int pixel) {
        return pixel >> 6;
    }
}
