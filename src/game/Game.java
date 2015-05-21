package game;

import game.graphics.*;
import game.world.World;

import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;

public class Game extends GameLoop {

    TextureAtlas atlas;
    SpriteBatch spriteBatch;

    World world;

    Sprite testImage, testImageTwo;

    public void init() {
        super.init();

        world = new World();
    }

    public void update(long delta) {

    }

    public void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        world.render();
    }

    public static void main(String[] args) {
        GameLoop game = new Game();
    }
}
