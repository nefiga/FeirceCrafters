package game.graphics;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

public class Texture {

    TextureAtlas atlas;

    /**
     * Bytes per pixel (R, G, B, A);
     */
    private final int BPP = 4;

    /**
     * The Id of this texture
     */
    private int id;

    private int width, height;

    ByteBuffer buffer;

    public Texture(TextureAtlas atlas) {
        this.atlas = atlas;
        this.width = atlas.getWidth();
        this.height = atlas.getHeight();
        buffer = BufferUtils.createByteBuffer(atlas.getAtlas().length * BPP);
        id = glGenTextures();
        loadTexture(atlas.getAtlas());
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void setActiveTexture(int unit) {
        glActiveTexture(GL_TEXTURE0 + unit);
    }

    /**
     * Loads all the colors from the texture into byte buffer.
     * Then loads it to the gpu. also set scaling, filtering and mipmaps
     * @param texture An array of the colors in the texture
     */
    public void loadTexture(int[] texture) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = texture[x + y * width];
                //Add red component
                buffer.put((byte) ((pixel >> 16) & 0xff));
                //Add green component
                buffer.put((byte) ((pixel >> 8) & 0xff));
                //Add blue component
                buffer.put((byte) (pixel & 0xff));
                //Add alpha component
                buffer.put((byte) ((pixel >> 24) & 0xff));
            }
        }
        buffer.rewind();

        bind();

        // Set texture scaling and filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Send texture data to the gpu
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);
    }

    /**
     * Updates the texture with any changes made to it's texture atlas
     */
    public void updateTexture() {
        int[] texture = atlas.getAtlas();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = texture[x + y * width];
                //Add red component
                buffer.put((byte) ((pixel >> 16) & 0xff));
                //Add green component
                buffer.put((byte) ((pixel >> 8) & 0xff));
                //Add blue component
                buffer.put((byte) (pixel & 0xff));
                //Add alpha component
                buffer.put((byte) ((pixel >> 24) & 0xff));
            }
        }
        buffer.rewind();

        bind();

        // Set texture scaling and filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        // Send texture data to the gpu
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        glGenerateMipmap(GL_TEXTURE_2D);
    }


    public void subTexture(int[] texture, int offsetX, int offsetY, int width, int height) {
        ByteBuffer tempBuffer = BufferUtils.createByteBuffer(texture.length * 4);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = texture[x + y * width];
                //Add red component
                tempBuffer.put((byte) ((pixel >> 16) & 0xff));
                //Add green component
                tempBuffer.put((byte) ((pixel >> 8) & 0xff));
                //Add blue component
                tempBuffer.put((byte) (pixel & 0xff));
                //Add alpha component
                tempBuffer.put((byte) ((pixel >> 24) & 0xff));
            }
        }
        tempBuffer.rewind();

        bind();

        glTexSubImage2D(GL_TEXTURE_2D, 0, offsetX, offsetY, width, height, GL_RGBA, GL_UNSIGNED_BYTE, tempBuffer);
    }

    public int getAtlasSize() {
        return atlas.getSize();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTextureCapacity() {
        return atlas.getTextureCapacity();
    }
}
