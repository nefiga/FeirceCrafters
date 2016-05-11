package game.graphics

import game.math.Vector2
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.Display
import org.lwjgl.util.vector.Vector
import org.omg.CORBA.NO_IMPLEMENT

import java.nio.FloatBuffer
import java.nio.ShortBuffer

import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP
import org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT
import org.lwjgl.opengl.GL15.*
import org.lwjgl.opengl.GL20.glDisableVertexAttribArray
import org.lwjgl.opengl.GL20.glEnableVertexAttribArray
import org.lwjgl.opengl.GL20.glVertexAttribPointer
import org.lwjgl.opengl.GL30.glBindVertexArray
import org.lwjgl.opengl.GL30.glGenVertexArrays

class SpriteBatch
/**
 * Creates a new SpriteBatcher Object
 */
(shader: String, private val texture: Texture, private val size: Int) {
    private val shader: ShaderManager

    private val vertex: FloatBuffer
    private val texCords: FloatBuffer
    private val elements: ShortBuffer

    private val vboID: Int
    private val vaoID: Int
    private val texID: Int
    private val eboID: Int
    private var points = 0
    private var renderCount: Int = 0
    private var elementPosition: Short = 0

    private var flushing: Boolean = false

    private var xOffset: Float = 0.toFloat()
    private var yOffset: Float = 0.toFloat()

    // Rotates the image clockwise
    enum class Rotation {
        NO_ROTATE, ROTATE_90, ROTATE_180, ROTATE_270
    }

    init {
        vertex = BufferUtils.createFloatBuffer(size * 8)
        texCords = BufferUtils.createFloatBuffer(size * 8)
        elements = BufferUtils.createShortBuffer(size * 10)
        this.shader = ShaderManager()
        this.shader.attachVertexShader(shader + ".vert")
        this.shader.attachFragmentShader(shader + ".frag")
        this.shader.link()

        vaoID = glGenVertexArrays()
        glBindVertexArray(vaoID)

        vboID = glGenBuffers()

        texID = glGenBuffers()

        eboID = glGenBuffers()

        glBindVertexArray(0)

        this.texture.setActiveTexture(0)

        // Turning on blending so alpha channel will be transparent
        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)
    }

    fun updateTexture() {
        texture.updateTexture()
    }

    /**
     * Binds the vertex array object and resets buffers and vertex point data
     */
    fun begin() {
        texture.bind()
        glBindVertexArray(vaoID)

        if (!flushing) {
            xOffset = 0f
            yOffset = 0f
        }

        vertex.clear()
        texCords.clear()
        elements.clear()
        points = 0
        elementPosition = 0
        flushing = false
    }

    /**
     * Flips all the buffers, sets the vertex attribute pointers and binds the element buffer object
     */
    fun end() {
        renderCount = 0
        vertex.flip()
        texCords.flip()
        elements.flip()

        glBindBuffer(GL_ARRAY_BUFFER, vboID)
        glBufferData(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW)
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0)

        glBindBuffer(GL_ARRAY_BUFFER, texID)
        glBufferData(GL_ARRAY_BUFFER, texCords, GL_STATIC_DRAW)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)
        glBindBuffer(GL_ARRAY_BUFFER, 0)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW)

        render()
    }

    fun flush() {
        flushing = true

        end()
        begin()
    }

    /**
     * Draws all the sprites in the buffers.
     */
    private fun render() {
        shader.bind()

        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glDrawElements(GL_TRIANGLE_STRIP, points, GL_UNSIGNED_SHORT, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)

        glBindVertexArray(0)

        shader.unBind()
    }

    /**
     * Sets how offset the images will be drawn from the position passed in. The offset will be added to the images position
     * Also the offsets are reset to zero when begin() is called so this method should be called before drawing the images
     * but after calling begin on the SpriteBatch.
     */
    fun setOffsets(xOffset: Float, yOffset: Float) {
        this.xOffset = xOffset
        this.yOffset = yOffset
    }

    fun draw(position: Vector2, sprite: Sprite) {
        draw(position.x, position.y, sprite)
    }

    @JvmOverloads fun draw(x: Float, y: Float, sprite: Sprite, rotate: Rotation = Rotation.NO_ROTATE) {
        draw(x, y, sprite.width, sprite.height, sprite, rotate)
    }

    //TODO make a draw method with a zoom option

    /**
     * Add the texture to the Buffer that will be rendered

     * @param x             The starting screenX point of the screen
     * *
     * @param y             The starting screenY point on the screen
     * *
     * @param drawWidth     The width to draw the texture
     * *
     * @param drawHeight    The height to draw the texture
     * *
     * @param sprite        The location of the sprite in the atlas
     * *
     * @param rotate        If the image should be rotate and in what direction it should be rotate
     */
    @JvmOverloads fun draw(x: Float, y: Float, drawWidth: Int, drawHeight: Int, sprite: Sprite, rotate: Rotation = Rotation.NO_ROTATE) {
        if (renderCount >= size) flush()

        val x1 = convertXPixelsToCoordinate(x)
        val y1 = convertYPixelsToCoordinate(y)
        val x2 = convertXPixelsToCoordinate(x + drawWidth)
        val y2 = convertYPixelsToCoordinate(y + drawHeight)

        //TODO If offset are not equal to zero add them to the x, y

        val tx1 = convertTextureXToCoordinate(sprite.startX.toFloat())
        val ty1 = convertTextureYToCoordinate(sprite.startY.toFloat())
        val tx2 = convertTextureXToCoordinate(sprite.endX.toFloat())
        val ty2 = convertTextureYToCoordinate(sprite.endY.toFloat())

        when (rotate) {
            SpriteBatch.Rotation.NO_ROTATE -> {
                vertex.put(x1).put(y1) // Top left
                vertex.put(x2).put(y1)// Top right
                vertex.put(x1).put(y2)// Bottom  left
                vertex.put(x2).put(y2)// Bottom right
            }
            SpriteBatch.Rotation.ROTATE_90 -> {
                vertex.put(x2).put(y1) // Top left
                vertex.put(x2).put(y2)// Top right
                vertex.put(x1).put(y1)// Bottom  left
                vertex.put(x1).put(y2)// Bottom right
            }
            SpriteBatch.Rotation.ROTATE_180 -> {
                vertex.put(x2).put(y2) // Top left
                vertex.put(x1).put(y2)// Top right
                vertex.put(x2).put(y1)// Bottom  left
                vertex.put(x1).put(y1)// Bottom right
            }
            SpriteBatch.Rotation.ROTATE_270 -> {
                vertex.put(x1).put(y2) // Top left
                vertex.put(x1).put(y1)// Top right
                vertex.put(x2).put(y2)// Bottom  left
                vertex.put(x2).put(y1)// Bottom right
            }
        }

        texCords.put(tx1).put(ty1)// Top left
        texCords.put(tx2).put(ty1)// Top right
        texCords.put(tx1).put(ty2)// Bottom left
        texCords.put(tx2).put(ty2)// Bottom right

        updateElements()

        renderCount++
    }

    private fun updateElements() {
        val startPosition = elements.position()

        if (elementPosition.toInt() != 0)
            elements.put(elementPosition)

        elements.put(elementPosition)
        elementPosition++
        elements.put(elementPosition)
        elementPosition++
        elements.put(elementPosition)
        elementPosition++
        elements.put(elementPosition)
        elements.put(elementPosition)
        elementPosition++
        points += elements.position() - startPosition
    }

    private fun convertXPixelsToCoordinate(xPixels: Float): Float {
        return (xPixels + xOffset) / Display.getWidth() * 2 - 1
    }

    private fun convertYPixelsToCoordinate(yPixels: Float): Float {
        return 1 - (yPixels + yOffset) / Display.getHeight() * 2
    }

    private fun convertTextureXToCoordinate(textureX: Float): Float {
        return textureX / texture.width
    }

    private fun convertTextureYToCoordinate(textureY: Float): Float {
        return textureY / texture.height
    }

    fun subTexture(pixels: IntArray, offsetX: Int, offsetY: Int, width: Int, height: Int) {
        texture.subTexture(pixels, offsetX, offsetY, width, height)
    }

    val texureAtlasSize: Int
        get() = texture.atlasSize
}
