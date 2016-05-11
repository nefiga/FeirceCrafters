package game.graphics

import java.awt.image.BufferedImage
import java.util.HashMap

class TextureAtlas {

    private val TILE_EMPTY = 0
    private val TILE_FULL = 1

    private val textures = HashMap<String, Sprite>()

    /**
     * @return An array of all the pixels that have be loaded in this TextureAtlas
     */
    var atlas: IntArray? = null
        private set

    // The tiles of the atlas. This is determined by rows * columns
    // An empty space is indicated by 0 and a full space is indicated by 1
    private var tiles: IntArray? = null

    // The number of rows and columns in the TextureAtlas
    private val rows: Int
    private val columns: Int

    // The size of the TextureAtlas
    var width: Int = 0
        private set
    var height: Int = 0
        private set

    val textureCapacity: Int

    /**
     * Creates a new TextureAtlas

     * @param atlasSize The size of the TextureAtlas. There are the standard sizes super small, small, medium and large
     */
    constructor(atlasSize: Int) {
        rows = atlasSize / TILE_SIZE
        columns = atlasSize / TILE_SIZE
        width = atlasSize
        height = atlasSize
        atlas = IntArray(atlasSize * atlasSize)
        textureCapacity = rows * columns
        tiles = IntArray(rows * columns)
    }

    constructor(atlasWidth: Int, atlasHeight: Int) {
        rows = atlasHeight / TILE_SIZE
        columns = atlasWidth / TILE_SIZE
        width = atlasWidth
        height = atlasHeight
        atlas = IntArray(atlasWidth * atlasHeight)
        tiles = IntArray(rows * columns)
        textureCapacity = rows * columns
    }

    val size: Int
        get() = textures.size

    /**
     * Adds the image to the texture atlas at the first open position.
     * If the atlas already contains the image the position of the image will be returned.

     * @return An array where position 0 and 1 are the starting screenX and screenY positions of the image in the atlas
     * * and position 2 and 3 are the width and height of the image.
     */
    fun addTexture(name: String, image: BufferedImage): Sprite {
        if (textures.containsKey(name)) {
            return textures[name]!!
        }

        val w = image.width
        val h = image.height
        val textureColumns = w / TILE_SIZE
        val textureRows = h / TILE_SIZE
        val imagePixels = IntArray(w * h)
        image.getRGB(0, 0, w, h, imagePixels, 0, image.width)

        for (currentRow in 0..rows - 1) {
            for (currentColumn in 0..columns - 1) {
                if (hasSpace(currentColumn, currentRow, textureColumns, textureRows)) {
                    val sprite = Sprite(currentColumn * TILE_SIZE, currentRow * TILE_SIZE, image.width, image.height)

                    val pixelRow = currentRow * TILE_SIZE
                    val pixelCol = currentColumn * TILE_SIZE
                    for (y in 0..h - 1) {
                        for (x in 0..w - 1) {
                            atlas!![pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w]
                        }
                    }
                    fillTile(currentColumn, currentRow, textureColumns, textureRows)
                    textures.put(name, sprite)
                    return sprite
                }
            }
        }

        throw IllegalStateException("Atlas out off space ${toString()}")
    }

    /**
     * Adds the image to the texture atlas at the first open position.
     * If the atlas already contains the image the position of the image will be returned.

     * @return An array where position 0 and 1 are the starting screenX and screenY positions of the image in the atlas
     * * and position 2 and 3 are the width and height of the image.
     */
    fun addTexture(name: String): Sprite? {
        if (textures.containsKey(name)) {
            return textures[name]
        }

        val image = ImageManager.getImage(name)

        val imageWidth = image.width
        val imageHeight = image.height
        val textureColumns = imageWidth / TILE_SIZE
        val textureRows = imageHeight / TILE_SIZE
        val imagePixels = IntArray(imageWidth * imageHeight)
        image.getRGB(0, 0, imageWidth, imageHeight, imagePixels, 0, image.width)

        for (currentRow in 0..rows - 1) {
            for (currentColumn in 0..columns - 1) {
                if (hasSpace(currentColumn, currentRow, textureColumns, textureRows)) {
                    val sprite = Sprite(currentColumn * TILE_SIZE, currentRow * TILE_SIZE, image.width, image.height)

                    val pixelRow = currentRow * TILE_SIZE
                    val pixelCol = currentColumn * TILE_SIZE
                    for (y in 0..imageHeight - 1) {
                        for (x in 0..imageWidth - 1) {
                            atlas!![pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * imageWidth]
                        }
                    }
                    fillTile(currentColumn, currentRow, textureColumns, textureRows)
                    textures.put(name, sprite)
                    return sprite
                }
            }
        }

        System.err.print("Atlas out off space " + this.toString())
        return null
    }


    //TODO This method is not complete but I'm not sure there is need for it
    /**
     * Adds the pixels to the texture atlas at the first open position

     * @param imagePixels An array of pixels to be added to the texture atlas
     * *
     * @return An array where position 0 and 1 are the starting screenX and screenY positions of the image in the atlas.
     */
    fun addTexture(imagePixels: IntArray, w: Int, h: Int): IntArray? {
        val position = IntArray(2)
        val textureColumns = w / TILE_SIZE
        val textureRows = h / TILE_SIZE

        for (r in 0..rows - 1) {
            for (c in 0..columns - 1) {
                if (hasSpace(c, r, textureColumns, textureRows)) {
                    position[0] = c
                    position[1] = r

                    val pixelRow = r * TILE_SIZE
                    val pixelCol = c * TILE_SIZE
                    for (y in 0..h - 1) {
                        for (x in 0..w - 1) {
                            atlas!![pixelCol + x + (y + pixelRow) * width] = imagePixels[x + y * w]
                        }
                    }
                    fillTile(c, r, textureColumns, textureRows)
                    return position
                }
            }
        }

        System.err.println("Atlas out off space textureRows: $textureRows textureColumns: $textureColumns rows $rows columns $columns")
        return null
    }

    /**
     * Sets the location in the tiles array to 1 (full)

     * @param column          The starting column
     * *
     * @param row          The starting row
     * *
     * @param numColumns The number of columns to set
     * *
     * @param numRows    The number of rows to set
     */
    fun fillTile(column: Int, row: Int, numColumns: Int, numRows: Int) {
        for (currentRow in 0..numRows - 1) {
            for (currentColumn in 0..numColumns - 1) {
                tiles!![column + currentColumn + (row + currentRow) * rows] = 1
            }
        }
    }

    /**
     * Checks if there is enough space for the image at the given location

     * @param column      The starting column
     * *
     * @param rows      The starting row
     * *
     * @param numCol The number of columns the image will take up
     * *
     * @param numRow The number of rows the image will take up
     * *
     * @return true if there is enough space
     */
    fun hasSpace(column: Int, rows: Int, numCol: Int, numRow: Int): Boolean {
        if (tiles!![column + rows * columns] == TILE_EMPTY && column + numCol <= columns && rows + numRow <= this.rows) {
            for (currentRow in 0..numRow - 1) {
                for (currentColumn in 0..numCol - 1) {
                    if (tiles!![column + currentColumn + (rows + currentRow) * this.rows] == TILE_FULL) return false
                }
            }
            return true
        }

        return false
    }

    companion object {

        val FONT_NORMAL = 0
        val SUPER_SMALL = 320
        val SMALL = 640
        val MEDIUM = 960
        val LARGE = 1024

        // The size of a column and row
        val TILE_SIZE = 32
    }
}
