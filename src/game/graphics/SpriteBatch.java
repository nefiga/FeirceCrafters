package game.graphics;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class SpriteBatch {

    Texture texture;
    ShaderManager shader;

    FloatBuffer vertex, texCords;
    ShortBuffer elements;

    int vboID, vaoID, texID, eboID;
    int points = 0;
    int size;
    int renderCount;
    short elementPosition = 0;

    // Rotates the image clockwise
    public static final int NO_ROTATE = 0, ROTATE_90 = 1, ROTATE_180 = 2, ROTATE_270 = 3;

    /**
     * Creates a new SpriteBatcher Object
     */
    public SpriteBatch(String shader, Texture texture, int size) {
        this.size = size;
        vertex = BufferUtils.createFloatBuffer(size * 8);
        texCords = BufferUtils.createFloatBuffer(size * 8);
        elements = BufferUtils.createShortBuffer(size * 10);

        this.texture = texture;
        this.shader = new ShaderManager();
        this.shader.attachVertexShader(shader + ".vert");
        this.shader.attachFragmentShader(shader + ".frag");
        this.shader.link();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();

        texID = glGenBuffers();

        eboID = glGenBuffers();

        glBindVertexArray(0);

        this.texture.setActiveTexture(0);

        // Turning on blending so alpha channel will be transparent
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void updateTexture() {
        texture.updateTexture();
    }

    /**
     * Binds the vertex array object and resets buffers and vertex point data
     */
    public void begin() {
        texture.bind();
        glBindVertexArray(vaoID);

        vertex.clear();
        texCords.clear();
        elements.clear();
        points = 0;
        elementPosition = 0;
    }

    /**
     * Flips all the buffers, sets the vertex attribute pointers and binds the element buffer object
     */
    public void end() {
        renderCount = 0;
        vertex.flip();
        texCords.flip();
        elements.flip();

        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, texID);
        glBufferData(GL_ARRAY_BUFFER, texCords, GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elements, GL_STATIC_DRAW);

        render();
    }

    public void flush() {
        //System.out.println("Flushing sprite batch because max render count was reached");
        end();
        begin();
    }

    /**
     * Draws all the sprites in the buffers.
     */
    private void render() {
        shader.bind();

        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLE_STRIP, points, GL_UNSIGNED_SHORT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        shader.unBind();
    }

    public void draw(float x, float y, int textureX, int textureY, int width, int height) {
        draw(x, y, width, height, textureX, textureY, width, height, NO_ROTATE);
    }

    public void draw(float x, float y, int textureX, int textureY, int width, int height, int rotate) {
        draw(x, y, width, height, textureX, textureY, width, height, rotate);
    }

    public void draw(float x, float y, int drawWidth, int drawHeight, int textureX, int textureY, int textureWidth, int textureHeight) {
        draw(x, y, drawWidth, drawHeight, textureX, textureY, textureWidth, textureHeight, NO_ROTATE);
    }

    //TODO make a draw method with a zoom option

    /**
     * Add the texture to the Buffer that will be rendered
     *
     * @param x             The starting screenX point of the screen
     * @param y             The starting screenY point on the screen
     * @param drawWidth     The width to draw the texture
     * @param drawHeight    The height to draw the texture
     * @param textureX      The starting screenX point of the texture in the texture atlas
     * @param textureY      The starting screenY point of the texture in the texture atlas
     * @param textureWidth  The width of the texture in the texture atlas
     * @param textureHeight The height of the texture in the texture atlas
     * @param rotate       If the image should be rotate and in what direction it should be rotate
     */
    public void draw(float x, float y, int drawWidth, int drawHeight, int textureX, int textureY, int textureWidth, int textureHeight, int rotate) {
        if (renderCount >= size) flush();

        float x1 = convertXPixelsToCoordinate(x);
        float y1 = convertYPixelsToCoordinate(y);
        float x2 = convertXPixelsToCoordinate(x + drawWidth);
        float y2 = convertYPixelsToCoordinate(y + drawHeight);


        float tx1 = convertTextureXToCoordinate(textureX);
        float ty1 = convertTextureYToCoordinate(textureY);
        float tx2 = convertTextureXToCoordinate(textureX + textureWidth);
        float ty2 = convertTextureYToCoordinate(textureY + textureHeight);

        switch (rotate) {
            case NO_ROTATE:
                vertex.put(x1).put(y1); // Top left
                vertex.put(x2).put(y1);// Top right
                vertex.put(x1).put(y2);// Bottom  left
                vertex.put(x2).put(y2);// Bottom right
                break;
            case ROTATE_90:
                vertex.put(x2).put(y1); // Top left
                vertex.put(x2).put(y2);// Top right
                vertex.put(x1).put(y1);// Bottom  left
                vertex.put(x1).put(y2);// Bottom right
                break;
            case ROTATE_180:
                vertex.put(x2).put(y2); // Top left
                vertex.put(x1).put(y2);// Top right
                vertex.put(x2).put(y1);// Bottom  left
                vertex.put(x1).put(y1);// Bottom right
                break;
            case ROTATE_270:
                vertex.put(x1).put(y2); // Top left
                vertex.put(x1).put(y1);// Top right
                vertex.put(x2).put(y2);// Bottom  left
                vertex.put(x2).put(y1);// Bottom right
                break;
        }

        texCords.put(tx1).put(ty1);// Top left
        texCords.put(tx2).put(ty1);// Top right
        texCords.put(tx1).put(ty2);// Bottom left
        texCords.put(tx2).put(ty2);// Bottom right

        updateElements();

        renderCount++;
    }

    private void updateElements() {
        int startPosition = elements.position();

        if (elementPosition != 0)
            elements.put(elementPosition);

        elements.put(elementPosition);
        elementPosition++;
        elements.put(elementPosition);
        elementPosition++;
        elements.put(elementPosition);
        elementPosition++;
        elements.put(elementPosition);
        elements.put(elementPosition);
        elementPosition++;
        points += elements.position() - startPosition;
    }

    private float convertXPixelsToCoordinate(float xPixels) {
        return xPixels / Display.getWidth() * 2 - 1;
    }

    private float convertYPixelsToCoordinate(float yPixels) {
        return 1 - yPixels / Display.getHeight() * 2;
    }

    private float convertTextureXToCoordinate(float textureX) {
        return textureX / texture.getWidth();
    }

    private float convertTextureYToCoordinate(float textureY) {
        return textureY / texture.getHeight();
    }

    public void subTexture(int[] pixels, int offsetX, int offsetY, int width, int height) {
        texture.subTexture(pixels, offsetX, offsetY, width, height);
    }

    public int getTexureAtlasSize() {
        return texture.getAtlasSize();
    }
}
