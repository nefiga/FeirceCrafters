package game.map;

import game.graphics.SpriteBatch;
import game.tiles.Tile;
import game.tiles.Tiles;
import game.world.World;

import java.util.Random;

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

        Random random = new Random();
        for (int i = 0; i < tiles.length; i++) {
//            int tree = random.nextInt(100);
//            if (tree < 50)
                tiles[i] = Tiles.treeTile;
//            else
//                tiles[i] = Tiles.greenTile;
        }

        durabilty = new int[width * height];
        direction = new int[width * height];
    }

    //TODO We might want to add more options that just render at x/y like zoom, transparency...
    public void render(int offsetX, int offsetY) {
        int startX = offsetX;
        int startY = offsetY;
        int endX = mapWidth;
        int endY = mapHeight;

        spriteBatch.begin();
        spriteBatch.setOffsets(World.getPlayerXOffset(), World.getPlayerYOffset());
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
        System.out.println("Removing tile at " + xPosition + "  " + yPosition);
        tiles[xPosition + yPosition * mapWidth] = Tiles.greenTile;
    }

    public void interact(int xPosition, int yPosition) {

    }

    public static int tileToPixel(int tile) {
        return tile << 6;
    }

    public static float pixelToTile(int pixel) {
        return pixel >> 6;
    }
}
