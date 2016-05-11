package game.graphics

import org.lwjgl.BufferUtils

import java.nio.ByteBuffer

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE
import org.lwjgl.opengl.GL13.GL_TEXTURE0
import org.lwjgl.opengl.GL13.glActiveTexture
import org.lwjgl.opengl.GL30.glGenerateMipmap

class Texture(internal var atlas:

              TextureAtlas) {

    /**
     * Bytes per pixel (R, G, B, A);
     */
    private val BPP = 4

    /**
     * The Id of this texture
     */
    private val id: Int

    val width: Int
    val height: Int

    internal var buffer: ByteBuffer

    init {
        this.width = atlas.width
        this.height = atlas.height
        buffer = BufferUtils.createByteBuffer(atlas.atlas!!.size * BPP)
        id = glGenTextures()
        loadTexture(atlas.atlas!!)
    }

    fun bind() {
        glBindTexture(GL_TEXTURE_2D, id)
    }

    fun setActiveTexture(unit: Int) {
        glActiveTexture(GL_TEXTURE0 + unit)
    }

    /**
     * Loads all the colors from the texture into byte buffer.
     * Then loads it to the gpu. also set scaling, filtering and mipmaps
     * @param texture An array of the colors in the texture
     */
    fun loadTexture(texture: IntArray) {
        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val pixel = texture[x + y * width]
                //Add red component
                buffer.put((pixel shr 16 and 0xff).toByte())
                //Add green component
                buffer.put((pixel shr 8 and 0xff).toByte())
                //Add blue component
                buffer.put((pixel and 0xff).toByte())
                //Add alpha component
                buffer.put((pixel shr 24 and 0xff).toByte())
            }
        }
        buffer.rewind()

        bind()

        // Set texture scaling and filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        // Send texture data to the gpu
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)

        glGenerateMipmap(GL_TEXTURE_2D)
    }

    /**
     * Updates the texture with any changes made to it's texture atlas
     */
    fun updateTexture() {
        val texture = atlas.atlas
        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val pixel = texture!![x + y * width]
                //Add red component
                buffer.put((pixel shr 16 and 0xff).toByte())
                //Add green component
                buffer.put((pixel shr 8 and 0xff).toByte())
                //Add blue component
                buffer.put((pixel and 0xff).toByte())
                //Add alpha component
                buffer.put((pixel shr 24 and 0xff).toByte())
            }
        }
        buffer.rewind()

        bind()

        // Set texture scaling and filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST)

        // Send texture data to the gpu
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer)

        glGenerateMipmap(GL_TEXTURE_2D)
    }


    fun subTexture(texture: IntArray, offsetX: Int, offsetY: Int, width: Int, height: Int) {
        val tempBuffer = BufferUtils.createByteBuffer(texture.size * 4)

        for (y in 0..height - 1) {
            for (x in 0..width - 1) {
                val pixel = texture[x + y * width]
                //Add red component
                tempBuffer.put((pixel shr 16 and 0xff).toByte())
                //Add green component
                tempBuffer.put((pixel shr 8 and 0xff).toByte())
                //Add blue component
                tempBuffer.put((pixel and 0xff).toByte())
                //Add alpha component
                tempBuffer.put((pixel shr 24 and 0xff).toByte())
            }
        }
        tempBuffer.rewind()

        bind()

        glTexSubImage2D(GL_TEXTURE_2D, 0, offsetX, offsetY, width, height, GL_RGBA, GL_UNSIGNED_BYTE, tempBuffer)
    }

    val atlasSize: Int
        get() = atlas.size

    val textureCapacity: Int
        get() = atlas.textureCapacity
}
