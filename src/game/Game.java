package game;

import game.graphics.*;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;

public class Game extends GameLoop {

    TextureAtlas atlas;
    SpriteBatch spriteBatch;

    int[] testImage, testImageTwo;

    public void init() {
        super.init();
        atlas = new TextureAtlas(TextureAtlas.SUPER_SMALL);
        spriteBatch = new SpriteBatch(ShaderManager.NORMAL_TEXTURE, new Texture(atlas), 1);
        BufferedImage image = ImageManager.getImage("/grass_tile");
        BufferedImage imageTwo = ImageManager.getImage("/grass_tile");
        testImage = atlas.addTexture("testImage", image);
        testImageTwo = atlas.addTexture("testImageTwo", imageTwo);
        spriteBatch.updateTexture();
    }

    public void update(long delta) {

    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        spriteBatch.begin();
        spriteBatch.draw(5, 5, testImage[0], testImage[1], testImage[2], testImage[3]);
        spriteBatch.draw(200, 100, testImage[0], testImage[1], testImage[2], testImage[3]);
        spriteBatch.draw(324, 35, testImage[0], testImage[1], testImage[2], testImage[3]);
        spriteBatch.draw(5, 678, testImage[0], testImage[1], testImage[2], testImage[3]);
        spriteBatch.draw(467, 694, testImage[0], testImage[1], testImage[2], testImage[3]);
        spriteBatch.draw(853, 683, testImageTwo[0], testImageTwo[1], testImageTwo[2], testImageTwo[3]);
        spriteBatch.end();
    }

    public static void main(String[] args) {
        GameLoop game = new Game();
    }
}
