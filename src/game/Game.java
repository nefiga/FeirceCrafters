package game;

import game.graphics.*;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;

public class Game extends GameLoop {

    TextureAtlas atlas;
    SpriteBatch spriteBatch;

    Sprite testImage, testImageTwo;

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
        spriteBatch.draw(5, 5, testImage.startX, testImage.startY, testImage.width, testImage.height);
        spriteBatch.draw(200, 100, testImage.startX, testImage.startY, testImage.width, testImage.height);
        spriteBatch.draw(324, 35, testImage.startX, testImage.startY, testImage.width, testImage.height);
        spriteBatch.draw(5, 678, testImage.startX, testImage.startY, testImage.width, testImage.height);
        spriteBatch.draw(467, 694, testImage.startX, testImage.startY, testImage.width, testImage.height);
        spriteBatch.draw(853, 683, testImage.startX, testImage.startY, testImage.width, testImage.height);
        spriteBatch.end();
    }

    public static void main(String[] args) {
        GameLoop game = new Game();
    }
}
