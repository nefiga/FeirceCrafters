package game.graphics;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class TextureAtlas {

    public static final int FONT_NORMAL = 0, SUPER_SMALL = 320, SMALL = 640, MEDIUM = 960, LARGE = 1024;

    private final int TILE_EMPTY = 0, TILE_FULL = 1;

    private Map<String, int[]> textures = new HashMap<String, int[]>();

    private int[] atlas;

    // The tiles of the atlas. This is determined by rows * columns
    // An empty space is indicated by 0 and a full space is indicated by 1
    private int[] tiles;

    // The number of rows and columns in the TextureAtlas
    private final int rows, columns;

    // The size of the TextureAtlas
    private int width, height;

    // The size of a column and row
    public static final int TILE_SIZE = 32;

    private int textureCapacity;

    /**
     * Creates a new TextureAtlas
     *
     * @param atlasSize The size of the TextureAtlas. There are the standard sizes super small, small, medium and large
     */
    public TextureAtlas(int atlasSize) {
        rows = atlasSize / TILE_SIZE;
        columns = atlasSize / TILE_SIZE;
        width = atlasSize;
        height = atlasSize;
        atlas = new int[atlasSize * atlasSize];
        textureCapacity = rows * columns;
        tiles = new int[rows * columns];
    }

    public TextureAtlas(int atlasWidth, int atlasHeight) {
        rows = atlasHeight / TILE_SIZE;
        columns = atlasWidth / TILE_SIZE;
        width = atlasWidth;
        height = atlasHeight;
        atlas = new int[atlasWidth * atlasHeight];
        tiles = new int[rows * columns];
    }

    public int getSize() {
        return textures.size();
    }

    /**
     * Adds the image to the texture atlas at the first open position.
     * If the atlas already contains the image the position of the image will be returned.
     *
     * @return An array where position 0 and 1 are the starting screenX and screenY positions of the image in the atlas
     * and position 2 and 3 are the width and height of the image.
     */
    public int[] addTexture(String name, BufferedImage image) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }

        int[] position = new int[4];
        int w = image.getWidth();
        int h = image.getHeight();
        int textureColumns = w / TILE_SIZE;
        int textureRows = h / TILE_SIZE;
        int[] imagePixels = new int[w * h];
        image.getRGB(0, 0, w, h, imagePixels, 0, image.getWidth());

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (hasSpace(c, r, textureColumns, textureRows)) {
                    position[0] = c * TILE_SIZE;
                    position[1] = r * TILE_SIZE;
                    position[2] = image.getWidth();
                    position[3] = image.getHeight();

                    int pixelRow = r * TILE_SIZE;
                    int pixelCol = c * TILE_SIZE;
                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w];
                        }
                    }
                    fillTile(c, r, textureColumns, textureRows);
                    textures.put(name, position);
                    return position;
                }
            }
        }

        System.err.print("Atlas out off space " + this.toString());
        return null;
    }

    /**
     * Adds the image to the texture atlas at the first open position.
     * If the atlas already contains the image the position of the image will be returned.
     *
     * @return An array where position 0 and 1 are the starting screenX and screenY positions of the image in the atlas
     * and position 2 and 3 are the width and height of the image.
     */
    public int[] addTexture(String name) {
        if (textures.containsKey(name)) {
            return textures.get(name);
        }

        BufferedImage image = ImageManager.getImage(name);

        int[] position = new int[4];
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();
        int textureColumns = imageWidth / TILE_SIZE;
        int textureRows = imageHeight / TILE_SIZE;
        int[] imagePixels = new int[imageWidth * imageHeight];
        image.getRGB(0, 0, imageWidth, imageHeight, imagePixels, 0, image.getWidth());

        for (int currentRow = 0; currentRow < rows; currentRow++) {
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                if (hasSpace(currentColumn, currentRow, textureColumns, textureRows)) {
                    position[0] = currentColumn * TILE_SIZE;
                    position[1] = currentRow * TILE_SIZE;
                    position[2] = image.getWidth();
                    position[3] = image.getHeight();

                    int pixelRow = currentRow * TILE_SIZE;
                    int pixelCol = currentColumn * TILE_SIZE;
                    for (int y = 0; y < imageHeight; y++) {
                        for (int x = 0; x < imageWidth; x++) {
                            atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * imageWidth];
                        }
                    }
                    fillTile(currentColumn, currentRow, textureColumns, textureRows);
                    textures.put(name, position);
                    return position;
                }
            }
        }

        System.err.print("Atlas out off space " + this.toString());
        return null;
    }

    /**
     * Adds the pixels to the texture atlas at the first open position
     *
     * @param imagePixels An array of pixels to be added to the texture atlas
     * @return An array where position 0 and 1 are the starting screenX and screenY positions of the image in the atlas.
     */
    public int[] addTexture(int[] imagePixels, int w, int h) {
        int[] position = new int[2];
        int textureColumns = w / TILE_SIZE;
        int textureRows = h / TILE_SIZE;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (hasSpace(c, r, textureColumns, textureRows)) {
                    position[0] = c;
                    position[1] = r;

                    int pixelRow = r * TILE_SIZE;
                    int pixelCol = c * TILE_SIZE;
                    for (int y = 0; y < h; y++) {
                        for (int x = 0; x < w; x++) {
                            atlas[pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w];
                        }
                    }
                    fillTile(c, r, textureColumns, textureRows);
                    return position;
                }
            }
        }

        System.err.println("Atlas out off space textureRows: " + textureRows + " textureColumns: " + textureColumns + " rows " + rows + " columns " + columns);
        return null;
    }

    /**
     * Sets the location in the tiles array to 1 (full)
     *
     * @param column          The starting column
     * @param row          The starting row
     * @param numColumns The number of columns to set
     * @param numRows    The number of rows to set
     */
    public void fillTile(int column, int row, int numColumns, int numRows) {
        for (int currentRow = 0; currentRow < numRows; currentRow++) {
            for (int currentColumn = 0; currentColumn < numColumns; currentColumn++) {
                tiles[(column + currentColumn) + (row + currentRow) * rows] = 1;
            }
        }
    }

    /**
     * Checks if there is enough space for the image at the given location
     *
     * @param column      The starting column
     * @param rows      The starting row
     * @param numCol The number of columns the image will take up
     * @param numRow The number of rows the image will take up
     * @return true if there is enough space
     */
    public boolean hasSpace(int column, int rows, int numCol, int numRow) {
        if (tiles[column + rows * columns] == TILE_EMPTY && column + numCol <= columns && rows + numRow <= this.rows) {
            for (int currentRow = 0; currentRow < numRow; currentRow++) {
                for (int currentColumn = 0; currentColumn < numCol; currentColumn++) {
                    if (tiles[(column + currentColumn) + (rows + currentRow) * this.rows] == TILE_FULL) return false;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * @return An array of all the pixels that have be loaded in this TextureAtlas
     */
    public int[] getAtlas() {
        return atlas;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureCapacity() {
        return textureCapacity;
    }
}
